package minijava.lang.error

class CompilerError(msg: String) extends MiniJavaError(msg)

case class FileNotFound(filename: String) extends CompilerError("error: file not found or file is not MiniJava: " + filename)