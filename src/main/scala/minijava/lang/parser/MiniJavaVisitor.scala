package minijava.lang.parser

import antlr4.{MiniJavaBaseVisitor, MiniJavaParser}
import minijava.lang.ast.ASTNode

class MiniJavaVisitorImpl extends MiniJavaBaseVisitor[ASTNode] {

    override def visitProgram(ctx: MiniJavaParser.ProgramContext): ASTNode = ???

    override def visitMainClass(ctx: MiniJavaParser.MainClassContext): ASTNode = ???

    override def visitClassDeclaration(ctx: MiniJavaParser.ClassDeclarationContext): ASTNode = ???

    override def visitVarDeclaration(ctx: MiniJavaParser.VarDeclarationContext): ASTNode = ???

    override def visitMethodDeclaration(ctx: MiniJavaParser.MethodDeclarationContext): ASTNode = ???

    override def visitIntArrayType(ctx: MiniJavaParser.IntArrayTypeContext): ASTNode = ???

    override def visitBoolType(ctx: MiniJavaParser.BoolTypeContext): ASTNode = ???

    override def visitIntType(ctx: MiniJavaParser.IntTypeContext): ASTNode = ???

    override def visitIdType(ctx: MiniJavaParser.IdTypeContext): ASTNode = ???

    override def visitStatementBlock(ctx: MiniJavaParser.StatementBlockContext): ASTNode = ???

    override def visitIfStatement(ctx: MiniJavaParser.IfStatementContext): ASTNode = ???

    override def visitWhileLoop(ctx: MiniJavaParser.WhileLoopContext): ASTNode = ???

    override def visitForLoop(ctx: MiniJavaParser.ForLoopContext): ASTNode = ???

    override def visitPrintExpr(ctx: MiniJavaParser.PrintExprContext): ASTNode = ???

    override def visitAssign(ctx: MiniJavaParser.AssignContext): ASTNode = ???

    override def visitArrayAssign(ctx: MiniJavaParser.ArrayAssignContext): ASTNode = ???

    override def visitExprNumber(ctx: MiniJavaParser.ExprNumberContext): ASTNode = ???

    override def visitExprTrue(ctx: MiniJavaParser.ExprTrueContext): ASTNode = ???

    override def visitExprFalse(ctx: MiniJavaParser.ExprFalseContext): ASTNode = ???

    override def visitExprId(ctx: MiniJavaParser.ExprIdContext): ASTNode = ???

    override def visitExprThis(ctx: MiniJavaParser.ExprThisContext): ASTNode = ???

    override def visitNewIntArray(ctx: MiniJavaParser.NewIntArrayContext): ASTNode = ???

    override def visitNewObject(ctx: MiniJavaParser.NewObjectContext): ASTNode = ???

    override def visitExprNot(ctx: MiniJavaParser.ExprNotContext): ASTNode = ???

    override def visitExprParenthesis(ctx: MiniJavaParser.ExprParenthesisContext): ASTNode = ???

    override def visitExprKlassMember(ctx: MiniJavaParser.ExprKlassMemberContext): ASTNode = ???

    override def visitExprLength(ctx: MiniJavaParser.ExprLengthContext): ASTNode = ???

    override def visitExprArray(ctx: MiniJavaParser.ExprArrayContext): ASTNode = ???

    override def visitExprOp(ctx: MiniJavaParser.ExprOpContext): ASTNode = ???

}
