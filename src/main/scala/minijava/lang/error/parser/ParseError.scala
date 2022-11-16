/**
 * CSC 444 - Compiler Design
 * State University of New York, College at Oswego
 *
 * @author  Gregory Maldonado
 * @date    November 15, 2022
 * @version 1.0
 */

package minijava.lang.error.parser

import minijava.lang.error.MiniJavaError

class ParseError(msg: String) extends MiniJavaError(msg)

case class MissingTokenError(token: String, line: String = "") extends ParseError("Missing " + token + " on line " + line + ".")

case class ParseTreeError(msg: String) extends ParseError(msg)

case class TypeError(msg: String) extends ParseError(msg)

case class SymbolAlreadyDefined(msg: String) extends ParseError(msg)

case class IllegalInheritance(msg: String) extends ParseError(msg)

case class CircularInheritance(className: String) extends ParseError("Circular Inheritance with class: " + className )

case class TypeMismatchError(msg: String) extends ParseError(msg)

case class UseBeforeDeclaration(msg: String) extends ParseError(msg)

case class TypeNotSupported(msg: String) extends ParseError(msg)

case class KeywordThisUsedInMainError(msg: String = "Keyword this was used in Main class.") extends ParseError(msg)

case class OperationNotSupported(msg: String) extends ParseError(msg)

case class DuplicatedMethod(method: String) extends ParseError("Duplicated method: " + method)

case class IllegalInheritedMethodOverload(method: String) extends ParseError("Illegal method override for " + method)
