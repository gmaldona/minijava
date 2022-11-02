package minijava.lang.typechecker

import minijava.lang.ast._
import minijava.lang.error.{CircularInheritance, IllegalInheritance, KeywordThisUsedInMainError, OperationNotSupported, TypeMismatchError, TypeNotSupported, UseBeforeDeclaration}
import minijava.lang.parser.symboltable.{SymbolTable, SymbolTableType}

import scala.annotation.tailrec
import scala.language.postfixOps
import scala.util.control.Breaks._

object TypeChecker {

    def typeCheck(symbolTable: SymbolTable, node: ASTNode): Unit = {
        node match {
            case n: Program =>
                illegalInheritanceCheck(symbolTable, n)

                for (classDecl <- n.ClassDecls) {
                    val classSymbolTable = symbolTable.getChildSymbolTable(classDecl.ClassName.id).get
                    typeCheck(classSymbolTable, classDecl)
                }
            case n: ClassDecl =>
                for (methodDecl <- n.methodDecls) {
                    val methodSymbolTable = symbolTable.getChildSymbolTable(n.ClassName.id + " - " + methodDecl.methodName.id).get
                    typeCheck(methodSymbolTable, methodDecl)
                }
            case n: MethodDecl =>
                returnStatementTypeCheck(symbolTable, n)
                for (statement <- n.statements) {
                    typeCheck(symbolTable, statement)
                }
            case n: AssignStatement =>
                assignStatementTypeCheck(symbolTable, n)
            case n: ArrayAssignStatement =>
                arrayAssignStatementTypeCheck(symbolTable, n)
            case _ =>
        }
    }

    /** Checks if the returned expression matches the return type of the method signature
     *
     * @param symbolTable Symbol Table for the Method scope
     * @param node        Node that contains the MethodDecl
     */
    def returnStatementTypeCheck(symbolTable: SymbolTable, node: MethodDecl): Unit = {
        val returnType: Type = expressionTypeCheck(symbolTable, node.returnExpr)
        if (node.methodType != returnType)
            TypeMismatchError("Got a return type of " + returnType + " when expecting a return type of " + node.methodType)
    }

    /** Checks if there is illegal inheritance when classes extends another class
     *
     * @param symbolTable Symbol Table for the Program scope
     * @param node        Node that contains the Program
     */
    def illegalInheritanceCheck(symbolTable: SymbolTable, node: Program): Unit = {

        /** Builds a stack of inheritance through recursion
         *
         * @param stack Recursive stack added to
         * @param node  Each ClassDecl node extends from
         */
        @tailrec
        def buildInheritanceStack(stack: List[String], node: ClassDecl): Unit = {
            node.superClass match {
                case Some(superClass) =>
                    if (!symbolTable.containsClass(superClass.id))
                        UseBeforeDeclaration("Class " + superClass.id + " was used before declared.")
                    if (stack.contains(node.ClassName.id))
                        CircularInheritance(node.ClassName.id)
                    buildInheritanceStack(stack :+ superClass.id,
                        symbolTable.getTableEntry(superClass.id, SymbolTableType.Class)._4.asInstanceOf[ClassDecl]
                    )
                case None =>
            }
        }
        for (classDecl <- node.ClassDecls)
            buildInheritanceStack(List(), classDecl)
    }

    /** Checks assignment expression type equal to the declared type of a variable
     *
     * @param symbolTable Symbol Table that contains the scope of the statement
     * @param node        Node that contains the AssignStatement
     */
    def assignStatementTypeCheck(symbolTable: SymbolTable, node: AssignStatement): Unit = {
        var currentSymbolTable: Option[SymbolTable] = Some(symbolTable)
        breakable {
            while (true) {
                currentSymbolTable match {
                    case Some(table) =>
                        if (table.containsSymbol(node.varName.id)) {
                            break
                        }
                        currentSymbolTable = symbolTable.parentSymbolTable
                    case None => UseBeforeDeclaration("Symbol " + node.varName.id + " used before declared.")
                }
            }
        }

        val tableEntry = currentSymbolTable.get.getTableEntry(node.varName.id, SymbolTableType.Variable)
        val varDeclType = tableEntry._4.asInstanceOf[VarDecl].varType
        val exprType = expressionTypeCheck(symbolTable, node.expr)
        if (varDeclType != exprType)
            TypeMismatchError("Mismatch type of " + varDeclType + " with " + exprType)
    }


