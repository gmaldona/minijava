package minijava.lang.ast

object ASTAliases {
    type MethodParam = (Type, Identifier)
}

sealed trait ASTNode
sealed trait Type extends ASTNode
sealed trait Statement extends ASTNode
sealed trait Expression extends ASTNode
sealed trait Expression2 extends ASTNode

abstract class Operator(_expr: Expression,
                        _expr2: Option[Expression2]) extends Expression2 {
    val expr: Expression = _expr
    val expr2: Option[Expression2] = _expr2
}
sealed trait Scope

import ASTAliases.MethodParam

case class Program(mainClass: MainClass,
                   ClassDecls: List[ClassDecl]) extends ASTNode with Scope

case class MainClass(ClassName: Identifier,
                     argName: Identifier,
                     statement: Statement) extends ASTNode with Scope

case class ClassDecl(ClassName: Identifier,
                 superClass: Option[Identifier],
                     varDecls: List[VarDecl],
                     methodDecls: List[MethodDecl]) extends ASTNode with Scope

case class VarDecl(varType: Type, varName: Identifier) extends ASTNode

case class MethodDecl(methodType: Type,
                      methodName: Identifier,
                      methodParams: List[MethodParam],
                      varDecls: List[VarDecl],
                      statements: List[Statement],
                      returnExpr: Expression) extends ASTNode with Scope

case class StatementBlock(statements: List[Statement]) extends Statement with Scope

case class IfStatement(expr: Expression,
                       statement: Statement,
                       elseStatement: Statement) extends Statement with Scope

case class WhileLoop(expr: Expression,
                     statement: Statement) extends Statement with Scope

case class ForLoop(var1Name: Identifier,
                   var1Assign: Expression,
                   conditional: Expression,
                   var2Name: Identifier,
                   var2Assign: Expression,
                   statement: Statement) extends Statement with Scope

case class PrintStatement(expr: Expression) extends Statement

case class AssignStatement(varName: Identifier,
                           expr: Expression) extends Statement

case class ArrayAssignStatement(indexExpr: Expression,
                                expr: Expression) extends Statement

case class ExprNumber(int: IntLiteral,
                      expr2: Option[Expression2]) extends Expression

abstract class ExprBoolean(_expr2: Option[Expression2]) extends Expression {
    val expr2: Option[Expression2] = _expr2
}

case class ExprTrue(_expr2: Option[Expression2]) extends ExprBoolean(_expr2)

case class ExprFalse(_expr2: Option[Expression2]) extends ExprBoolean(_expr2)

case class ExprId(id: Identifier,
                  expr2: Option[Expression2]) extends Expression

case class ExprThis(expr2: Option[Expression2]) extends Expression

case class ExprNot(expr: Expression,
                   expr2: Option[Expression2]) extends Expression

case class ExprParenthesis(expr: Expression,
                           expr2: Option[Expression2]) extends Expression

case class ExprClassMember(id: Identifier,
                           memberParams: Option[List[Expression]],
                           expr2: Option[Expression2]) extends Expression2

case class ExprArray(expr: Expression,
                     expr2: Option[Expression2]) extends Expression2

case class NewIntArrayDecl(expr: Expression,
                           expr2: Option[Expression2]) extends Expression

case class NewClassDecl(ClassName: Identifier,
                        expr2: Option[Expression2]) extends Expression

case class ArrayLength(expr2: Option[Expression2]) extends Expression2

case class Identifier(id: String) extends ASTNode {
    override def toString: String = id
}
case class IntLiteral(int: Int) extends ASTNode

case class IntArray() extends Type {
    override def toString: String = "int[]"
}
case class boolean() extends Type {
    override def toString: String = "boolean"
}
case class int() extends Type {
    override def toString: String = "int"
}
case class ClassType() extends Type

case class And(_expr: Expression,
               _expr2: Option[Expression2]) extends Operator(_expr, _expr2)

case class Addition(_expr: Expression,
                    _expr2: Option[Expression2]) extends Operator(_expr, _expr2)

case class Subtraction(_expr: Expression,
                       _expr2: Option[Expression2]) extends Operator(_expr, _expr2)

case class Multiplication(_expr: Expression,
                          _expr2: Option[Expression2]) extends Operator(_expr, _expr2)

case class LessThan(_expr: Expression,
                    _expr2: Option[Expression2]) extends Operator(_expr, _expr2)




