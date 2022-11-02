package minijava.lang.typechecker
import minijava.lang.parser.ParserTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{a, be}
import org.scalatest.flatspec.AnyFlatSpec

class ExprParenthesisTypeCheckSpec extends AnyFlatSpec {
    "The Type Check" should "pass" in {
        val testProgram =
            """
               class Main {
                    public static void main(String[] args) {
                        System.out.println(new Main2().test());
                    }
               }

               class Main2 {
                public int test() {
                    int expr;
                    expr = (10 + 5);
                    return expr;
                }
               }
              """.stripMargin

        ParserTest.run(testProgram)
    }

    "The Type Check" should "catch the expression error" in {
        val testProgram =
            """
               class Main {
                    public static void main(String[] args) {
                        System.out.println(new Main2().test());
                    }
               }

               class Main2 {
                public int test() {
                    int expr;
                    expr = (10 + true);
                    return expr;
                }
               }
              """.stripMargin

        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }
}
