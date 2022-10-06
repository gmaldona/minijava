package minijava.lang.parser


import antlr4.MiniJavaParser.{BoolTypeContext, IdTypeContext, IntArrayTypeContext, IntTypeContext}
import antlr4.{MiniJavaBaseVisitor, MiniJavaParser}
import minijava.lang.ast._

class MiniJavaVisitorImpl extends MiniJavaBaseVisitor[ASTNode] {

    override def visitProgram(ctx: MiniJavaParser.ProgramContext): ASTNode = {
        val mainClass = visit(ctx.mainClass()).asInstanceOf[MainClass]

        var classDecls: List[ClassDecl] = List()
        val classIter = ctx.classDeclaration().iterator()
        while (classIter.hasNext) {
            classDecls = visit(classIter.next()).asInstanceOf[ClassDecl] :: classDecls
        }

        Program(mainClass, classDecls)
    }

    override def visitMainClass(ctx: MiniJavaParser.MainClassContext): ASTNode = {
        val className = visit(ctx.Identifier().get(0)).asInstanceOf[Identifier]
        val argName   = visit(ctx.Identifier().get(1)).asInstanceOf[Identifier]
        val statement = visit(ctx.statement()).asInstanceOf[Statement]

        MainClass(className, argName, statement)
    }

    override def visitClassDeclaration(ctx: MiniJavaParser.ClassDeclarationContext): ASTNode = {
        val className  = visit(ctx.Identifier().get(0)).asInstanceOf[Identifier]
        val superClass = if (ctx.Identifier().size() > 1)
                             Some(visit(ctx.Identifier().get(1))
                                 .asInstanceOf[Identifier]) else None

        var varDecls: List[VarDecl] = List()
        val varIter = ctx.varDeclaration().iterator()
        while (varIter.hasNext) {
            varDecls = visit(varIter.next()).asInstanceOf[VarDecl] :: varDecls
        }

        var methodDecls: List[MethodDecl] = List()
        val methodIter = ctx.methodDeclaration().iterator()
        while (methodIter.hasNext) {
            methodDecls = visit(methodIter.next()).asInstanceOf[MethodDecl] :: methodDecls
        }

        ClassDecl(className, superClass, varDecls, methodDecls)
    }

    override def visitVarDeclaration(ctx: MiniJavaParser.VarDeclarationContext): ASTNode = {
        val varName = visit(ctx.Identifier()).asInstanceOf[Identifier]

        ctx.`type`() match {
            case _: IdTypeContext       => VarDecl(visit(ctx.`type`()).asInstanceOf[ClassType], varName)
            case _: IntArrayTypeContext => VarDecl(visit(ctx.`type`()).asInstanceOf[IntArray], varName)
            case _: IntTypeContext      => VarDecl(visit(ctx.`type`()).asInstanceOf[Int], varName)
            case _: BoolTypeContext     => VarDecl(visit(ctx.`type`()).asInstanceOf[Boolean], varName)

            case _ => VarDecl(visit(ctx.`type`()).asInstanceOf[Type], varName)
        }
    }

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
