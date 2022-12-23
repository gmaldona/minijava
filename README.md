# <span> A compiler for MiniJava <img src="https://img.icons8.com/color/48/000000/java-coffee-cup-logo--v1.png"/></span> 
MiniJava is a subset of the Java language

A few examples of the MiniJava language can be found <a href="https://www.cambridge.org/resources/052182060X/">here</a>. <br>
The grammar used in this project comes from the grammar found on <a href="http://www.cs.tufts.edu/~sguyer/classes/comp181-2006/minijava.html">here</a>. <br>

<hr>

## <u> Downloading the ANTLR4 Tools:</u>

From the root directory of the project, run the following script to install the ANTLR4 tools.
```shell
$ ./scripts/build_venv.sh
```

## <u> Compiling the MiniJava Grammar using ANTLR4:</u>
From the root directory of the project, run the following script to compile the MiniJava Grammar using ANTLR4.
```shell
$ ./scripts/compile_grammar.sh
```

Phase 1: Parsing ✅ <br>
Phase 2: Intermediate Representation/AST ✅ <br>
Phase 3: Code Generation (WIP) <br>
