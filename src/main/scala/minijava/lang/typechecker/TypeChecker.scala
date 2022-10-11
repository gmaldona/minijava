package minijava.lang.typechecker

import minijava.lang.ast._
import minijava.lang.error.{KeywordThisUsedInMainError, TypeMismatchError, TypeNotSupported, UseBeforeDeclaration}
import minijava.lang.parser.symboltable.{SymbolTable, SymbolTableType}

import scala.language.postfixOps
import scala.util.control.Breaks._

object TypeChecker {

    def typeCheck(symbolTable: SymbolTable, node: ASTNode): Unit = {
        node match {
            case _: AssignStatement => assignStatementTypeCheck(symbolTable, node.asInstanceOf[AssignStatement])
            case _: MethodDecl => returnStatementTypeCheck(symbolTable, node.asInstanceOf[MethodDecl])
        }
    }

    def methodParamTypeCheck(): Unit = ???

    def returnStatementTypeCheck(symbolTable: SymbolTable, node: MethodDecl): Unit = {
        val returnType: Type = expressionTypeCheck(symbolTable, node.returnExpr)
        if (node.methodType != returnType)
            TypeMismatchError("Got a return type of " + returnType + " when expecting a return type of " + node.methodType)
    }

    def assignStatementTypeCheck(symbolTable: SymbolTable, node: AssignStatement): Unit = {
        if (! symbolTable.containsSymbol(node.varName.id)) {
            symbolTable.parentSymbolTable match {
                case Some(table) =>
                    assignStatementTypeCheck(table, node)
                    return
                case None => UseBeforeDeclaration("Symbol " + node.varName.id + " used before declared.")
            }
        }

        val tableEntry = symbolTable.getTableEntry(node.varName.id, SymbolTableType.Variable)
        val varDeclType = tableEntry._4.asInstanceOf[VarDecl].varType
        val exprType = expressionTypeCheck(symbolTable, node.expr)
        if (varDeclType != exprType)
            TypeMismatchError("Mismatch type of " + varDeclType + " with " + exprType)
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