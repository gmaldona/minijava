package minijava.lang.codegen.util

import minijava.lang.ast._

object JvmTypeGen {

    def get(t: Type): String = {
        t match {
            case _: IntArray  => "[I"
            case _: int       => "I"
            case _: boolean   => "Z"
            case _: ClassType => ""
        }
    }
}
