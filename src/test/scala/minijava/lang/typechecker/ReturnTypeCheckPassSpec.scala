package minijava.lang.typechecker

import minijava.lang.parser.symboltable.SymbolTableBuilder
import minijava.lang.parser.{MiniJavaVisitorImpl, Parser, ParserTest}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{a, be}

class ReturnTypeCheckPassSpec extends AnyFlatSpec {

        "The Type Checker for method returns" should "pass" in {
            val testProgram: String =
                """
               class Main {
                public static void main(String[] args) {
                    System.out.println(new Main2().test());
                }
               }
               class Main2 {
                public int test() {
                    int testVar;
                    testVar = 1;
                    return testVar;
                }
               }
              """.stripMargin

            ParserTest.run(testProgram)
        }

    "The Type Check for method returns" should "not pass" in {
        val testProgram =
            """
           class Main {
            public static void main(String[] args) {
                System.out.println(new Main2().test());
            }
           }
           class Main2 {

            public int test() {
                boolean testVar;
                testVar = true;
                return testVar;
            }
           }
          """.stripMargin

        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }
}