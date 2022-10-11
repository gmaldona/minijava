package minijava.lang.parser.symboltable

import minijava.lang.ast._

/*
        -----------------------------------------
        |  Symbol  |  Type  |  Scope  | ASTNode |
        -----------------------------------------
        |    s_1   |  T_1   |   S_1   |   N_1   |
        -----------------------------------------


 */
sealed abstract class SymbolTableType()
object SymbolTableType {
    case object Variable   extends SymbolTableType
    case object MainClass  extends SymbolTableType
    case object Class      extends SymbolTableType
    case object Method     extends SymbolTableType
    case object Program    extends SymbolTableType
    case object SuperClass extends SymbolTableType
}

class SymbolTable(tag: String) {

    type tableEntry = (String, SymbolTableType, Scope, ASTNode)

    var parentSymbolTable: Option[SymbolTable] = None
    var childrenSymbolTables: List[SymbolTable] = List()
    var tableEntries: List[tableEntry] = List()

    def setParentSymbolTable(symbolTable: SymbolTable): Unit = {
        parentSymbolTable = Some(symbolTable)
    }

    def addChildSymbolTable(symbolTable: SymbolTable): Unit = {
        childrenSymbolTables = symbolTable :: childrenSymbolTables
    }

    def addEntry(entry: tableEntry): Unit = {
        tableEntries = entry :: tableEntries
    }

    def addEntry(symbol: String,
                 symbolType: SymbolTableType,
                 scope: Scope,
                 node: ASTNode): Unit = {
        val entry = (
            symbol,
            symbolType,
            scope,
            node
        )
        tableEntries = entry :: tableEntries
    }

    def addEntries(entries: List[tableEntry]): Unit = {
        for (entry <- entries)
            addEntry(
                entry._1,
                entry._2,
                entry._3,
                entry._4
            )
    }

    def containsSymbol(symbol: String): Boolean = {
        tableEntries
            .filter( entry => entry._1.equals(symbol))
            .nonEmpty
    }

    def getTableEntry(symbol: String, tableType: SymbolTableType): tableEntry = {
        tableEntries
            .filter( entry => entry._1.equals(symbol))
            .filter( entry => entry._2 == tableType)
            .head
    }

    def scope: Scope = tableEntries.head._3

    def getTag: String = tag

    override def toString: String = {
        val sb = new StringBuilder
        sb.append("\t" + tag + "\t\n")
        sb.append("symbol\t\tsymbol type\t\tscope\t\t\t\t\t\t\t\t\t\tnode\n")
        for (entry <- tableEntries) {
            val scope = if (entry._3 != null) entry._3.getClass else null
            sb
                .append(entry._1 + "\t\t")
                .append(entry._2 + "\t\t\t")
                .append(scope + "\t\t\t")
                .append(entry._4.getClass + "\t\n")
        }
        sb.toString()
    }
}
