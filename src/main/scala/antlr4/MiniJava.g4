grammar MiniJava;

program: mainClass (classDeclaration)* EOF;

mainClass:  'class' Identifier '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' Identifier ')' '{' statement '}' '}';

classDeclaration: 'class' Identifier ('extends' Identifier)? '{' (varDeclaration)* (methodDeclaration)* '}';

varDeclaration: type Identifier ';';

methodDeclaration: 	'public'type Identifier '(' (type Identifier (','type Identifier)* )? ')' '{' (varDeclaration)* (statement)* 'return' expression ';' '}';

type:
        'int' '[' ']'
    |   'boolean'
    |   'int'
    |   Identifier
    ;

    /*  for (int i = 0; i < expression; i=i+8)    */

statement:
        '{' (statement)* '}'
    |   'if' '(' expression ')' statement 'else' statement
    |   'while' '(' expression ')' statement
    |   'for' '(' 'int' Identifier '=' expression ';' expression ';' Identifier '=' expression ')' statement
    |   'System.out.println' '(' expression ')' ';'
    |   Identifier '=' expression ';'
    |   Identifier  '[' expression ']' '=' expression ';'
    ;

expression:
        IntegerLiteral expression2
    |   'true' expression2
    |   'false' expression2
    |   Identifier expression2
    |   'this' expression2
    |   'new' 'int' '[' expression ']' expression2
    |   'new' Identifier '(' ')' expression2
    |   '!' expression expression2
    |   '(' expression ')' expression2
    ;

expression2:
        '.' Identifier '(' (expression(',' expression)*)? ')' expression2
    |   '.'  'length' expression2
    |   '[' expression ']' expression2
    |   ('&&' | '<' | '+' | '-' | '*') expression expression2
    |   /* epsilon */
    ;

IntegerLiteral:  [0-9]+ ;
Identifier: [a-zA-Z_]+[a-zA-Z_0-9]*;
WS : [ \t\r\n]+ -> skip ;
COMMENT : '//' .+? ('\n'|EOF) -> skip ;





