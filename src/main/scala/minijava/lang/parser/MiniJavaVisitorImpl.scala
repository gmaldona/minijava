/**
 * CSC 444 - Compiler Design
 * State University of New York, College at Oswego
 *
 * @author  Gregory Maldonado
 * @date    November 15, 2022
 * @version 1.0
 */

package minijava.lang.parser

import antlr4.{MiniJavaBaseVisitor, MiniJavaParser}
import minijava.lang.ast.ASTAliases.MethodParam
import minijava.lang.ast.VariableScope.{ClassVariable, LocalVariable}
import minijava.lang.ast._

import scala.language.postfixOps

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
        val className = Identifier(ctx.Identifier().get(0).getText)
        val argName   = Identifier(ctx.Identifier().get(1).getText)
        val statement = visit(ctx.statement()).asInstanceOf[Statement]

        MainClass(className, argName, statement)
    }

    override def visitClassDeclaration(ctx: MiniJavaParser.ClassDeclarationContext): ASTNode = {
        val className  = Identifier(ctx.Identifier().get(0).getText)
        val superClass = if (ctx.Identifier().size() > 1)
                             Some(Identifier(ctx.Identifier().get(1).getText)) else None

        var varDecls: List[VarDecl] = List()
        val varIter = ctx.varDeclaration().iterator()
        while (varIter.hasNext) {
            varDecls = visit(varIter.next()).asInstanceOf[VarDecl] :: varDecls
        }

        varDecls.foreach( varDecl => varDecl.varScope = Some(ClassVariable()))

        var methodDecls: List[MethodDecl] = List()
        val methodIter = ctx.methodDeclaration().iterator()
        while (methodIter.hasNext) {
            methodDecls = visit(methodIter.next()).asInstanceOf[MethodDecl] :: methodDecls
        }

        ClassDecl(className, superClass, varDecls, methodDecls)
    }

    override def visitVarDeclaration(ctx: MiniJavaParser.VarDeclarationContext): ASTNode = {
        val varName = Identifier(ctx.Identifier().getText)
        val varType = visit(ctx.`type`()).asInstanceOf[Type]

        VarDecl(varType, varName)
    }

    override def visitMethodDeclaration(ctx: MiniJavaParser.MethodDeclarationContext): ASTNode = {
        val methodType = visit(ctx.`type`().get(0)).asInstanceOf[Type]
        val methodName = Identifier(ctx.Identifier().get(0).getText)

        var parameters: List[MethodParam] = List()
        val typeIter = ctx.`type`().iterator()
        val idIter   = ctx.Identifier().iterator()
        typeIter.next()
        idIter.next()

        while (typeIter.hasNext) {
            parameters = (
                visit(typeIter.next()).asInstanceOf[Type], Identifier(idIter.next().getText)
            ) :: parameters
        }

        var varDecls: List[VarDecl] = List()
        val varIter = ctx.varDeclaration().iterator()
        while (varIter.hasNext) {
            varDecls = visit(varIter.next()).asInstanceOf[VarDecl] :: varDecls
        }

        varDecls.foreach( varDecl => varDecl.varScope = Some(LocalVariable()))

        var statements: List[Statement] = List()
        val statementIter = ctx.statement().iterator()
        while (statementIter.hasNext) {
            statements = visit(statementIter.next()).asInstanceOf[Statement] :: statements
        }

        val returnExpr = visit(ctx.expression()).asInstanceOf[Expression]

        MethodDecl(methodType, methodName, parameters, varDecls, statements, returnExpr)
    }

    override def visitIntArrayType(ctx: MiniJavaParser.IntArrayTypeContext): ASTNode = {
        IntArray()
    }

    override def visitBoolType(ctx: MiniJavaParser.BoolTypeContext): ASTNode = {
        boolean()
    }

    override def visitIntType(ctx: MiniJavaParser.IntTypeContext): ASTNode = {
        int()
    }

    override def visitIdType(ctx: MiniJavaParser.IdTypeContext): ASTNode = {
        ClassType(Identifier(ctx.Identifier().getText))
    }

    override def visitStatementBlock(ctx: MiniJavaParser.StatementBlockContext): ASTNode = {
        var statementBlocks: List[Statement] = List()
        val statementIter = ctx.statement().iterator()
        while (statementIter.hasNext) {
            statementBlocks = visit(statementIter.next()).asInstanceOf[Statement] :: statementBlocks
        }

        StatementBlock(statementBlocks)
    }

    override def visitIfStatement(ctx: MiniJavaParser.IfStatementContext): ASTNode = {
        val expr          = visit(ctx.expression()).asInstanceOf[Expression]
        val statement     = visit(ctx.statement().get(0)).asInstanceOf[Statement]
        val elseStatement = visit(ctx.statement().get(1)).asInstanceOf[Statement]

        IfStatement(expr, statement, elseStatement)
    }

    override def visitWhileLoop(ctx: MiniJavaParser.WhileLoopContext): ASTNode = {
        val expr      = visit(ctx.expression()).asInstanceOf[Expression]
        val statement = visit(ctx.statement()).asInstanceOf[Statement]

        WhileLoop(expr, statement)
    }

    override def visitForLoop(ctx: MiniJavaParser.ForLoopContext): ASTNode = {

        val var1Name    = Identifier(ctx.Identifier().get(0).getText)
        val var2Name    = Identifier(ctx.Identifier().get(1).getText)
        val var1Assign  = visit(ctx.expression().get(0)).asInstanceOf[Expression]
        val var2Assign  = visit(ctx.expression().get(2)).asInstanceOf[Expression]
        val conditional = visit(ctx.expression().get(1)).asInstanceOf[Expression]
        val statement   = visit(ctx.statement()).asInstanceOf[Statement]

        ForLoop(var1Name, var1Assign, conditional, var2Name, var2Assign, statement)
    }

    override def visitPrintExpr(ctx: MiniJavaParser.PrintExprContext): ASTNode = {
         val expr = visit(ctx.expression()).asInstanceOf[Expression]

        PrintStatement(expr)
    }

    override def visitAssign(ctx: MiniJavaParser.AssignContext): ASTNode = {
        val varName = Identifier(ctx.Identifier().getText)
         val expr = visit(ctx.expression()).asInstanceOf[Expression]

        AssignStatement(varName, expr)
    }

    override def visitArrayAssign(ctx: MiniJavaParser.ArrayAssignContext): ASTNode = {
        val indexExpr = visit(ctx.expression().get(0)).asInstanceOf[Expression]
        val expr      = visit(ctx.expression().get(1)).asInstanceOf[Expression]

        ArrayAssignStatement(indexExpr, expr)
    }

    override def visitExprNumber(ctx: MiniJavaParser.ExprNumberContext): ASTNode = {
        val intLit = IntLiteral(ctx.IntegerLiteral().getText.toInt)
        val expr   = if (ctx.expression2().children != null)
                        Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        ExprNumber(intLit, expr)
    }

    override def visitExprTrue(ctx: MiniJavaParser.ExprTrueContext): ASTNode = {
        val expr   = if (ctx.expression2().children != null)
                        Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        ExprTrue(expr)
    }

    override def visitExprFalse(ctx: MiniJavaParser.ExprFalseContext): ASTNode = {
        val expr   = if (ctx.expression2().children != null)
            Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        ExprFalse(expr)
    }

    override def visitExprId(ctx: MiniJavaParser.ExprIdContext): ASTNode = {

        val id = Identifier(ctx.Identifier().getText)
        val expr2 = if (ctx.expression2().children != null)
            Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        ExprId(id, expr2)
    }

    override def visitExprThis(ctx: MiniJavaParser.ExprThisContext): ASTNode = {

        val expr   = if (ctx.expression2().children != null)
            Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        ExprThis(expr)
    }

    override def visitNewIntArray(ctx: MiniJavaParser.NewIntArrayContext): ASTNode = {
        val index = visit(ctx.expression()).asInstanceOf[Expression]
        val expr   = if (ctx.expression2().children != null)
                        Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        NewIntArrayDecl(index, expr)
    }

    override def visitNewObject(ctx: MiniJavaParser.NewObjectContext): ASTNode = {
        val className = Identifier(ctx.Identifier().getText)
        val expr   = if (ctx.expression2().children != null)
                        Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        NewClassDecl(className, expr)
    }

    override def visitExprNot(ctx: MiniJavaParser.ExprNotContext): ASTNode = {
        val expr = visit(ctx.expression()).asInstanceOf[Expression]
        val expr2   = if (ctx.expression2().children != null)
            Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        ExprNot(expr, expr2)
    }

    override def visitExprParenthesis(ctx: MiniJavaParser.ExprParenthesisContext): ASTNode = {
        val expr = visit(ctx.expression()).asInstanceOf[Expression]
        val expr2   = if (ctx.expression2().children != null)
            Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        ExprParenthesis(expr, expr2)
    }

    override def visitExprClassMember(ctx: MiniJavaParser.ExprClassMemberContext): ASTNode = {
        val className = Identifier(ctx.Identifier().getText)
        val expr      = if (ctx.expression2().children != null)
                            Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        var memberParams: List[Expression] = List()
        val memberParamIter = ctx.expression().iterator()
        while (memberParamIter.hasNext) {
            memberParams = visit(memberParamIter.next()).asInstanceOf[Expression] :: memberParams
        }

        ExprClassMember(className, Some(memberParams), expr)
    }

    override def visitExprLength(ctx: MiniJavaParser.ExprLengthContext): ASTNode = {
        val expr = if (ctx.expression2().children != null)
            Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        ArrayLength(expr)
    }

    override def visitExprArray(ctx: MiniJavaParser.ExprArrayContext): ASTNode = {
        val expr = visit(ctx.expression()).asInstanceOf[Expression]
        val expr2   = if (ctx.expression2().children != null)
            Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        ExprArray(expr, expr2)
    }

    override def visitExprOp(ctx: MiniJavaParser.ExprOpContext): ASTNode = {

        val expr = visit(ctx.expression()).asInstanceOf[Expression]
        val expr2   = if (ctx.expression2().children != null)
            Some(visit(ctx.expression2()).asInstanceOf[Expression2]) else None

        ctx.children.get(0).getText match {
            case "&&" => And(expr, expr2)
            case "<"  => LessThan(expr, expr2)
            case "+"  => Addition(expr, expr2)
            case "-"  => Subtraction(expr, expr2)
            case "*"  => Multiplication(expr, expr2)

            case _    => throw new Exception()
        }
    }

}
