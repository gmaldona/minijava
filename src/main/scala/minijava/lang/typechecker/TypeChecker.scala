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

    /** Returns the type of the expression node
     * @param symbolTable Symbol Table that contains the scope of the expression
     * @param node        Node that contains the Expression
     *
     * @return The expression Type
     */
    def expressionTypeCheck(symbolTable: SymbolTable, node: Expression): Type = {
        node match {
            // Checks the type for a node of ExprNumber - should have a type of int()
            case n: ExprNumber =>
                n.expr2 match {
                    case Some(expr) =>
                        val expr2Type = expression2TypeCheck(symbolTable, expr)
                        if (expr2Type != int())
                            TypeMismatchError("Mismatch type of " + int() + " with " + expr2Type)
                        int()
                    case None => int()
                }

            // Checks the type of a node of ExprBoolean - should have a type of boolean()
            case n: ExprBoolean =>
                n.expr2 match {
                    case Some(expr) =>
                        val expr2Type = expression2TypeCheck(symbolTable, expr)
                        if (expr2Type != boolean())
                            TypeMismatchError("Mismatch type of " + boolean() + " with " + expr2Type)
                        boolean()
                    case None => boolean()
                }

            // Checks the type of a node of ExprId - should have a type of the Identifier()
            case n: ExprId =>
                /**
                 * @param symbolTable Recursed Symbol table
                 * @param symbol      Symbol searched over each Symbol Table to be found
                 *
                 * @return A Symbol Table that contains the symbol
                 * @throws UseBeforeDeclaration Use Before Declaration on the symbol if the symbol was used before declared
                 * */
                def getExprIdSymbolTable(symbolTable: SymbolTable, symbol: String): Option[SymbolTable] = {
                    if (symbolTable.containsSymbol(symbol))
                        return Some(symbolTable)

                    symbolTable.parentSymbolTable match {
                        case Some(table) => getExprIdSymbolTable(table, symbol)
                        case None => UseBeforeDeclaration("Symbol " + n.id.id + " was used before declared.")
                    }
                    None
                }

                val node = getExprIdSymbolTable(symbolTable, n.id.id).get
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

            // Checks the type of a node NewIntArrayDecl - Should have a type of intArray()
            case n: NewIntArrayDecl =>
                val sizeExpr = expressionTypeCheck(symbolTable, n.expr)
                if (sizeExpr != int())
                    TypeMismatchError("Expecting type " + int() + " for size of array. Got type " + sizeExpr)
                n.expr2 match {
                    case Some(expr) =>
                      expr match {
                          case _: ArrayLength => expression2TypeCheck(symbolTable, expr)
                          case _ => OperationNotSupported("Operation not supported for declaring new int[]")
                      }
                    case None =>
                }
                IntArray()

            // Checks the type of a node ExprThis - Should have a type of the scope
            case n: ExprThis =>

                /**
                 * @param symbolTable Recursed Symbol Table
                 *
                 * @return The ClassDecl Node that is the type of this
                 * @throws KeywordThisUsedInMainError if the recursion does not find a ClassDecl Node
                 */
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

            // Checks the type of a node of NewClassDecl - Should have a type of the Identifier
            case n: NewClassDecl =>
                n.expr2 match {
                    case Some(expr) => expression2TypeCheck(symbolTable, expr)
                    case None => ClassType(n.ClassName)
                }

            // Checks the type of a node of ExprNot - should have a type of boolean to be negated
            case n: ExprNot =>
                val exprType = expressionTypeCheck(symbolTable, n.expr)
                if (exprType != boolean())
                    TypeMismatchError("Not (!) operator expecting type " + boolean() + ". Got type " + exprType)
                n.expr2 match {
                    case Some(expr) => expr match {
                            case _: And | _: LessThan =>
                                val expr2Type = expression2TypeCheck(symbolTable, expr)
                                if (expr2Type != boolean()) TypeMismatchError("Mismatch type of " + boolean() + " with " + expr2Type)
                            case _ =>
                                OperationNotSupported("Can only perform boolean operations on booleans.")
                        }
                    case None =>
                }
                boolean()

            // Checks the type of a node of ExprParenthesis - will call the expression function and return the type of the inner expression
            case n: ExprParenthesis =>
                val exprType = expressionTypeCheck(symbolTable, n.expr)
                n.expr2 match {
                    case Some(expr) =>
                        val expr2Type = expression2TypeCheck(symbolTable, expr)
                        if (exprType != expr2Type) TypeMismatchError("Mismatch type of " + exprType + " with " + expr2Type)
                    case None =>
                }
                exprType
        }
    }

    def expression2TypeCheck(symbolTable: SymbolTable, node: Expression2): Type = {
        node match {
            // Check the type of a node of Operator - will call the expression function and return the type of the expr
            case n: Operator =>
                val exprType = expressionTypeCheck(symbolTable, n.expr)
                n.expr2 match {
                    case Some(expr) =>
                        val expr2Type = expression2TypeCheck(symbolTable, expr)
                        if (expr2Type != expressionTypeCheck(symbolTable, n.expr))
                            TypeMismatchError("Mismatch type of " + exprType + " with " + expr2Type)
                    case None =>
                }
                exprType

            // Checks the type of a node of ExprArray - Should have a type of int()
            case n: ExprArray =>
                val exprType = expressionTypeCheck(symbolTable, n.expr)
                if (exprType != int())
                    TypeMismatchError("Array index expecting type " + int() + ". Got type " + exprType)
                n.expr2 match {
                    case Some(expr) =>
                        val expr2Type = expression2TypeCheck(symbolTable, expr)
                        if (expr2Type != int())
                            TypeMismatchError("Mismatch type of " + exprType + " with " + expr2Type)
                    case None =>
                }
                int()

            // Checks the type of a node of ArrayLength - Should have a type of int
            case n: ArrayLength =>
                n.expr2 match {
                    case Some(expr) =>
                        val expr2Type = expression2TypeCheck(symbolTable, expr)
                        if (expr2Type != int())
                            TypeMismatchError("Mismatch type of " + int() + " with " + expr2Type)
                    case None =>
                }
                int()

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
                            case None =>
                        }
                    }

                }
                int()

        }
    }

}