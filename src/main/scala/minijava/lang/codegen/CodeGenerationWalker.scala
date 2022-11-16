/**
 * CSC 444 - Compiler Design
 * State University of New York, College at Oswego
 *
 * @author  Gregory Maldonado
 * @date    November 15, 2022
 * @version 1.0
 */

package minijava.lang.codegen

import minijava.lang.ast._
import org.objectweb.asm.{ClassVisitor, ClassWriter}
import org.objectweb.asm.Opcodes._

class sCodeGenerationWalker {

    def walkAST(node: ASTNode): Unit = {
        node match {
            case n: Program              => ???
            case n: MainClass            => ???
            case n: ClassDecl            => ???
            case n: VarDecl              => ???
            case n: MethodDecl           => ???
            case n: StatementBlock       => ???
            case n: IfStatement          => ???
            case n: WhileLoop            => ???
            case n: ForLoop              => ???
            case n: PrintStatement       => ???
            case n: AssignStatement      => ???
            case n: ArrayAssignStatement => ???
        }
    }

}
