/**
 * CSC 444 - Compiler Design
 * State University of New York, College at Oswego
 *
 * @author  Gregory Maldonado
 * @date    November 15, 2022
 * @version 1.0
 */

package minijava.lang.codegen.util

/**
 * ByteArrayClassLoader extends base ClassLoader to take a byte[] and output a class file
 */
class ByteArrayClassLoader extends ClassLoader {

    def defineClass(name: String, bytes: Array[Byte]): Class[_] = {
        defineClass(name, bytes, 0, bytes.length)
    }

}
