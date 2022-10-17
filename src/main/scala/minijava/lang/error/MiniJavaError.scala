package minijava.lang.error

abstract class MiniJavaError(msg: String) {

    val RED_TEXT   = "\u001B[31m"
    val PLAIN_TEXT = "\u001B[0m"

    throw new NoStackTraceThrowable(RED_TEXT + msg + PLAIN_TEXT)

    class NoStackTraceThrowable(msg: String) extends Throwable(msg) {
        override def fillInStackTrace: Throwable = this
    }
}
