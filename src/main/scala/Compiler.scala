import java.io.File
import minijava.lang.parser.{MiniJavaVisitorImpl, Parser}
import minijava.lang.error.{CompilerError, FileNotFound}
import minijava.lang.parser.symboltable.SymbolTableBuilder

object Compiler {

    def main(args: Array[String]): Unit = {

        if (args.length == 0) {
            new CompilerError("No MiniJava file provided.")
        }

        val filename = System.getProperty("user.dir") + "/src/main/minijava/" + args(0)
        if (! new File(filename).exists || ! filename.endsWith(".minijava")) {
            FileNotFound(filename)
        }

        val parseTree = Parser.parse(filename)
        val miniJavaVisitor = new MiniJavaVisitorImpl()
        val AST = miniJavaVisitor.visit(parseTree)
        println(AST)

        val typechecker = new SymbolTableBuilder(AST)
    }

}
