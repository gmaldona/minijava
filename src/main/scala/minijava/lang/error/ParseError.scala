package minijava.lang.error

class ParseError(msg: String) extends MiniJavaError(msg)

case class MissingTokenError(token: String, line: String = "") extends ParseError("Missing " + token + " on line " + line + ".")

case class ParseTreeError(msg: String) extends ParseError(msg)

case class TypeError(msg: String) extends ParseError(msg)

case class SymbolAlreadyDefined(msg: String) extends ParseError(msg)

case class IllegalMultipleInheritance(msg: String) extends ParseError(msg)