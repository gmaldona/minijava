package minijava.lang.parser

import minijava.lang.parser.Parser
import minijava.lang.parser.symboltable.SymbolTableBuilder
import minijava.lang.typechecker.TypeChecker

import java.io.File
import org.scalatest.flatspec.AnyFlatSpec

class ParserSpec extends AnyFlatSpec {

    "MiniJavaParser" should "Parse through all the files in the example folder." in {

        val example_filepath = System.getProperty("user.dir") + "/src/main/minijava/"
        val dir = new File(example_filepath)
        val files = dir.listFiles()

        for (file <- files) {
            if (! file.getName.endsWith(".sh")) {
                try {
                    val parseTree = Parser.parseFile(example_filepath + file.getName)
                    println(parseTree.toStringTree())
                } catch {
                    case _: Exception => println("Could not parse file: " + file.getName + ". Moving on ...")
                }

            }
        }
    }

}

object ParserTest {
    @throws[Exception]
    def run(testProgram: String): Unit = {
        val parseTree = Parser.parseStream(testProgram)
        val miniJavaVisitor = new MiniJavaVisitorImpl()
        val AST = miniJavaVisitor.visit(parseTree)
        val symbolTable = new SymbolTableBuilder(AST).symbolTable
        TypeChecker.typeCheck(symbolTable, AST)
    }
}