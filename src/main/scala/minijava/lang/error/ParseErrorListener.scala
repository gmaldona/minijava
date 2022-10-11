package minijava.lang.error

import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer
import org.antlr.v4.runtime.misc.ParseCancellationException

class ParseErrorListener extends BaseErrorListener {

    @throws[ParseCancellationException]
    override def syntaxError(recognizer: Recognizer[_, _],
                             offendingSymbol: Any,
                             line: Int,
                             charPositionInLine: Int,
                             msg: String,
                             e: RecognitionException):
    Unit = {

    }

}

object ParseErrorInstance {
    val INSTANCE = new ParseErrorListener()
}
