package minijava.lang.parser.symboltable

import minijava.lang.ast.ASTAliases.MethodParam
import minijava.lang.ast._
import minijava.lang.error.{IllegalMultipleInheritance, SymbolAlreadyDefined}
import minijava.lang.typechecker.TypeChecker

import scala.language.postfixOps

class SymbolTableBuilder(AST: ASTNode) {

    val symbolTable = new SymbolTable("Program")
    buildSymbolTable(symbolTable, AST)
    hasUniqueSymbols(symbolTable)

    def hasUniqueSymbols(symbolTable: SymbolTable): Unit = {
        var uniqueEntries = symbolTable.tableEntries
            .filter( entry => entry._2 == SymbolTableType.Variable)
            .map( entry => entry._1 )
            .distinct

        if (uniqueEntries.size != symbolTable.tableEntries.count(entry => entry._2 == SymbolTableType.Variable))
            SymbolAlreadyDefined("Symbol has multiple definitions.")

        uniqueEntries = symbolTable.tableEntries
            .filter( entry =>
                entry._2 == SymbolTableType.MainClass || entry._2 == SymbolTableType.Class
            )
            .map( entry => entry._1 )
            .distinct

        if (uniqueEntries.size != symbolTable.tableEntries.count(entry => entry._2 == SymbolTableType.Class || entry._2 == SymbolTableType.MainClass))
            SymbolAlreadyDefined("Class has multiple definitions.")

        uniqueEntries = symbolTable.tableEntries
            .filter( entry => entry._2 == SymbolTableType.Method)
            .map( entry => entry._1 )
            .distinct

        // TODO: FIX METHOD OVERRIDING
        if (uniqueEntries.size != symbolTable.tableEntries.count(entry => entry._2 == SymbolTableType.Method)) {
            val uniqueMethodNames = symbolTable.tableEntries
                .filter( entry => entry._2 == SymbolTableType.Method)
                .map( entry => entry._1)

            for (methodName <- uniqueMethodNames) {
                val methodOverloads = symbolTable.getTableEntries(methodName, SymbolTableType.Method)
                val uniqueMethods: List[List[Type]] = methodOverloads
                    .map(entry => entry._4.asInstanceOf[MethodDecl].methodParams)
                    .map(entry => entry.map(param => param._1))
                    .distinct

                if (uniqueMethods.size != methodOverloads.size)
                    SymbolAlreadyDefined("Method has multiple definitions.")

            }

        }

        uniqueEntries = symbolTable.tableEntries
            .filter( entry => entry._2 == SymbolTableType.SuperClass)
            .map( entry => entry._1 )
            .distinct

        if (uniqueEntries.size != symbolTable.tableEntries.count(entry => entry._2 == SymbolTableType.SuperClass))
            IllegalMultipleInheritance("Illegal Multiple Inheritance")

        for (childTable <- symbolTable.childrenSymbolTables)
            hasUniqueSymbols(childTable)
    }

    def buildSymbolTable(symbolTable: SymbolTable, node: ASTNode): Unit = {
        node match {
            case _: Program        => TableEntry.program(symbolTable, node.asInstanceOf[Program])
            case _: MainClass      => TableEntry.mainClass(symbolTable, node.asInstanceOf[MainClass])
            case _: ClassDecl      => TableEntry.classDecl(symbolTable, node.asInstanceOf[ClassDecl])
            case _: MethodDecl     =>
                TableEntry.methodDecl(symbolTable, node.asInstanceOf[MethodDecl])
                TypeChecker.typeCheck(symbolTable.childrenSymbolTables.last, node)
            case _: StatementBlock => TableEntry.statementBlock(symbolTable, node.asInstanceOf[StatementBlock])
            case _: WhileLoop      => TableEntry.whileLoop(symbolTable, node.asInstanceOf[WhileLoop])
            case _: ForLoop        => TableEntry.forLoop(symbolTable, node.asInstanceOf[ForLoop])
            case _: IfStatement    => TableEntry.ifStatement(symbolTable, node.asInstanceOf[IfStatement])

            case _: AssignStatement =>
                TypeChecker.typeCheck(symbolTable, node)
            case _                  =>
        }

    }

