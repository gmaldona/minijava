package minijava.lang.typechecker

import minijava.lang.parser.ParserTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{a, be}

class VarNotDeclaredSpec extends AnyFlatSpec {

    "The Symbol Table" should "catch the variable is used before declared locally" in {
        val testProgram =
            """
             class Main {
                  public static void main(String[] args) {
                       System.out.println(new Main2().test());
                  }
             }
             class Main2 {
                  public int test() {
                       int testVar;
                       testVar = testVar2;
                       return testVar;
                  }
             }
              """.stripMargin

        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }

    "The Symbol Table" should "catch the variable is used before declared in class" in {
        val testProgram =
            """
             class Main {
                  public static void main(String[] args) {
                       System.out.println(new Main2().test());
                  }
             }
             class Main2 {
                  int testVar;
                  public int test() {
                       testVar = testVar2;
                       return testVar;
                  }
             }
              """.stripMargin

        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }

    "The Symbol Table" should "pass" in {
        val testProgram =
            """
             class Main {
                  public static void main(String[] args) {
                       System.out.println(new Main2().test());
                  }
             }
             class Main2 {
                  int testVar;
                  int testVar2;
                  public int test() {
                       testVar2 = 3;
                       testVar = testVar2;
                       return testVar;
                  }
             }
              """.stripMargin

        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }
}
