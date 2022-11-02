package minijava.lang.typechecker

import minijava.lang.parser.symboltable.SymbolTableBuilder
import minijava.lang.parser.{MiniJavaVisitorImpl, Parser, ParserTest}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{a, be}

import scala.language.postfixOps

class BoolTypeCheckSpec extends AnyFlatSpec {

    "The Type Checker for int" should "pass" in {
        val testProgram =
            """
           class Main {
            public static void main(String[] args) {
                System.out.println(new Main2().test());
            }
           }
           class Main2 {
            boolean testVar;
            boolean x = false;
            public boolean test() {
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
            int testVar;
            boolean x;
            public int test() {
                x = true;
                testVar = x;
                return testVar;
            }
           }
          """.stripMargin
        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }
}