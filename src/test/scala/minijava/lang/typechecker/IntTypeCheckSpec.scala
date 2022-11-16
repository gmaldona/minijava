package minijava.lang.typechecker

import minijava.lang.error.parser.TypeMismatchError
import minijava.lang.parser.symboltable.SymbolTableBuilder
import minijava.lang.parser.{MiniJavaVisitorImpl, Parser, ParserTest}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{a, be}

import scala.language.postfixOps

class IntTypeCheckSpec extends AnyFlatSpec {

    "The Type Checker for int" should "pass" in {
        val testProgram =
            """
           class Main {
            public static void main(String[] args) {
                System.out.println(new Main2().test());
            }
           }
           class Main2 {
            int testVar;
            int x = 10;
            public int test() {
                testVar = x;
                return testVar;
            }
           }
          """.stripMargin

        ParserTest.run(testProgram)
    }

    it should "throw a TypeMismatchError" in {
        val testProgram =
            """
           class Main {
            public static void main(String[] args) {
                System.out.println(new Main2().test());
            }
           }
           class Main2 {
            boolean testVar;
            int x = 10;
            public int test() {
                testVar = x;
                return x;
            }
           }
          """.stripMargin

        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }
}
