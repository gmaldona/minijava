import minijava.lang.ast.Program
import minijava.lang.codegen.CodeGenerationImpl
import minijava.lang.codegen.util.ByteArrayClassLoader

import java.io.{File, FileOutputStream}
import minijava.lang.parser.{MiniJavaVisitorImpl, Parser}
import minijava.lang.error.FileNotFound
import minijava.lang.error.compile.{CompilerError, FileNotFound}
import minijava.lang.parser.symboltable.SymbolTableBuilder
import minijava.lang.typechecker.TypeChecker
import org.objectweb.asm._

object Compiler {

    def main(args: Array[String]): Unit = {

        if (args.length == 0) {
            new CompilerError("No MiniJava file provided.")
        }

        val filename = System.getProperty("user.dir") + "/src/main/minijava/" + args(0)
        if (!new File(filename).exists || !filename.endsWith(".minijava")) {
            FileNotFound(filename)
        }

        // Phase 1
        val parseTree = Parser.parse(filename)
        val miniJavaVisitor = new MiniJavaVisitorImpl()
        val AST = miniJavaVisitor.visit(parseTree)
        println(AST)

        // Phase 2
        val symbolTableBuilder = new SymbolTableBuilder(AST)
        val symbolTable = symbolTableBuilder.symbolTable
        symbolTableBuilder.hasUniqueSymbols(symbolTable)
        TypeChecker.typeCheck(symbolTable, AST)

        // Phase 3
        val classWriter = new CodeGenerationImpl().mainClass(AST.asInstanceOf[Program].mainClass)
        val bytes = classWriter.toByteArray

        val file = new File(AST.asInstanceOf[Program].mainClass.ClassName.id + ".class")
        val os = new FileOutputStream(file)
        os.write(bytes)

        val mainClass: Class[_] = new ByteArrayClassLoader().defineClass(AST.asInstanceOf[Program].mainClass.ClassName.id, bytes)

        val classReader: ClassReader = new ClassReader(bytes)
        classReader.accept(classWriter, 0)

    }

}
