/**
 * CSC 444 - Compiler Design
 * State University of New York, College at Oswego
 *
 * @author  Gregory Maldonado
 * @date    November 15, 2022
 * @version 1.0
 */

package minijava.lang.codegen

import minijava.lang.ast._
import minijava.lang.codegen.util.JvmTypeGen
import minijava.lang.error.parser.{ParseError, UseBeforeDeclaration}
import minijava.lang.parser.symboltable.SymbolTable
import minijava.lang.typechecker.TypeChecker
import org.objectweb.asm.{ClassWriter, Label, MethodVisitor}
import org.objectweb.asm.Opcodes._

import scala.::
import scala.annotation.tailrec

object CodeGenerationImpl extends ICodeGeneration {

//    def walkAST(node: ASTNode): Unit = {
//        node match {
//            case n: Program              => CodeGenerationImpl.visitProgram(n)
//            case n: MainClass            => CodeGenerationImpl.visitMainClass(n)
//            case n: ClassDecl            => CodeGenerationImpl.visitClassDecl(n)
//            case n: VarDecl              => CodeGenerationImpl.visitVarDecl(n)
//            case n: MethodDecl           => CodeGenerationImpl.visitMethodDecl(n)
//            case n: StatementBlock       => CodeGenerationImpl.visitStatementBlock(n)
//            case n: IfStatement          => CodeGenerationImpl.visitIfStatement(n)
//            case n: WhileLoop            => CodeGenerationImpl.visitWhileLoop(n)
//            case n: ForLoop              => CodeGenerationImpl.visitForLoop(n)
//            case n: AssignStatement      => CodeGenerationImpl.visitAssignStatement(n)
//            case n: ArrayAssignStatement => CodeGenerationImpl.visitArrayAssignStatement(n)
//        }
//    }
    var symbolTable: SymbolTable = null
    def setSymbolTable(st: SymbolTable): Unit = symbolTable = st
    type ClassWriterT = (ClassWriter, String)

    def generate(node: Program): List[ClassWriterT] = visitProgram(node)

    @tailrec
    def getExpr2Type(expr: Expression, expr2: Expression2): Type = {
        expr2 match {
            case n: ExprClassMember =>
                n.expr2 match {
                    case Some(e) => getExpr2Type(expr, e)
                    case None    => getMethodType(expr.asInstanceOf[NewClassDecl].ClassName.id, n.id.id)
                }
        }
    }

    @tailrec
    def getExprType(expr: Expression): Type = {
                expr match {
                    case _: ExprNumber => int()
                    case _: ExprTrue | _: ExprFalse => boolean()
                    case n: NewIntArrayDecl         => IntArray()
                    case n: ExprId       => ???
                    case n: ExprThis     =>
                        n.expr2 match {
                            case Some(expr) => ???
                            case None => ???
                        }
                    case n: NewClassDecl =>
                        n.expr2 match {
                            case Some(expr) => getExpr2Type(n, expr)
                            case None => ClassType(n.ClassName)
                        }
                    case n: ExprNot      => getExprType(n)
                    case n: ExprParenthesis => getExprType(n.expr)
                }
    }

    def methodWalker(node: ASTNode, methodVisitor: MethodVisitor): Unit = {
        node match {
            case n: ExprThis             => CodeGenerationImpl.visitThis(n, methodVisitor)
            case n: NewClassDecl         => CodeGenerationImpl.visitNewClassDecl(n, methodVisitor)
            case n: StatementBlock       => CodeGenerationImpl.visitStatementBlock(n, methodVisitor)
            case n: IfStatement          => CodeGenerationImpl.visitIfStatement(n, methodVisitor)
            case n: WhileLoop            => CodeGenerationImpl.visitWhileLoop(n, methodVisitor)
            case n: ForLoop              => CodeGenerationImpl.visitForLoop(n, methodVisitor)
            case n: PrintStatement       => CodeGenerationImpl.visitPrintStatement(n, methodVisitor)
            case n: ArrayAssignStatement => CodeGenerationImpl.visitArrayAssignStatement(n)
            case n: ExprNumber           => CodeGenerationImpl.visitIntegerExpr(n.int, methodVisitor)
            case n: ExprBoolean          => CodeGenerationImpl.visitBooleanExpr(n, methodVisitor)
            case n: AssignStatement      =>
        }
    }

