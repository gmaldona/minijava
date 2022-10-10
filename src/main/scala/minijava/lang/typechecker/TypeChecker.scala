package minijava.lang.typechecker

import minijava.lang.ast.{ASTNode, ArrayAssignStatement, AssignStatement, ClassDecl, ExprArray, ForLoop, IfStatement, MainClass, MethodDecl, NewIntArrayDecl, Program, StatementBlock, WhileLoop}
import minijava.lang.parser.symboltable.SymbolTable

class TypeChecker(AST: ASTNode, symbolTable: SymbolTable) {

    _traverseAST(AST)

    def _traverseAST(node: ASTNode): Unit = {
        node match {
            case _: Program =>
                val programNode = node.asInstanceOf[Program]
                _traverseAST(programNode.mainClass)
                for (classDecl <- programNode.ClassDecls) _traverseAST(classDecl)
            case _: MainClass =>
                val mainClassNode = node.asInstanceOf[MainClass]
                _traverseAST(mainClassNode.statement)
            case _: ClassDecl =>
                val classNode = node.asInstanceOf[ClassDecl]
                for (methodDecl <- classNode.methodDecls) _traverseAST(methodDecl)
            case _: MethodDecl =>
                val methodNode = node.asInstanceOf[MethodDecl]
                methodReturnTypeCheck(methodNode)
                methodParameterTypeCheck(methodNode)
                for (statement <- methodNode.statements) _traverseAST(statement)
            case _: StatementBlock =>
                val statementNode = node.asInstanceOf[StatementBlock]
                for (statement <- statementNode.statements) _traverseAST(statement)
            case _: IfStatement =>
                val ifStatementNode = node.asInstanceOf[IfStatement]
                ifStatementBooleanTypeCheck(ifStatementNode)
                _traverseAST(ifStatementNode.statement)
                _traverseAST(ifStatementNode.elseStatement)
            case _: WhileLoop =>
                val whileLoopNode = node.asInstanceOf[WhileLoop]
                whileLoopExpressionTypeCheck(whileLoopNode)
                _traverseAST(whileLoopNode.statement)
            case _: ForLoop =>
                val forLoopNode = node.asInstanceOf[ForLoop]
                forLoopExpressionTypeCheck(forLoopNode)
                _traverseAST(forLoopNode.statement)
            case _: AssignStatement =>
                val assignNode = node.asInstanceOf[AssignStatement]
                varAssignTypeCheck(assignNode)
            case _: ArrayAssignStatement =>
                val arrayAssignNode = node.asInstanceOf[ArrayAssignStatement]
                arrayAssignTypeCheck(arrayAssignNode)
            case _: ExprArray =>
                val exprArrayNode = node.asInstanceOf[ExprArray]
                arrayIndexTypeCheck(exprArrayNode)
        }
    }

    def methodReturnTypeCheck(node: MethodDecl): Unit = ???
    def varAssignTypeCheck(node: AssignStatement): Unit = ???
    def ifStatementBooleanTypeCheck(node: IfStatement): Unit = ???
    def whileLoopExpressionTypeCheck(node: WhileLoop): Unit = ???
    def forLoopExpressionTypeCheck(node: ForLoop): Unit = ???
    def methodParameterTypeCheck(node: MethodDecl): Unit = ???
    def arrayAssignTypeCheck(node: ArrayAssignStatement): Unit = ???
    def arrayIndexTypeCheck(node: ExprArray): Unit = ???
    def newIntArrayDeclTypeCheck(node: NewIntArrayDecl): Unit = ???
}