    /** Checks assignment expression type equal to the declared type of a int[]
     * @param symbolTable Symbol Table that contains the scope of the statement
     * @param node        Node that contains the ArrayAssignStatement
     */
    def arrayAssignStatementTypeCheck(symbolTable: SymbolTable, node: ArrayAssignStatement): Unit = {
        val indexExpr = expressionTypeCheck(symbolTable, node.indexExpr)
        if (indexExpr != int()) {
            TypeMismatchError("Expecting an index expression of type " + int() + ". Got an expression of type " + indexExpr)
        }
        val assignExpr = expressionTypeCheck(symbolTable, node.expr)
        if (assignExpr != int()) {
            TypeMismatchError("Expecting an expression of type " + int() + ". Got an expression of type " + assignExpr)
        }
    }

    def expressionTypeCheck(symbolTable: SymbolTable, node: Expression): Type = {
        node match {
            case n: ExprNumber =>
                n.expr2 match {
                    case Some(expr) =>
                        val expr2Type = expression2TypeCheck(symbolTable, expr)
                        if (expr2Type != int())
                            TypeMismatchError("Mismatch type of " + int() + " with " + expr2Type)
                        int()
                    case None => int()
                }
            case n: ExprBoolean =>
                n.expr2 match {
                    case Some(expr) =>
                        val expr2Type = expression2TypeCheck(symbolTable, expr)
                        if (expr2Type != boolean())
                            TypeMismatchError("Mismatch type of " + boolean() + " with " + expr2Type)
                        boolean()
                    case None => boolean()
                }
            case n: ExprId =>
                var currentSymbolTable: Option[SymbolTable] = Some(symbolTable)
                breakable {
                    while (true) {
                        currentSymbolTable match {
                            case Some(table) =>
                                if (table.containsSymbol(n.id.id))
                                    break
                                currentSymbolTable = table.parentSymbolTable
                            case None => UseBeforeDeclaration("Symbol " + n.id.id + " was used before declared.")
                        }
                    }
                }

                val node = currentSymbolTable.get
                    .getTableEntry(n.id.id, SymbolTableType.Variable)
                    ._4

                var idType: Option[Type] = None
                node match {
                    case n: VarDecl => idType = Some(n.varType)
                    case n: Identifier => idType = Some(symbolTable
                        .scope.asInstanceOf[MethodDecl]
                        .methodParams.filter( param => param._2 == n).head._1)
                }

                n.expr2 match {
                    case Some(expr) =>
                        expr match {
                            case _: ArrayLength =>
                                if (idType.get != IntArray())
                                    TypeMismatchError(".length expects a symbol of type " + IntArray())
                                    expression2TypeCheck(symbolTable, expr)
                                int()
                            case _: ExprArray => expression2TypeCheck(symbolTable, expr)
                            case _ => ???
                        }
                    case None => idType.get
                }
            case n: NewIntArrayDecl =>
                val sizeExpr = expressionTypeCheck(symbolTable, n.expr)
                if (sizeExpr != int())
                    TypeMismatchError("Expecting type " + int() + " for size of array. Got type " + sizeExpr)
                n.expr2 match {
                    case Some(expr) =>
                      expr match {
                          case _: ArrayLength =>
                              expression2TypeCheck(symbolTable, expr)
                          case _ =>
                              OperationNotSupported("Operation not supported for declaring new int[]")
                              int()
                      }
                    case None => IntArray()
                }
            case n: ExprThis =>

                def getClassType(symbolTable: SymbolTable): Option[ClassDecl] = {
                    symbolTable.parentSymbolTable match {
                        case Some(table) =>
                            table.scope match {
                                case _: ClassDecl => Some(table.scope.asInstanceOf[ClassDecl])
                                case _ => KeywordThisUsedInMainError()
                            }
                        case None => KeywordThisUsedInMainError()
                    }
                    None
                }
                val thisType: Option[ClassDecl] = getClassType(symbolTable)

                n.expr2 match {
                    case Some(expr) => expression2TypeCheck(symbolTable, expr)
                    case None => ClassType(thisType.get.ClassName)

                }
            case n: NewClassDecl =>
                n.expr2 match {
                    case Some(expr) =>
                        expression2TypeCheck(symbolTable, expr)
                    case None => ClassType(n.ClassName)
                }
            case n: ExprNot =>
                val exprType = expressionTypeCheck(symbolTable, n.expr)
                if (exprType != boolean())
                    TypeMismatchError("Not (!) operator expecting type " + boolean() + ". Got type " + exprType)
                n.expr2 match {
                    case Some(expr) =>
                        expr match {
                            case _: And | _: LessThan =>
                                val expr2Type = expression2TypeCheck(symbolTable, expr)
                                if (expr2Type != boolean())
                                    TypeMismatchError("Mismatch type of " + boolean() + " with " + expr2Type)
                            case _ =>
                                OperationNotSupported("Can only perform boolean operations on booleans.")
                        }
                        boolean()
                    case None => boolean()
                }
            case n: ExprParenthesis =>
                val exprType = expressionTypeCheck(symbolTable, n.expr)
                n.expr2 match {
                    case Some(expr) =>
                        val expr2Type = expression2TypeCheck(symbolTable, expr)
                        if (exprType != expr2Type)
                            TypeMismatchError("Mismatch type of " + exprType + " with " + expr2Type)
                        exprType
                    case None => exprType
                }
        }

    }

