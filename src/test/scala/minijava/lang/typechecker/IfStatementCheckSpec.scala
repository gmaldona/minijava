package minijava.lang.typechecker

import minijava.lang.parser.ParserTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{a, be}

class IfStatementCheckSpec extends AnyFlatSpec {

    "The inheritance check" should "pass" in {
        val testProgram =
            """
              class Main {
                   public static void main(String[] args) {
                        System.out.println(new Main2().method(2));
                   }
              }

                 class Main2 {
                      public int method(int x) {
                            if (42) {
                                 System.out.println(x + 1);
                            } else {
                                 System.out.println(x);
                            }
                            return x;
                      }
                 }
             """.stripMargin

        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }

}
