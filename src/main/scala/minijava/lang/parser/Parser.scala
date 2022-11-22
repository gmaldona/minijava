/**
 * CSC 444 - Compiler Design
 * State University of New York, College at Oswego
 *
 * @author  Gregory Maldonado
 * @date    November 15, 2022
 * @version 1.0
 */

package minijava.lang.parser

import org.antlr.v4.runtime._
import org.antlr.v4.runtime.misc.ParseCancellationException

import antlr4.{MiniJavaLexer, MiniJavaParser}
import minijava.lang.error.ParseErrorInstance

object Parser {

    @throws[ParseCancellationException]
    def parse(input: String, isFile: Boolean = false):  MiniJavaParser.ProgramContext = {
        val charStream = if (isFile) CharStreams.fromFileName(input) else CharStreams.fromString(input)

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
