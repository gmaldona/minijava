package minijava.lang.typechecker

import minijava.lang.parser.ParserTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{a, be}

class MethodOverloadingTypeCheck extends AnyFlatSpec {

    "The Type Checker for method overloading" should "fail" in {
        val testProgram =
            """
           class Main {
            public static void main(String[] args) {
                System.out.println(new Main2().method(2));
            }
           }
           class Main2 {
            public int method(int x) {
                int a;
                a = x;
                return a;
            }
            public boolean method(int p) {
                int a;
                a = 1;
                return false;
            }
           }
          """.stripMargin

        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }

    "The Type Checker for method overloading for inherited methods" should "fail" in {
        val testProgram =
            """
           class Main {
            public static void main(String[] args) {
                System.out.println(new Main2().method(2));
            }
           }
           class Main4 {
                public boolean method(int x) {
                  return true;
            }
           }
           class Main3 extends Main4{
           }
           class Main2 extends Main3 {
            public int method(int x) {
                int a;
                a = x;
                return a;
            }
           }
          """.stripMargin

        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }
}
