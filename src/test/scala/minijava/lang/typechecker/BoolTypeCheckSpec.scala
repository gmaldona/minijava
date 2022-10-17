package minijava.lang.typechecker

import minijava.lang.parser.symboltable.SymbolTableBuilder
import minijava.lang.parser.{MiniJavaVisitorImpl, Parser}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{a, be}

import scala.language.postfixOps

class BoolTypeCheckSpec extends AnyFlatSpec {

    var testProgram =
        """
           class Main {
            public static void main(String[] args) {
                System.out.println(new Main2().test());
            }
           }
           class Main2 {
            boolean testVar;
            boolean x = false;
            public boolean test() {
                testVar = x;
                return testVar;
            }
           }
          """.stripMargin

    "The Type Checker for int" should "pass" in {
        val parseTree = Parser.parseStream(testProgram)
        val miniJavaVisitor = new MiniJavaVisitorImpl()
        val AST = miniJavaVisitor.visit(parseTree)
        new SymbolTableBuilder(AST).symbolTable
    }

    testProgram =
        """
           class Main {
            public static void main(String[] args) {
                System.out.println(new Main2().test());
            }
           }
           class Main2 {
            int testVar;
            boolean x = false;
            public int test() {
                testVar = x;
                return testVar;
            }
           }
          """.stripMargin

    it should "throw a TypeMismatchError" in {
        a [Exception] should be thrownBy {
            val parseTree = Parser.parseStream(testProgram)
            val miniJavaVisitor = new MiniJavaVisitorImpl()
            val AST = miniJavaVisitor.visit(parseTree)
            new SymbolTableBuilder(AST).symbolTable
        }
    }
}