    def expression2TypeCheck(symbolTable: SymbolTable, node: Expression2): Type = {
        node match {
            case n: Operator =>
                val exprType = expressionTypeCheck(symbolTable, n.expr)
                n.expr2 match {
                    case Some(expr) =>
                        val expr2Type = expression2TypeCheck(symbolTable, expr)
                        if (expr2Type != expressionTypeCheck(symbolTable, n.expr))
                            TypeMismatchError("Mismatch type of " + exprType + " with " + expr2Type)
                        exprType
                    case None => exprType
                }
            case n: ExprArray =>
                val exprType = expressionTypeCheck(symbolTable, n.expr)
                if (exprType != int())
                    TypeMismatchError("Array index expecting type " + int() + ". Got type " + exprType)
                n.expr2 match {
                    case Some(expr) =>
                        val expr2Type = expression2TypeCheck(symbolTable, expr)
                        if (expr2Type != int())
                            TypeMismatchError("Mismatch type of " + exprType + " with " + expr2Type)
                        int()
                    case None => int()
                }
            case n: ArrayLength =>
                n.expr2 match {
                    case Some(expr) =>
                        val expr2Type = expression2TypeCheck(symbolTable, expr)
                        if (expr2Type != int())
                            TypeMismatchError("Mismatch type of " + int() + " with " + expr2Type)
                        int()
                    case None => int()
                }
            case n: ExprClassMember =>
                var currentSymbolTable: Option[SymbolTable] = Some(symbolTable)

                breakable {
                    while (true) {
                        currentSymbolTable match {
                            case Some(table) =>
                                if (table.containsSymbol(n.id.id) && table.getTableEntries(n.id.id, SymbolTableType.Method).nonEmpty){

                                    if (n.memberParams.get.isEmpty) {
                                        val hasMethodWithNoParams = currentSymbolTable.get.getTableEntries(n.id.id, SymbolTableType.Method)
                                            .filter(entry => entry._4.asInstanceOf[MethodDecl].methodParams.isEmpty)

                                        if (hasMethodWithNoParams.nonEmpty) {
                                            return hasMethodWithNoParams.head._4.asInstanceOf[MethodDecl].methodType
                                        }
                                    }

                                    val exprTypes: List[Type] = n.memberParams.get.map( param => expressionTypeCheck(currentSymbolTable.get, param))

                                    val method = currentSymbolTable.get.getTableEntries(n.id.id, SymbolTableType.Method)
                                        .filter(entry => entry._4.asInstanceOf[MethodDecl].methodParams.map(param => param._1) == exprTypes)

                                    if (method.nonEmpty)
                                        return method.head._4.asInstanceOf[MethodDecl].methodType

                                }

                                table.parentSymbolTable match {
                                    case Some(pTable) =>
                                        if (pTable.getTag.equals("Program")) { // check for method override then
                                            pTable.getClassNode(table.getTag).superClass match {
                                                case Some(superClass) =>
                                                    val superClassTable = pTable.childrenSymbolTables
                                                        .filter( childTable => childTable.getTag.equals(superClass.id))
                                                        .head

                                                    if (n.memberParams.get.isEmpty) {
                                                        val hasMethodWithNoParams = superClassTable.getTableEntries(n.id.id, SymbolTableType.Method)
                                                            .filter(entry => entry._4.asInstanceOf[MethodDecl].methodParams.isEmpty)
                                                        if (hasMethodWithNoParams.isEmpty) {
                                                            UseBeforeDeclaration("Method " + n.id.id + " was used before declared.")
                                                        }

                                                        return hasMethodWithNoParams.head._4.asInstanceOf[MethodDecl].methodType
                                                    }

                                                    val exprTypes: List[Type] = n.memberParams.get.map( param => expressionTypeCheck(superClassTable, param))

                                                    val method = superClassTable.getTableEntries(n.id.id, SymbolTableType.Method)
                                                        .filter(entry => entry._4.asInstanceOf[MethodDecl].methodParams.map(param => param._1) == exprTypes)

                                                    if (method.isEmpty)
                                                        UseBeforeDeclaration("Method " + n.id.id + " was used before declared")

                                                    method.head._4.asInstanceOf[MethodDecl].methodType
                                                 /* */

                                                case None => UseBeforeDeclaration("Method " + n.id.id + " was used before declared.")
                                            }
                                        }
                                    case None =>
                                }
                                currentSymbolTable = table.parentSymbolTable
                            case None => ???


                        }
                    }

                }
                int()

        }
    }

}