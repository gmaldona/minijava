package minijava.lang.codegen

import minijava.lang.ast._
import org.objectweb.asm.{ClassVisitor, ClassWriter}
import org.objectweb.asm.Opcodes._

class CodeGenerationWalker {

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
