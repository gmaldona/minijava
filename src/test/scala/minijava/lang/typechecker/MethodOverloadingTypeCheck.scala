package minijava.lang.typechecker

import minijava.lang.parser.ParserTest
import org.scalatest.flatspec.AnyFlatSpec

class MethodOverloadingTypeCheck extends AnyFlatSpec {

    "The Type Checker for method overloading" should "pass" in {
        val testProgram =
            """
           class Main {
            public static void main(String[] args) {
                System.out.println(new Main2().test());
            }
           }
           class Main2 {
            public int method(int x) {
                int a;
                a = x;
                return a;
            }
            public int method() {
                int a;
                a = 1;
                return a;
            }
            public int test() {
              return this.method(4);
            }
           }
          """.stripMargin


        ParserTest.run(testProgram)
    }
}
