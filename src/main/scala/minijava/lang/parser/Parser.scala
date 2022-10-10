package minijava.lang.parser

import org.antlr.v4.runtime._
import org.antlr.v4.runtime.misc.ParseCancellationException

import antlr4.{MiniJavaLexer, MiniJavaParser}
import minijava.lang.error.ParseErrorInstance

object Parser {

    @throws[ParseCancellationException]
    def parse(filename: String): MiniJavaParser.ProgramContext = {

        val charStream = CharStreams.fromFileName(filename)
        val miniJavaLexer = new MiniJavaLexer(charStream)
        miniJavaLexer.removeErrorListeners()
        miniJavaLexer.addErrorListener(ParseErrorInstance.INSTANCE)

        val tokenStream = new CommonTokenStream(miniJavaLexer)
        val miniJavaParser = new MiniJavaParser(tokenStream)
        miniJavaParser.removeErrorListeners()
        miniJavaParser.addErrorListener(ParseErrorInstance.INSTANCE)

        miniJavaParser.program()
    }
}
