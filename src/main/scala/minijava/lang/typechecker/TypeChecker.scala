package minijava.lang.typechecker

import minijava.lang.ast.{ASTNode, ClassDecl, MainClass, MethodDecl, Program}
import minijava.lang.parser.{SymbolTable, SymbolTableType}

import scala.language.postfixOps

class TypeChecker(AST: ASTNode) {

    val symbolTable = new SymbolTable("Program")
    buildSymbolTable(symbolTable, AST)

    def buildSymbolTable(symbolTable: SymbolTable, node: ASTNode): Unit = {

        node match {
            case _: Program    => TableEntry.program(symbolTable, node.asInstanceOf[Program])
            case _: MainClass  => TableEntry.mainClass(symbolTable, node.asInstanceOf[MainClass])
            case _: ClassDecl  => TableEntry.classDecl(symbolTable, node.asInstanceOf[ClassDecl])
            case _: MethodDecl => TableEntry.methodDecl()
        }

    }

    object TableEntry {
        def program(symbolTable: SymbolTable, node: Program): Unit = {

            val mainClass = (
                node.mainClass.ClassName,
                SymbolTableType.MainClass,
                node,
                node.mainClass
            )

            for (classDecl <- node.ClassDecls) {
                val classDeclEntry = (
                    classDecl.ClassName,
                    SymbolTableType.Class,
                    node,
                    classDecl
                )
                symbolTable.addEntry(classDeclEntry)
            }

            println(symbolTable)

            buildSymbolTable(symbolTable, node.mainClass)
            for (classDecl <- node.ClassDecls)
                buildSymbolTable(symbolTable, classDecl)
        }

        def mainClass(parentSymbolTable: SymbolTable, node: MainClass): Unit = {
            val symbolTable = new SymbolTable(node.ClassName.id)
            parentSymbolTable.addChildSymbolTable(symbolTable)
            symbolTable.setParentSymbolTable(parentSymbolTable)

            val classSymbol = (
                node.ClassName.id,
                SymbolTableType.MainClass,
                parentSymbolTable.scope,
                node
            )

            val argsSymbol = (
                node.argName.id,
                SymbolTableType.Variable,
                node,
                node.argName
            )

            symbolTable.addEntries(List(classSymbol, argsSymbol))

            println(symbolTable)
        }

        def classDecl(parentSymbolTable: SymbolTable, node: ClassDecl): Unit = {
            val symbolTable = new SymbolTable(node.ClassName.id)
            parentSymbolTable.addChildSymbolTable(symbolTable)
            symbolTable.setParentSymbolTable(parentSymbolTable)

            val classSymbol = (
                node.ClassName.id,
                SymbolTableType.Class,
                parentSymbolTable.scope,
                node
            )

            val varSymbols = node.varDecls.map( varDecl => (
                varDecl.varName.id,
                SymbolTableType.Variable,
                node,
                varDecl
            ))

            for (varSymbol <- varSymbols) {
                symbolTable.addEntry(varSymbol)
            }

            val methodSymbols = node.methodDecls.map( methodDecl => (
                methodDecl.methodName.id,
                SymbolTableType.Method,
                node,
                methodDecl
            ))

            for (methodSymbol <- methodSymbols) {
                symbolTable.addEntry(methodSymbol)
            }


            node.superClass match {
                case Some(n) => {
                    val superClassSymbol = (
                        n.id,
                        SymbolTableType.SuperClass,
                        node,
                        n
                    )
                    symbolTable.addEntries(List(classSymbol, superClassSymbol))
                }
                case None => symbolTable.addEntry(classSymbol)
            }

            println(symbolTable)

            for (methodDecl <- node.methodDecls)
                buildSymbolTable(symbolTable, methodDecl)
        }

        def VarDecl(): Unit = ???
        def methodDecl(): Unit = ???
        def statementBlock(): Unit = ???
        def ifStatement(): Unit = ???
        def whiteLoop(): Unit = ???
        def forLoop(): Unit = ???
        def assign(): Unit = ???
        def arrayAssign(): Unit = ???
    }
}
