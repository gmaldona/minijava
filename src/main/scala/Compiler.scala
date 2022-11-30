import minijava.lang.ast.Program
import minijava.lang.codegen.CodeGenerationImpl
import minijava.lang.codegen.CodeGenerationImpl.ClassWriterT
import minijava.lang.codegen.util.ByteArrayClassLoader

import java.io.{File, FileOutputStream}
import minijava.lang.parser.{MiniJavaVisitorImpl, Parser}
import minijava.lang.error.compile.{CompilerError, FileNotFound}
import minijava.lang.parser.debug.VariablePrinter
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
        val parseTree = Parser.parse(filename, isFile = true)
        val miniJavaVisitor = new MiniJavaVisitorImpl()
        val AST = miniJavaVisitor.visit(parseTree)
        println(AST)

        // Phase 2
        val symbolTableBuilder = new SymbolTableBuilder(AST)
        val symbolTable = symbolTableBuilder.symbolTable
        symbolTableBuilder.hasUniqueSymbols(symbolTable)
        TypeChecker.typeCheck(symbolTable, AST)
        VariablePrinter.debug(AST.asInstanceOf[Program])

        // Phase 3
        CodeGenerationImpl.setSymbolTable(symbolTable)
        val targetDir = new File("minijava-target")
        if (! targetDir.isDirectory) {
            targetDir.mkdir()
        }
        val cws: List[ClassWriterT] = CodeGenerationImpl.generate(AST.asInstanceOf[Program])
        for (cw <- cws) {
            val classWriter = cw._1
            val className   = cw._2

            val bytes = classWriter.toByteArray
            val os = new FileOutputStream(new File(targetDir.getName + "/" + className + ".class"))

            os.write(bytes)
            println("\u001B[32m Generated class file: " + className + "\u001B[0m" )
        }

    }

}
