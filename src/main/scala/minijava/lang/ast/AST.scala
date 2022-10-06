package minijava.lang.ast

object ASTAliases {
    type MethodParam = (Type, Identifier)
}

sealed trait ASTNode
sealed trait Type extends ASTNode
sealed trait Statement extends ASTNode
sealed trait Expression extends ASTNode
sealed trait Expression2 extends ASTNode
sealed trait Operator extends ASTNode

import ASTAliases.MethodParam

case class Program(mainClass: MainKlass,
                   klassDecls: List[KlassDecl]) extends ASTNode

case class MainKlass(klassName: Identifier,
                     argName: Identifier,
                     statement: Statement) extends ASTNode

case class KlassDecl(klassName: Identifier,
                     superKlass: Option[Identifier],
                     varDecls: List[VarDecl],
                     methodDecls: List[MethodDecl]) extends ASTNode

case class VarDecl(varType: Type, varName: Identifier)

case class MethodDecl(methodType: Type,
                      methodName: Identifier,
                      methodParams: List[MethodParam],
                      varDecls: List[VarDecl],
                      statements: List[Statement],
                      returnExpr: Expression) extends ASTNode

case class StatementBlock(statements: List[Statement]) extends ASTNode

case class IfStatement(expr: Expression,
                       statement: Statement,
                       elseStatement: Statement) extends ASTNode

case class WhileLoop(expr: Expression,
                     statement: Statement) extends ASTNode

case class ForLoop(var1Name: Identifier,
                   var1Assign: Expression,
                   conditional: Statement,
                   var2Name: Identifier,
                   var2Assign: Expression,
                   statement: Statement) extends ASTNode

case class PrintStatement(expr: Expression) extends ASTNode

case class AssignStatement(expr: Expression) extends ASTNode

case class ArrayAssignStatement(indexExpr: Expression,
                                expr: Expression) extends ASTNode

case class ExprNumber(int: IntLiteral,
                      expr2: Option[Expression2]) extends ASTNode

case class ExprTrue(expr2: Option[Expression2]) extends ASTNode

case class ExprId(id: Identifier,
                  expr2: Option[Expression2]) extends ASTNode

case class ExprThis(expr2: Option[Expression2]) extends ASTNode

case class ExprNot(expr: Expression,
                   expr2: Option[Expression2]) extends ASTNode

case class ExprParenthesis(expr: Expression,
                           expr2: Option[Expression2]) extends ASTNode

case class ExprKlassMember(id: Identifier,
                           memberParams: Option[List[Expression]],
                           expr2: Option[Expression2]) extends ASTNode

case class ExprArray(expression: Expression,
                     expr2: Option[Expression2]) extends ASTNode

case class NewIntArrayDecl(expr: Expression,
                           expr2: Option[Expression2]) extends ASTNode

case class NewKlassDecl(klassName: Identifier,
                        expr2: Option[Expression2]) extends ASTNode

case class ArrayLength(expr2: Option[Expression2]) extends ASTNode

case class Identifier(id: String) extends ASTNode
case class IntLiteral(int: Int) extends ASTNode

case class IntArray() extends Type
case class Boolean() extends Type
case class Int() extends Type
case class KlassType() extends Type

case class And(expr: Expression,
               expr2: Option[Expression2]) extends Operator

case class Addition(expr: Expression,
                    expr2: Option[Expression2]) extends Operator

case class Subtraction(expr: Expression,
                       expr2: Option[Expression2]) extends Operator

case class Multiplication(expr: Expression,
                          expr2: Option[Expression2]) extends Operator

case class LessThan(expr: Expression,
                    expr2: Option[Expression2]) extends Operator




