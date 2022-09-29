package minijava.lang.parser

import org.antlr.v4.runtime._
import antlr4.{MiniJavaLexer, MiniJavaParser}

object Parser {

    def parse(filename: String): MiniJavaParser.ProgramContext = {

        val charStream = CharStreams.fromFileName(filename)
        val miniJavaLexer = new MiniJavaLexer(charStream)
        val tokenStream = new CommonTokenStream(miniJavaLexer)
        val miniJavaParser = new MiniJavaParser(tokenStream)

        miniJavaParser.program()
    }

}
