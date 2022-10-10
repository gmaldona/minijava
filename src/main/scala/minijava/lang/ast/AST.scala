package minijava.lang.ast

object ASTAliases {
    type MethodParam = (Type, Identifier)
}

sealed trait ASTNode
sealed trait Type extends ASTNode
sealed trait Statement extends ASTNode
sealed trait Expression extends ASTNode
sealed trait Expression2 extends ASTNode
sealed trait Operator extends Expression2
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

case class AssignStatement(expr: Expression) extends Statement

case class ArrayAssignStatement(indexExpr: Expression,
                                expr: Expression) extends Statement

case class ExprNumber(int: IntLiteral,
                      expr2: Option[Expression2]) extends Expression

case class ExprTrue(expr2: Option[Expression2]) extends Expression

case class ExprFalse(expr2: Option[Expression2]) extends Expression

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

case class Identifier(id: String) extends ASTNode
case class IntLiteral(int: Int) extends ASTNode

case class IntArray() extends Type
case class Boolean() extends Type
case class int() extends Type
case class ClassType() extends Type

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




