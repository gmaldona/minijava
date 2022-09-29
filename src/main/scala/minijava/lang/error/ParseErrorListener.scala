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
        if (msg.contains("expecting")) {
            val expectingStartingIndex = msg.indexOf('{')
            val subMsg = msg.substring(expectingStartingIndex + 1, msg.length - 1)
            val expectingTokens = subMsg
                                    .replace(" ", "")
                                    .replace("'", "")
                                    .split(",")

            if (expectingTokens.contains("}")) {
                MissingTokenError("'}'", line + ":" + charPositionInLine)
            }
            else if (expectingTokens.contains(";")) {
                MissingTokenError("';'", line + ":" + charPositionInLine)
            }
        }
        throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg)
    }

}

object ParseErrorInstance {
    val INSTANCE = new ParseErrorListener()
}
