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
import org.objectweb.asm.{ClassWriter, MethodVisitor}
import org.objectweb.asm.Opcodes._

import scala.::

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

    type ClassWriterT = (ClassWriter, String)

    def generate(node: Program): List[ClassWriterT] = visitProgram(node)


    def methodWalker(node: ASTNode, methodVisitor: MethodVisitor): Unit = {
        node match {
            case n: VarDecl              => CodeGenerationImpl.visitVarDecl(n)
            case n: StatementBlock       => CodeGenerationImpl.visitStatementBlock(n)
            case n: IfStatement          => CodeGenerationImpl.visitIfStatement(n)
            case n: WhileLoop            => CodeGenerationImpl.visitWhileLoop(n)
            case n: ForLoop              => CodeGenerationImpl.visitForLoop(n)
            case n: PrintStatement       => CodeGenerationImpl.visitPrintStatement(n, methodVisitor)
            case n: AssignStatement      => CodeGenerationImpl.visitAssignStatement(n)
            case n: ArrayAssignStatement => CodeGenerationImpl.visitArrayAssignStatement(n)
        }
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

        for (statement <- methodDecl.statements) {
            methodWalker(statement, methodVisitor)
        }

        methodVisitor.visitInsn(RETURN)
        methodVisitor.visitEnd()
        methodVisitor.visitMaxs(-1,-1)

        methodVisitor
    }

    override def visitVarDecl(varDecl: VarDecl): Unit = ???

    override def visitStatementBlock(statementBlock: StatementBlock): Unit = ???

    override def visitIfStatement(ifStatement: IfStatement): Unit = ???

    override def visitWhileLoop(whileLoop: WhileLoop): Unit = ???

    override def visitForLoop(forLoop: ForLoop): Unit = ???

    override def visitPrintStatement(printStatement: PrintStatement, methodVisitor: MethodVisitor): Unit = {
        methodVisitor.visitFieldInsn(
            GETSTATIC,
            "java/lang/System",
            "out",
            "Ljava/io/PrintStream;"
        )

        // TODO: Change to the actual value in the print statement
        methodVisitor.visitLdcInsn("Hello, World!")

        methodVisitor.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/io/PrintStream",
            "println",
            "(Ljava/lang/String;)V",
            false
        )
    }

    override def visitAssignStatement(assignStatement: AssignStatement): Unit = ???

    override def visitArrayAssignStatement(arrayAssignStatement: ArrayAssignStatement): Unit = ???
}
