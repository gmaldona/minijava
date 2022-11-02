import java.io.File
import minijava.lang.parser.{MiniJavaVisitorImpl, Parser}
import minijava.lang.error.{CompilerError, FileNotFound}
import minijava.lang.parser.symboltable.SymbolTableBuilder
import minijava.lang.typechecker.TypeChecker

object Compiler {

    def main(args: Array[String]): Unit = {

        if (args.length == 0) {
            new CompilerError("No MiniJava file provided.")
        }

        val filename = System.getProperty("user.dir") + "/src/main/minijava/" + args(0)
        if (! new File(filename).exists || ! filename.endsWith(".minijava")) {
            FileNotFound(filename)
        }

        val parseTree = Parser.parseFile(filename)
        val miniJavaVisitor = new MiniJavaVisitorImpl()
        val AST = miniJavaVisitor.visit(parseTree)
        println(AST)

        val symbolTableBuilder = new SymbolTableBuilder(AST)
        val symbolTable = symbolTableBuilder.symbolTable
        symbolTableBuilder.hasUniqueSymbols(symbolTable)
        TypeChecker.typeCheck(symbolTable, AST)
        println("finished")
    }

}
