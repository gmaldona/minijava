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
import minijava.lang.codegen.CodeGenerationImpl.ClassWriterT
import org.objectweb.asm.{ClassWriter, MethodVisitor}

trait ICodeGeneration {

    def visitProgram(program: Program): List[ClassWriterT]
    def visitMainClass(mainClass: MainClass): ClassWriter
    def visitMainMethodDecl(statement: Statement, cw: ClassWriter): MethodVisitor
    def visitMethodDecl(methodDecl: MethodDecl, cw: ClassWriter): MethodVisitor
    def visitClassDecl(classDecl: ClassDecl): ClassWriter
    def visitVarDecl(varDecl: VarDecl): Unit
    def visitStatementBlock(statementBlock: StatementBlock): Unit
    def visitIfStatement(ifStatement: IfStatement): Unit
    def visitWhileLoop(whileLoop: WhileLoop): Unit
    def visitForLoop(forLoop: ForLoop): Unit
    def visitPrintStatement(printStatement: PrintStatement, methodVisitor: MethodVisitor): Unit
    def visitAssignStatement(assignStatement: AssignStatement): Unit
    def visitArrayAssignStatement(arrayAssignStatement: ArrayAssignStatement): Unit


}
