package minijava.lang.typechecker

import minijava.lang.ast._
import minijava.lang.error.{KeywordThisUsedInMainError, TypeMismatchError, TypeNotSupported, UseBeforeDeclaration}
import minijava.lang.parser.symboltable.{SymbolTable, SymbolTableType}

import scala.language.postfixOps
import scala.util.control.Breaks._

object TypeChecker {

    def typeCheck(symbolTable: SymbolTable, node: ASTNode): Unit = {
        node match {
            case n: AssignStatement => assignStatementTypeCheck(symbolTable, node.asInstanceOf[AssignStatement])

        }
    }

    def assignStatementTypeCheck(symbolTable: SymbolTable, node: AssignStatement): Unit = {
        if (! symbolTable.containsSymbol(node.varName.id)) {
            symbolTable.parentSymbolTable match {
                case Some(table) =>
                    assignStatementTypeCheck(table, node)
                    return
                case None        => UseBeforeDeclaration("Symbol " + node.varName.id + " used before declared.")
            }
        }

        val tableEntry = symbolTable.getTableEntry(node.varName.id, SymbolTableType.Variable)
        val varDeclType = tableEntry._4.asInstanceOf[VarDecl].varType
        val exprType = expressionTypeCheck(symbolTable, node.expr)
        if (varDeclType != exprType)
            TypeMismatchError("Mismatch type of " + varDeclType + " with " + exprType)
    }

    def expressionTypeCheck(symbolTable: SymbolTable, node: Expression): Type = {
        println(node)

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
                val currentSymbolTable: Option[SymbolTable] = Some(symbolTable)
                breakable {
                    while (true) {
                        currentSymbolTable match {
                            case Some(table) =>
                                if (table.containsSymbol(n.id.id))
                                    break
                            case None => UseBeforeDeclaration("Symbol " + n.id.id + " was used before declared.")
                        }
                    }
                }
                val idType = currentSymbolTable.get
                    .getTableEntry(n.id.id, SymbolTableType.Variable)
                    ._4.asInstanceOf[VarDecl].varType
                n.expr2 match {
                    case Some(expr) => ???
                    case None => idType
                }
            case n: NewIntArrayDecl =>
                val sizeExpr = expressionTypeCheck(symbolTable, n.expr)
                if (sizeExpr != int())
                    TypeMismatchError("Expecting type " + int() + " for size of array. Got type " + sizeExpr)
                n.expr2 match {
                    case Some(expr) => ???
                    case None => IntArray()
                }
            case n: ExprThis =>
                var thisType: Option[Scope] = None
                breakable {
                    val currentSymbolTable: Option[SymbolTable] = Some(symbolTable)
                    while (true) {
                        currentSymbolTable match {
                            case Some(table) =>
                                table.scope match {
                                    case scopeType: ClassDecl =>
                                        thisType = Some(scopeType)
                                        break
                                    case _ => KeywordThisUsedInMainError()
                                }
                            case None => KeywordThisUsedInMainError()
                        }
                    }
                }
                n.expr2 match {
                    case Some(expr) => ???
                    case None => ClassType(thisType.get.asInstanceOf[ClassDecl].ClassName)

                }
            case n: NewClassDecl =>
                n.expr2 match {
                    case Some(expr) => ???
                    case None => ClassType(n.ClassName)
                }
            case n: ExprNot => ???
            case n: ExprParenthesis => ???
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
        }
    }
}

//import scala.language.postfixOps
//
//class TypeChecker(symbolTable: SymbolTable, AST: ASTNode) {
//
//    _traverseAST(AST)
//    var currentSymbolTable: SymbolTable = symbolTable
//
//    def _traverseAST(node: ASTNode): Unit = {
//        node match {
//            case _: Program =>
//                val programNode = node.asInstanceOf[Program]
//                _traverseAST(programNode.mainClass)
//                for (classDecl <- programNode.ClassDecls) _traverseAST(classDecl)
//            case _: MainClass =>
//                val mainClassNode = node.asInstanceOf[MainClass]
//                _traverseAST(mainClassNode.statement)
//            case _: ClassDecl =>
//                val classNode = node.asInstanceOf[ClassDecl]
//                for (methodDecl <- classNode.methodDecls) _traverseAST(methodDecl)
//            case _: MethodDecl =>
//                val methodNode = node.asInstanceOf[MethodDecl]
//                //methodReturnTypeCheck(methodNode)
//                //methodParameterTypeCheck(methodNode)
//                for (statement <- methodNode.statements) _traverseAST(statement)
//            case _: StatementBlock =>
//                val statementNode = node.asInstanceOf[StatementBlock]
//                for (statement <- statementNode.statements) _traverseAST(statement)
//            case _: IfStatement =>
//                val ifStatementNode = node.asInstanceOf[IfStatement]
//                ifStatementBooleanTypeCheck(ifStatementNode)
//                _traverseAST(ifStatementNode.statement)
//                _traverseAST(ifStatementNode.elseStatement)
//            case _: WhileLoop =>
//                val whileLoopNode = node.asInstanceOf[WhileLoop]
//                whileLoopExpressionTypeCheck(whileLoopNode)
//                _traverseAST(whileLoopNode.statement)
//            case _: ForLoop =>
//                val forLoopNode = node.asInstanceOf[ForLoop]
//                forLoopExpressionTypeCheck(forLoopNode)
//                _traverseAST(forLoopNode.statement)
//            case _: AssignStatement =>
//                val assignNode = node.asInstanceOf[AssignStatement]
//                varAssignTypeCheck(assignNode)
//            case _: ArrayAssignStatement =>
//                val arrayAssignNode = node.asInstanceOf[ArrayAssignStatement]
//                arrayAssignTypeCheck(arrayAssignNode)
//            case _: ExprArray =>
//                val exprArrayNode = node.asInstanceOf[ExprArray]
//                arrayIndexTypeCheck(exprArrayNode)
//            case _: PrintStatement =>
//                val printStatementNode = node.asInstanceOf[PrintStatement]
//        }
//    }
//
//    def expressionTypeCheck(node: Expression): Type = {
//        node match {
//            case n: ExprNumber =>
//                n.expr2 match {
//                    case Some(expr) =>
//                        val expr2Type = expression2TypeCheck(expr)
//                        if (expr2Type != int())
//                            TypeMismatchError("Mismatch type of " + int() + " and " + expr2Type)
//                        int()
//                    case None => int()
//                }
//        }
//    }
//
//
//
//    def expression2TypeCheck(node: Expression2): Type = ???
//    def methodReturnTypeCheck(node: MethodDecl): Unit = ???
//    def varAssignTypeCheck(node: AssignStatement): Unit = {
//
//    }
//    def ifStatementBooleanTypeCheck(node: IfStatement): Unit = ???
//    def whileLoopExpressionTypeCheck(node: WhileLoop): Unit = ???
//    def forLoopExpressionTypeCheck(node: ForLoop): Unit = ???
//    def methodParameterTypeCheck(node: MethodDecl): Unit = ???
//    def arrayAssignTypeCheck(node: ArrayAssignStatement): Unit = ???
//    def arrayIndexTypeCheck(node: ExprArray): Unit = ???
//    def newIntArrayDeclTypeCheck(node: NewIntArrayDecl): Unit = ???
//}
