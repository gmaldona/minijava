package minijava.lang.codegen.util

class ByteArrayClassLoader extends ClassLoader {

    def defineClass(name: String, bytes: Array[Byte]): Class[_] = {
        defineClass(name, bytes, 0, bytes.length)
    }

}
