grammar MiniJava;

@header {
package antlr4;
}

program: mainClass (classDeclaration)* EOF;

mainClass:  'class' Identifier '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' Identifier ')' '{' statement '}' '}';

classDeclaration: 'class' Identifier ('extends' Identifier)? '{' (varDeclaration)* (methodDeclaration)* '}';

varDeclaration: type Identifier ';';

methodDeclaration: 	'public'type Identifier '(' (type Identifier (','type Identifier)* )? ')' '{' (varDeclaration)* (statement)* 'return' expression ';' '}';

type:
        'int' '[' ']' # intArrayType
    |   'boolean'     # boolType
    |   'int'         # intType
    |   Identifier    # idType
    ;

statement:
        '{' (statement)* '}'                                                                                    # statementBlock
    |   'if' '(' expression ')' statement 'else' statement                                                      # ifStatement
    |   'while' '(' expression ')' statement                                                                    # whileLoop
    |   'for' '(' 'int' Identifier '=' expression ';' expression ';' Identifier '=' expression ')' statement    # forLoop
    |   'System.out.println' '(' expression ')' ';'                                                             # printExpr
    |   Identifier '=' expression ';'                                                                           # assign
    |   Identifier  '[' expression ']' '=' expression ';'                                                       # arrayAssign
    ;

expression:
        IntegerLiteral expression2                      # exprNumber
    |   'true' expression2                              # exprTrue
    |   'false' expression2                             # exprFalse
    |   Identifier expression2                          # exprId
    |   'this' expression2                              # exprThis
    |   'new' 'int' '[' expression ']' expression2      # newIntArray
    |   'new' Identifier '(' ')' expression2            # newObject
    |   '!' expression expression2                      # exprNot
    |   '(' expression ')' expression2                  # exprParenthesis
    ;

expression2:
        '.' Identifier '(' (expression(',' expression)*)? ')' expression2   # exprFunction
    |   '.'  'length' expression2                                           # exprLength
    |   '[' expression ']' expression2                                      # expr
    |   ('&&' | '<' | '+' | '-' | '*') expression expression2               # exprOp
    |   /* epsilon */                                                       # epilson
    ;

IntegerLiteral:  [0-9]+ ;
Identifier: [a-zA-Z_]+[a-zA-Z_0-9]*;
WS : [ \t\r\n]+ -> skip ;
COMMENT : '//' .+? ('\n'|EOF) -> skip ;



