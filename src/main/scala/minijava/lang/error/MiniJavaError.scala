package minijava.lang.error

abstract class MiniJavaError(msg: String) {

    val RED_TEXT   = "\u001B[31m"
    val PLAIN_TEXT = "\u001B[0m"

    println(RED_TEXT + msg + PLAIN_TEXT)
    System.exit(1)
}
