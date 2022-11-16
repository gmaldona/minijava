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

trait ICodeGeneration {

    def visitProgram(program: Program): Unit
    def visitMainClass(mainClass: MainClass): Unit
    def visitClassDecl(classDecl: ClassDecl): Unit
    def visitVarDecl(varDecl: VarDecl): Unit
    def visitMethodDecl(methodDecl: MethodDecl): Unit
    def visitStatementBlock(statementBlock: StatementBlock): Unit
    def visitIfStatement(ifStatement: IfStatement): Unit
    def visitWhileLoop(whileLoop: WhileLoop): Unit
    def visitForLoop(forLoop: ForLoop): Unit
    def visitPrintStatement(printStatement: PrintStatement): Unit
    def visitAssignStatement(assignStatement: AssignStatement): Unit
    def visitArrayAssignStatement(arrayAssignStatement: ArrayAssignStatement): Unit


}