    object TableEntry {
        def program(symbolTable: SymbolTable, node: Program): Unit = {

            val mainClass = (
                node.mainClass.ClassName.id,
                SymbolTableType.MainClass,
                node,
                node.mainClass
            )
            symbolTable.addEntry(mainClass)

            for (classDecl <- node.ClassDecls) {
                val classDeclEntry = (
                    classDecl.ClassName.id,
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

            val argsSymbol = (
                node.argName.id,
                SymbolTableType.Variable,
                node,
                node.argName
            )

            symbolTable.addEntry(argsSymbol)

            println(symbolTable)
        }

        def classDecl(parentSymbolTable: SymbolTable, node: ClassDecl): Unit = {
            val symbolTable = new SymbolTable(node.ClassName.id)
            parentSymbolTable.addChildSymbolTable(symbolTable)
            symbolTable.setParentSymbolTable(parentSymbolTable)

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
                    symbolTable.addEntry(superClassSymbol)
                }
                case None =>
            }

            println(symbolTable)

            for (methodDecl <- node.methodDecls)
                buildSymbolTable(symbolTable, methodDecl)
        }

        def methodDecl(parentSymbolTable: SymbolTable, node: MethodDecl): Unit = {
            val symbolTable = new SymbolTable(parentSymbolTable.getTag + " - " + node.methodName.id)
            parentSymbolTable.addChildSymbolTable(symbolTable)
            symbolTable.setParentSymbolTable(parentSymbolTable)

            val paramsSymbols = node.methodParams.map( paramDecl => (
                paramDecl._2.id,
                SymbolTableType.Variable,
                node,
                paramDecl._2
            ))

            for (paramSymbol <- paramsSymbols) {
                symbolTable.addEntry(paramSymbol)
            }

            val varSymbols = node.varDecls.map( varDecl => (
                varDecl.varName.id,
                SymbolTableType.Variable,
                node,
                varDecl
            ))

            for (varSymbol <- varSymbols) {
                symbolTable.addEntry(varSymbol)
            }

            println(symbolTable)

            for (statement <- node.statements) {
                buildSymbolTable(symbolTable, statement)
            }
        }

        def statementBlock(parentSymbolTable: SymbolTable, node: StatementBlock): Unit = {
            val symbolTable = new SymbolTable(parentSymbolTable.getTag + " - Statement Block")
            parentSymbolTable.addChildSymbolTable(symbolTable)
            symbolTable.setParentSymbolTable(parentSymbolTable)

            for (statement <- node.statements)
                buildSymbolTable(symbolTable, statement)
        }

        def whileLoop(parentSymbolTable: SymbolTable, node: WhileLoop): Unit = {
            val symbolTable = new SymbolTable(parentSymbolTable.getTag + " - While Loop")
            parentSymbolTable.addChildSymbolTable(symbolTable)
            symbolTable.setParentSymbolTable(parentSymbolTable)

            buildSymbolTable(symbolTable, node.statement)
        }

        def forLoop(parentSymbolTable: SymbolTable, node: ForLoop): Unit = {
            val symbolTable = new SymbolTable(parentSymbolTable.getTag + " - For Loop")
            parentSymbolTable.addChildSymbolTable(symbolTable)
            symbolTable.setParentSymbolTable(parentSymbolTable)

            val varDecl = (
                node.var1Name.id,
                SymbolTableType.Variable,
                node,
                node
            )

            symbolTable.addEntry(varDecl)

            println(symbolTable)

            buildSymbolTable(symbolTable, node.statement)
        }


        def ifStatement(parentSymbolTable: SymbolTable, node: IfStatement): Unit = {
            val symbolTable = new SymbolTable(parentSymbolTable.getTag + " - If Statement")
            parentSymbolTable.addChildSymbolTable(symbolTable)
            symbolTable.setParentSymbolTable(parentSymbolTable)

            buildSymbolTable(symbolTable, node.statement)
            buildSymbolTable(symbolTable, node.elseStatement)
        }
        def assign(): Unit = ???
        def arrayAssign(): Unit = ???
    }
}
