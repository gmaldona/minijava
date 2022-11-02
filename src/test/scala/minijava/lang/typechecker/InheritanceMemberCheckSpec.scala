package minijava.lang.typechecker

import minijava.lang.parser.ParserTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{a, be}


class InheritanceMemberCheckSpec extends AnyFlatSpec {

    "The type checker" should "pass in checking inherited variables" in {
        val testProgram =
            """class Main {
     public static void main(String[] args) {
         System.out.println(new Main2().test());
     }
    class Main3 {
        int testVar;
    }
    class Main2 extends Main3 {
        public int test() {
            return testVar;
        }
    }"""

        ParserTest.run(testProgram)
    }

    "The type checker" should "catch in checking inherited variables not declared" in {
        val testProgram =
            """class Main {
     public static void main(String[] args) {
         System.out.println(new Main2().test());
     }
    class Main3 {
    }
    class Main2 extends Main3 {
        public int test() {
            return testVar;
        }
    }"""

        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }



}
