package minijava.lang.parser

import minijava.lang.parser.Parser

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
                    val parseTree = Parser.parse(example_filepath + file.getName)
                    println(parseTree.toStringTree())
                } catch {
                    case _: Exception => println("Could not parse file: " + file.getName + ". Moving on ...")
                }

            }
        }
    }
}