package minijava.lang.typechecker

import minijava.lang.parser.symboltable.SymbolTableBuilder
import minijava.lang.parser.{MiniJavaVisitorImpl, Parser}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{a, be}

class ReturnTypeCheckPassSpec extends AnyFlatSpec {
        var testProgram: String =
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

        "The Type Checker for method returns" should "pass" in {
            val parseTree = Parser.parseStream(testProgram)
            val miniJavaVisitor = new MiniJavaVisitorImpl()
            val AST = miniJavaVisitor.visit(parseTree)
            new SymbolTableBuilder(AST).symbolTable
        }
}
class ReturnTypeCheckFailSpec extends AnyFlatSpec {

    var testProgram =
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

    "The Type Check for method returns" should "not pass" in {
        a [Throwable] should be thrownBy {
            val parseTree = Parser.parseStream(testProgram)
            val miniJavaVisitor = new MiniJavaVisitorImpl()
            val AST = miniJavaVisitor.visit(parseTree)
            new SymbolTableBuilder(AST).symbolTable
        }
    }
}
