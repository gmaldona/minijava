import java.io.File

import minijava.lang.parser.Parser
import minijava.lang.error.{CompilerError, FileNotFound}

object Compiler {

    def main(args: Array[String]): Unit = {

        if (args.length == 0) {
            new CompilerError("No MiniJava file provided.")
        }

        val filename = System.getProperty("user.dir") + "/src/main/minijava/" + args(0)
        if (! new File(filename).exists || ! filename.endsWith(".minijava")) {
            FileNotFound(filename)
        }

        val parseTree = Parser.parse(filename).toStringTree()
        println(parseTree)
    }

}
