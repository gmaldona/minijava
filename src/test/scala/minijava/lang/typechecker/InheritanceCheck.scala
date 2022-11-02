package minijava.lang.typechecker

import minijava.lang.parser.ParserTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{a, be}

class InheritanceCheck extends AnyFlatSpec {

    "The inheritance check" should "pass" in {
        val testProgram =
            """class Main {
     public static void main(String[] args) {
         System.out.println(new Main2().test());
     }
    class Main3 {
        public int test2() {
            int testVar;
            testVar = 4;
            return testVar;
        }
    }
    }
    class Main2 extends Main3 {
        public int test() {
            int testVar;
            testVar = this.test2();
            return testVar;
        }
    }"""
        ParserTest.run(testProgram)
    }

    "The inheritance check" should "throw a illegal inheritance" in {

        val testProgram =
            """class Main {
     public static void main(String[] args) {
         System.out.println(new Main2().test());
     }
    class Main4 extends Main2 {
        public int test3() {
            int testVar;
            test = 4;
            return testVar;
        }
    }
    class Main3 extends Main4 {
        public int test2() {
            int testVar;
            testVar = 4;
            return testVar;
        }
    }
    }
    class Main2 extends Main3 {
        public int test() {
            int testVar;
            testVar = this.test2();
            return testVar;
        }
    }"""

        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }
}
