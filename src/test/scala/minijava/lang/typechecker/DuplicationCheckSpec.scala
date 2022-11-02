package minijava.lang.typechecker

import minijava.lang.parser.symboltable.SymbolTableBuilder
import minijava.lang.parser.{MiniJavaVisitorImpl, Parser, ParserSpec, ParserTest}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{a, be}

class DuplicationCheckSpec extends AnyFlatSpec {

    "The Type Check" should "catch the double declaration" in {
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
                int testVar;
                return testVar;
            }
           }
          """.stripMargin
        a [Throwable] should be thrownBy {
            ParserTest.run(testProgram)
        }
    }
}
