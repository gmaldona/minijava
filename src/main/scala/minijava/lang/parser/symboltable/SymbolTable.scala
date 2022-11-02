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

/** Data Structure that contains symbols that are contained within a given scope
 *
 * @param tag Symbol Table Tag - descriptive with scope
 */
class SymbolTable(tag: String) {

    type tableEntry = (String, SymbolTableType, Scope, ASTNode)

    var parentSymbolTable: Option[SymbolTable] = None
    var childrenSymbolTables: List[SymbolTable] = List()
    var tableEntries: List[tableEntry] = List()

    def scope: Scope = tableEntries.head._3

    def getTag: String = tag

    def setParentSymbolTable(symbolTable: SymbolTable): Unit = parentSymbolTable = Some(symbolTable)

    def addChildSymbolTable(symbolTable: SymbolTable): Unit = childrenSymbolTables = symbolTable :: childrenSymbolTables


    def addEntry(entry: tableEntry): Unit = tableEntries = entry :: tableEntries

    /**
     * @param symbol     Symbol
     * @param symbolType The type of the symbol
     * @param scope      The scope at which the symbol is attached to
     * @param node       The node that the symbol represents
     */
    def addEntry(symbol: String,
                 symbolType: SymbolTableType,
                 scope: Scope,
                 node: ASTNode): Unit = tableEntries = (symbol, symbolType, scope, node) :: tableEntries

    /** Add multiple entries to the table at once */
    def addEntries(entries: List[tableEntry]): Unit = {
        for (entry <- entries)
            addEntry(
                entry._1,
                entry._2,
                entry._3,
                entry._4
            )
    }

    /** @return If the table contains a symbol */
    def containsSymbol(symbol: String): Boolean = tableEntries.exists(entry => entry._1.equals(symbol))

    /** @return If the table contains a class name */
    def containsClass(symbol: String): Boolean =
        tableEntries
            .filter(entry => entry._1.equals(symbol))
            .exists(entry => entry._2 == SymbolTableType.Class)

    /** @return The child symbol table with a specific tag */
    def getChildSymbolTable(tag: String): Option[SymbolTable] =
        childrenSymbolTables.find(table => table.getTag.equals(tag))

    /** @return The ClassDecl Node with a specific symbol */
    def getClassNode(symbol: String): ClassDecl =
        tableEntries
            .filter( entry => entry._1.equals(symbol))
            .filter( entry => entry._2 == SymbolTableType.Class)
            .head._4.asInstanceOf[ClassDecl]

    /** @return a list of table entries with a given symbol and table type */
    def getTableEntries(symbol: String, tableType: SymbolTableType): List[tableEntry] = {
        tableEntries
            .filter( entry => entry._1.equals(symbol))
            .filter( entry => entry._2 == tableType)
    }

    /** @return a table entry with a given symbol and table type */
    def getTableEntry(symbol: String, tableType: SymbolTableType): tableEntry =
        tableEntries
            .filter( entry => entry._1.equals(symbol))
            .filter( entry => entry._2 == tableType)
            .head
}