    def visitNewClassDecl(klass: NewClassDecl, methodVisitor: MethodVisitor): Unit = {
        methodVisitor.visitTypeInsn(NEW, klass.ClassName.id)
        methodVisitor.visitInsn(DUP)
        methodVisitor.visitMethodInsn(INVOKESPECIAL, klass.ClassName.id, "<init>", "()V", false)

        klass.expr2 match {
            case Some(expr) =>
                val castExpr = expr.asInstanceOf[ExprClassMember]
                val klassNode = symbolTable.getClassNode(klass.ClassName.id)
                for (method <- klassNode.methodDecls) {
                    if (method.methodName.id.equals(castExpr.id.id)) {
                        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, klass.ClassName.id, method.methodName.id, "()"+JvmTypeGen.get(method.methodType), false)
                    }
                }

            case None =>
        }
    }

    def visitBooleanExpr(boolean: ExprBoolean, methodVisitor: MethodVisitor): Unit = {
        boolean match {
            case _: ExprTrue  => methodVisitor.visitInsn(ICONST_1)
            case _: ExprFalse => methodVisitor.visitInsn(ICONST_0)
            case _ => ??? // should not exist lol
        }
    }

    def visitThis(exprThis: ExprThis, methodVisitor: MethodVisitor): Unit = {
        methodVisitor.visitIntInsn(ALOAD, 0)
    }

    def visitIntegerExpr(IntLit: IntLiteral, methodVisitor: MethodVisitor): Unit = {
        methodVisitor.visitLdcInsn(IntLit.int)
    }


    override def visitProgram(program: Program): List[ClassWriterT] = {
       var classWriters = List((visitMainClass(program.mainClass), program.mainClass.ClassName.id))

        for (classDecl <- program.ClassDecls) {
            classWriters = classWriters.:: (visitClassDecl(classDecl), classDecl.ClassName.id)
        }

        classWriters
    }

    override def visitMainClass(mainClass: MainClass): ClassWriter = {
        val cw: ClassWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES)

        cw.visit(V1_8,
            ACC_PUBLIC,
            mainClass.ClassName.id,
            null,
            "java/lang/Object",
            null
        )

        visitMainMethodDecl(mainClass.statement, cw)

        cw.visitEnd()

        cw
    }

    override def visitClassDecl(classDecl: ClassDecl): ClassWriter = {
        val cw: ClassWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES)

        classDecl.superClass match {
            case Some(superClass) =>
                cw.visit(V1_8,
                    ACC_PUBLIC,
                    classDecl.ClassName.id,
                    null,
                    superClass.id,
                    null
                    )
            case None =>
                cw.visit(V1_8,
                    ACC_PUBLIC,
                    classDecl.ClassName.id,
                    null,
                    "java/lang/Object",
                    null)
        }

        val constructorMethodVisitor = cw.visitMethod(
            ACC_PUBLIC,
            "<init>",
            "()V",
            null,
            null
        )

        constructorMethodVisitor.visitCode()
        constructorMethodVisitor.visitVarInsn(ALOAD, 0)
        if (classDecl.superClass.isEmpty) {
            constructorMethodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
        } else {
            constructorMethodVisitor.visitMethodInsn(INVOKESPECIAL, classDecl.superClass.get.id, "<init>", "()V", false)
        }
        constructorMethodVisitor.visitInsn(RETURN)
        constructorMethodVisitor.visitMaxs(-1,-1)
        constructorMethodVisitor.visitEnd()

        for (variable <- classDecl.varDecls) {
            visitVarDecl(variable, cw)
        }

        for (method <- classDecl.methodDecls) {
            visitMethodDecl(method, cw)
        }

        cw.visitEnd()

        cw
    }

    override def visitMainMethodDecl(statement: Statement, cw: ClassWriter): MethodVisitor = {
        val mainMethodVisitor = cw.visitMethod(
            ACC_PUBLIC | ACC_STATIC,
            "main",
            "([Ljava/lang/String;)V",
            null,
            null
        )

        mainMethodVisitor.visitCode()

        methodWalker(statement, mainMethodVisitor)

        mainMethodVisitor.visitInsn(RETURN)
        mainMethodVisitor.visitEnd()
        mainMethodVisitor.visitMaxs(-1,-1)

        mainMethodVisitor
    }

    override def visitMethodDecl(methodDecl: MethodDecl, cw: ClassWriter): MethodVisitor = {
        val jvmParamType = new StringBuilder()
        for (paramT <- methodDecl.methodParams.map(param => param._1)) {
            jvmParamType.append(JvmTypeGen.get(paramT))
        }

        val jvmReturnType = JvmTypeGen.get(methodDecl.methodType)

        val methodVisitor = cw.visitMethod(
            ACC_PUBLIC,
            methodDecl.methodName.id,
            "(" + jvmParamType.toString + ")" + jvmReturnType,
            null,
            null
        )

        methodVisitor.visitCode()

        visitVarDecl(methodDecl, methodVisitor)

        for (statement <- methodDecl.statements) {
            methodWalker(statement, methodVisitor)
        }

        methodWalker(methodDecl.returnExpr, methodVisitor)

        methodDecl.methodType match {
            case t: int     => methodVisitor.visitInsn(IRETURN)
            case b: boolean => ???
            case _ => methodVisitor.visitInsn(RETURN)
        }
        methodVisitor.visitEnd()
        methodVisitor.visitMaxs(-1,-1)

        methodVisitor
    }

    override def visitVarDecl(varDecl: VarDecl, cw: ClassWriter): Unit = {
        val fieldVisitor = cw.visitField(
            0,
            varDecl.varName.id,
            util.JvmTypeGen.get(varDecl.varType),
            null,
            null
        )
        fieldVisitor.visitEnd()
    }

    override def visitStatementBlock(statementBlock: StatementBlock, methodVisitor: MethodVisitor): Unit = {
        for (statement <- statementBlock.statements) {
            methodWalker(statement, methodVisitor)
        }
    }

    override def visitIfStatement(ifStatement: IfStatement, methodVisitor: MethodVisitor): Unit = {
        val ifTrueLabel  = new Label()
        val ifFalseLabel = new Label()
        methodWalker(ifStatement.expr, methodVisitor)
        methodVisitor.visitJumpInsn(IFEQ, ifTrueLabel)
        methodWalker(ifStatement.statement, methodVisitor)
        methodVisitor.visitJumpInsn(GOTO, ifFalseLabel)
        methodVisitor.visitLabel(ifTrueLabel)
        methodWalker(ifStatement.elseStatement, methodVisitor)
        methodVisitor.visitLabel(ifFalseLabel)
    }

    override def visitWhileLoop(whileLoop: WhileLoop, methodVisitor: MethodVisitor): Unit = {
        val whileBegin = new Label()
        val whileEnd   = new Label()
        methodVisitor.visitLabel(whileBegin)
        methodWalker(whileLoop.expr, methodVisitor)
        methodVisitor.visitJumpInsn(IFEQ, whileEnd)
        methodWalker(whileLoop.statement, methodVisitor)
        methodVisitor.visitJumpInsn(GOTO, whileBegin)
        methodVisitor.visitLabel(whileEnd)
    }

    override def visitForLoop(forLoop: ForLoop, methodVisitor: MethodVisitor): Unit = {
    }

    override def visitPrintStatement(printStatement: PrintStatement, methodVisitor: MethodVisitor): Unit = {
        methodVisitor.visitFieldInsn(
            GETSTATIC,
            "java/lang/System",
            "out",
            "Ljava/io/PrintStream;"
        )

        methodWalker(printStatement.expr, methodVisitor)

        methodVisitor.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/io/PrintStream",
            "println",
            "(" + JvmTypeGen.get(getExprType(printStatement.expr)) + ")V",
            false
        )
    }

    override def visitAssignStatement(assignStatement: AssignStatement, methodVisitor: MethodVisitor): Unit = {
     ???
    }

    override def visitArrayAssignStatement(arrayAssignStatement: ArrayAssignStatement): Unit = ???

    override def visitVarDecl(methodDecl: MethodDecl, methodVisitor: MethodVisitor): Unit = {
        val methodStart = new Label()
        methodVisitor.visitLabel(methodStart)
        methodVisitor.visitInsn(ICONST_4)
        methodVisitor.visitVarInsn(ISTORE, 1)

//        var istore      = methodDecl.methodParams.size + 1
//        for (varDecl <- methodDecl.varDecls) {
//
//            methodVisitor.visitLocalVariable(
//                varDecl.varName.id,
//                util.JvmTypeGen.get(varDecl.varType),
//                null,
//                methodStart,
//                methodEnd,
//                istore
//            )
//            istore = istore.+(1)
//        }
    }

    def getMethodType(className: String, methodName: String): Type = {
        val classNode = symbolTable.getClassNode(className)
        for (method <- classNode.methodDecls) {
            if (method.methodName.id.equals(methodName)) {
                return method.methodType
            }
        }
        UseBeforeDeclaration(methodName + " does not exist.")
        int()
    }
}
