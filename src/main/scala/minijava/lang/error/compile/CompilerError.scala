/**
 * CSC 444 - Compiler Design
 * State University of New York, College at Oswego
 *
 * @author  Gregory Maldonado
 * @date    November 15, 2022
 * @version 1.0
 */

package minijava.lang.error.compile

import minijava.lang.error.MiniJavaError

class CompilerError(msg: String) extends MiniJavaError(msg)

case class FileNotFound(filename: String) extends CompilerError("error: file not found or file is not MiniJava: " + filename)