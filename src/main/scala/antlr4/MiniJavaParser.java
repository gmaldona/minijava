// Generated from /Users/gregory/workspace/Oswego/ComputerScience/CSC444/minijava/src/main/scala/antlr4/MiniJava.g4 by ANTLR 4.10.1
package antlr4;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniJavaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, IntegerLiteral=37, Identifier=38, 
		WS=39, COMMENT=40;
	public static final int
		RULE_program = 0, RULE_mainClass = 1, RULE_classDeclaration = 2, RULE_varDeclaration = 3, 
		RULE_methodDeclaration = 4, RULE_type = 5, RULE_statement = 6, RULE_expression = 7, 
		RULE_expression2 = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "mainClass", "classDeclaration", "varDeclaration", "methodDeclaration", 
			"type", "statement", "expression", "expression2"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'{'", "'public'", "'static'", "'void'", "'main'", "'('", 
			"'String'", "'['", "']'", "')'", "'}'", "'extends'", "';'", "','", "'return'", 
			"'int'", "'boolean'", "'if'", "'else'", "'while'", "'for'", "'='", "'System.out.println'", 
			"'true'", "'false'", "'this'", "'new'", "'!'", "'.'", "'length'", "'&&'", 
			"'<'", "'+'", "'-'", "'*'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "IntegerLiteral", "Identifier", "WS", "COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MiniJava.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MiniJavaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public MainClassContext mainClass() {
			return getRuleContext(MainClassContext.class,0);
		}
		public TerminalNode EOF() { return getToken(MiniJavaParser.EOF, 0); }
		public List<ClassDeclarationContext> classDeclaration() {
			return getRuleContexts(ClassDeclarationContext.class);
		}
		public ClassDeclarationContext classDeclaration(int i) {
			return getRuleContext(ClassDeclarationContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			mainClass();
			setState(22);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(19);
				classDeclaration();
				}
				}
				setState(24);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(25);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MainClassContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(MiniJavaParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(MiniJavaParser.Identifier, i);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public MainClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainClass; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterMainClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitMainClass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitMainClass(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MainClassContext mainClass() throws RecognitionException {
		MainClassContext _localctx = new MainClassContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_mainClass);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			match(T__0);
			setState(28);
			match(Identifier);
			setState(29);
			match(T__1);
			setState(30);
			match(T__2);
			setState(31);
			match(T__3);
			setState(32);
			match(T__4);
			setState(33);
			match(T__5);
			setState(34);
			match(T__6);
			setState(35);
			match(T__7);
			setState(36);
			match(T__8);
			setState(37);
			match(T__9);
			setState(38);
			match(Identifier);
			setState(39);
			match(T__10);
			setState(40);
			match(T__1);
			setState(41);
			statement();
			setState(42);
			match(T__11);
			setState(43);
			match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(MiniJavaParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(MiniJavaParser.Identifier, i);
		}
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public List<MethodDeclarationContext> methodDeclaration() {
			return getRuleContexts(MethodDeclarationContext.class);
		}
		public MethodDeclarationContext methodDeclaration(int i) {
			return getRuleContext(MethodDeclarationContext.class,i);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitClassDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			match(T__0);
			setState(46);
			match(Identifier);
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__12) {
				{
				setState(47);
				match(T__12);
				setState(48);
				match(Identifier);
				}
			}

			setState(51);
			match(T__1);
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__17) | (1L << Identifier))) != 0)) {
				{
				{
				setState(52);
				varDeclaration();
				}
				}
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(58);
				methodDeclaration();
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(64);
			match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MiniJavaParser.Identifier, 0); }
		public VarDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterVarDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitVarDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitVarDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclarationContext varDeclaration() throws RecognitionException {
		VarDeclarationContext _localctx = new VarDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_varDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			type();
			setState(67);
			match(Identifier);
			setState(68);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclarationContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> Identifier() { return getTokens(MiniJavaParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(MiniJavaParser.Identifier, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterMethodDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitMethodDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_methodDeclaration);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(T__2);
			setState(71);
			type();
			setState(72);
			match(Identifier);
			setState(73);
			match(T__6);
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__17) | (1L << Identifier))) != 0)) {
				{
				setState(74);
				type();
				setState(75);
				match(Identifier);
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__14) {
					{
					{
					setState(76);
					match(T__14);
					setState(77);
					type();
					setState(78);
					match(Identifier);
					}
					}
					setState(84);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(87);
			match(T__10);
			setState(88);
			match(T__1);
			setState(92);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(89);
					varDeclaration();
					}
					} 
				}
				setState(94);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << T__23) | (1L << Identifier))) != 0)) {
				{
				{
				setState(95);
				statement();
				}
				}
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(101);
			match(T__15);
			setState(102);
			expression();
			setState(103);
			match(T__13);
			setState(104);
			match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MiniJavaParser.Identifier, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_type);
		try {
			setState(112);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				match(T__16);
				setState(107);
				match(T__8);
				setState(108);
				match(T__9);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(109);
				match(T__17);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(110);
				match(T__16);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(111);
				match(Identifier);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> Identifier() { return getTokens(MiniJavaParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(MiniJavaParser.Identifier, i);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_statement);
		int _la;
		try {
			setState(170);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(114);
				match(T__1);
				setState(118);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__18) | (1L << T__20) | (1L << T__21) | (1L << T__23) | (1L << Identifier))) != 0)) {
					{
					{
					setState(115);
					statement();
					}
					}
					setState(120);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(121);
				match(T__11);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(122);
				match(T__18);
				setState(123);
				match(T__6);
				setState(124);
				expression();
				setState(125);
				match(T__10);
				setState(126);
				statement();
				setState(127);
				match(T__19);
				setState(128);
				statement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(130);
				match(T__20);
				setState(131);
				match(T__6);
				setState(132);
				expression();
				setState(133);
				match(T__10);
				setState(134);
				statement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(136);
				match(T__21);
				setState(137);
				match(T__6);
				setState(138);
				match(T__16);
				setState(139);
				match(Identifier);
				setState(140);
				match(T__22);
				setState(141);
				expression();
				setState(142);
				match(T__13);
				setState(143);
				expression();
				setState(144);
				match(T__13);
				setState(145);
				match(Identifier);
				setState(146);
				match(T__22);
				setState(147);
				expression();
				setState(148);
				match(T__10);
				setState(149);
				statement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(151);
				match(T__23);
				setState(152);
				match(T__6);
				setState(153);
				expression();
				setState(154);
				match(T__10);
				setState(155);
				match(T__13);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(157);
				match(Identifier);
				setState(158);
				match(T__22);
				setState(159);
				expression();
				setState(160);
				match(T__13);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(162);
				match(Identifier);
				setState(163);
				match(T__8);
				setState(164);
				expression();
				setState(165);
				match(T__9);
				setState(166);
				match(T__22);
				setState(167);
				expression();
				setState(168);
				match(T__13);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode IntegerLiteral() { return getToken(MiniJavaParser.IntegerLiteral, 0); }
		public Expression2Context expression2() {
			return getRuleContext(Expression2Context.class,0);
		}
		public TerminalNode Identifier() { return getToken(MiniJavaParser.Identifier, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_expression);
		try {
			setState(203);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(172);
				match(IntegerLiteral);
				setState(173);
				expression2();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(174);
				match(T__24);
				setState(175);
				expression2();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(176);
				match(T__25);
				setState(177);
				expression2();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(178);
				match(Identifier);
				setState(179);
				expression2();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(180);
				match(T__26);
				setState(181);
				expression2();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(182);
				match(T__27);
				setState(183);
				match(T__16);
				setState(184);
				match(T__8);
				setState(185);
				expression();
				setState(186);
				match(T__9);
				setState(187);
				expression2();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(189);
				match(T__27);
				setState(190);
				match(Identifier);
				setState(191);
				match(T__6);
				setState(192);
				match(T__10);
				setState(193);
				expression2();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(194);
				match(T__28);
				setState(195);
				expression();
				setState(196);
				expression2();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(198);
				match(T__6);
				setState(199);
				expression();
				setState(200);
				match(T__10);
				setState(201);
				expression2();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expression2Context extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MiniJavaParser.Identifier, 0); }
		public Expression2Context expression2() {
			return getRuleContext(Expression2Context.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterExpression2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitExpression2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitExpression2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression2Context expression2() throws RecognitionException {
		Expression2Context _localctx = new Expression2Context(_ctx, getState());
		enterRule(_localctx, 16, RULE_expression2);
		int _la;
		try {
			setState(233);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(205);
				match(T__29);
				setState(206);
				match(Identifier);
				setState(207);
				match(T__6);
				setState(216);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << IntegerLiteral) | (1L << Identifier))) != 0)) {
					{
					setState(208);
					expression();
					setState(213);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__14) {
						{
						{
						setState(209);
						match(T__14);
						setState(210);
						expression();
						}
						}
						setState(215);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(218);
				match(T__10);
				setState(219);
				expression2();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(220);
				match(T__29);
				setState(221);
				match(T__30);
				setState(222);
				expression2();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(223);
				match(T__8);
				setState(224);
				expression();
				setState(225);
				match(T__9);
				setState(226);
				expression2();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(228);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(229);
				expression();
				setState(230);
				expression2();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001(\u00ec\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0001\u0000\u0001\u0000\u0005\u0000\u0015\b\u0000\n\u0000\f"+
		"\u0000\u0018\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0003\u00022\b\u0002\u0001\u0002\u0001\u0002\u0005"+
		"\u00026\b\u0002\n\u0002\f\u00029\t\u0002\u0001\u0002\u0005\u0002<\b\u0002"+
		"\n\u0002\f\u0002?\t\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0005\u0004Q\b\u0004\n\u0004\f\u0004T\t\u0004\u0003\u0004V\b\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0005\u0004[\b\u0004\n\u0004\f\u0004^\t"+
		"\u0004\u0001\u0004\u0005\u0004a\b\u0004\n\u0004\f\u0004d\t\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005q\b"+
		"\u0005\u0001\u0006\u0001\u0006\u0005\u0006u\b\u0006\n\u0006\f\u0006x\t"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0003\u0006\u00ab\b\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u00cc"+
		"\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005\b\u00d4"+
		"\b\b\n\b\f\b\u00d7\t\b\u0003\b\u00d9\b\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\b\u00ea\b\b\u0001\b\u0000\u0000\t\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0000\u0001\u0001\u0000 $\u0102\u0000\u0012"+
		"\u0001\u0000\u0000\u0000\u0002\u001b\u0001\u0000\u0000\u0000\u0004-\u0001"+
		"\u0000\u0000\u0000\u0006B\u0001\u0000\u0000\u0000\bF\u0001\u0000\u0000"+
		"\u0000\np\u0001\u0000\u0000\u0000\f\u00aa\u0001\u0000\u0000\u0000\u000e"+
		"\u00cb\u0001\u0000\u0000\u0000\u0010\u00e9\u0001\u0000\u0000\u0000\u0012"+
		"\u0016\u0003\u0002\u0001\u0000\u0013\u0015\u0003\u0004\u0002\u0000\u0014"+
		"\u0013\u0001\u0000\u0000\u0000\u0015\u0018\u0001\u0000\u0000\u0000\u0016"+
		"\u0014\u0001\u0000\u0000\u0000\u0016\u0017\u0001\u0000\u0000\u0000\u0017"+
		"\u0019\u0001\u0000\u0000\u0000\u0018\u0016\u0001\u0000\u0000\u0000\u0019"+
		"\u001a\u0005\u0000\u0000\u0001\u001a\u0001\u0001\u0000\u0000\u0000\u001b"+
		"\u001c\u0005\u0001\u0000\u0000\u001c\u001d\u0005&\u0000\u0000\u001d\u001e"+
		"\u0005\u0002\u0000\u0000\u001e\u001f\u0005\u0003\u0000\u0000\u001f \u0005"+
		"\u0004\u0000\u0000 !\u0005\u0005\u0000\u0000!\"\u0005\u0006\u0000\u0000"+
		"\"#\u0005\u0007\u0000\u0000#$\u0005\b\u0000\u0000$%\u0005\t\u0000\u0000"+
		"%&\u0005\n\u0000\u0000&\'\u0005&\u0000\u0000\'(\u0005\u000b\u0000\u0000"+
		"()\u0005\u0002\u0000\u0000)*\u0003\f\u0006\u0000*+\u0005\f\u0000\u0000"+
		"+,\u0005\f\u0000\u0000,\u0003\u0001\u0000\u0000\u0000-.\u0005\u0001\u0000"+
		"\u0000.1\u0005&\u0000\u0000/0\u0005\r\u0000\u000002\u0005&\u0000\u0000"+
		"1/\u0001\u0000\u0000\u000012\u0001\u0000\u0000\u000023\u0001\u0000\u0000"+
		"\u000037\u0005\u0002\u0000\u000046\u0003\u0006\u0003\u000054\u0001\u0000"+
		"\u0000\u000069\u0001\u0000\u0000\u000075\u0001\u0000\u0000\u000078\u0001"+
		"\u0000\u0000\u00008=\u0001\u0000\u0000\u000097\u0001\u0000\u0000\u0000"+
		":<\u0003\b\u0004\u0000;:\u0001\u0000\u0000\u0000<?\u0001\u0000\u0000\u0000"+
		"=;\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000>@\u0001\u0000\u0000"+
		"\u0000?=\u0001\u0000\u0000\u0000@A\u0005\f\u0000\u0000A\u0005\u0001\u0000"+
		"\u0000\u0000BC\u0003\n\u0005\u0000CD\u0005&\u0000\u0000DE\u0005\u000e"+
		"\u0000\u0000E\u0007\u0001\u0000\u0000\u0000FG\u0005\u0003\u0000\u0000"+
		"GH\u0003\n\u0005\u0000HI\u0005&\u0000\u0000IU\u0005\u0007\u0000\u0000"+
		"JK\u0003\n\u0005\u0000KR\u0005&\u0000\u0000LM\u0005\u000f\u0000\u0000"+
		"MN\u0003\n\u0005\u0000NO\u0005&\u0000\u0000OQ\u0001\u0000\u0000\u0000"+
		"PL\u0001\u0000\u0000\u0000QT\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000"+
		"\u0000RS\u0001\u0000\u0000\u0000SV\u0001\u0000\u0000\u0000TR\u0001\u0000"+
		"\u0000\u0000UJ\u0001\u0000\u0000\u0000UV\u0001\u0000\u0000\u0000VW\u0001"+
		"\u0000\u0000\u0000WX\u0005\u000b\u0000\u0000X\\\u0005\u0002\u0000\u0000"+
		"Y[\u0003\u0006\u0003\u0000ZY\u0001\u0000\u0000\u0000[^\u0001\u0000\u0000"+
		"\u0000\\Z\u0001\u0000\u0000\u0000\\]\u0001\u0000\u0000\u0000]b\u0001\u0000"+
		"\u0000\u0000^\\\u0001\u0000\u0000\u0000_a\u0003\f\u0006\u0000`_\u0001"+
		"\u0000\u0000\u0000ad\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000"+
		"bc\u0001\u0000\u0000\u0000ce\u0001\u0000\u0000\u0000db\u0001\u0000\u0000"+
		"\u0000ef\u0005\u0010\u0000\u0000fg\u0003\u000e\u0007\u0000gh\u0005\u000e"+
		"\u0000\u0000hi\u0005\f\u0000\u0000i\t\u0001\u0000\u0000\u0000jk\u0005"+
		"\u0011\u0000\u0000kl\u0005\t\u0000\u0000lq\u0005\n\u0000\u0000mq\u0005"+
		"\u0012\u0000\u0000nq\u0005\u0011\u0000\u0000oq\u0005&\u0000\u0000pj\u0001"+
		"\u0000\u0000\u0000pm\u0001\u0000\u0000\u0000pn\u0001\u0000\u0000\u0000"+
		"po\u0001\u0000\u0000\u0000q\u000b\u0001\u0000\u0000\u0000rv\u0005\u0002"+
		"\u0000\u0000su\u0003\f\u0006\u0000ts\u0001\u0000\u0000\u0000ux\u0001\u0000"+
		"\u0000\u0000vt\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000wy\u0001"+
		"\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000y\u00ab\u0005\f\u0000\u0000"+
		"z{\u0005\u0013\u0000\u0000{|\u0005\u0007\u0000\u0000|}\u0003\u000e\u0007"+
		"\u0000}~\u0005\u000b\u0000\u0000~\u007f\u0003\f\u0006\u0000\u007f\u0080"+
		"\u0005\u0014\u0000\u0000\u0080\u0081\u0003\f\u0006\u0000\u0081\u00ab\u0001"+
		"\u0000\u0000\u0000\u0082\u0083\u0005\u0015\u0000\u0000\u0083\u0084\u0005"+
		"\u0007\u0000\u0000\u0084\u0085\u0003\u000e\u0007\u0000\u0085\u0086\u0005"+
		"\u000b\u0000\u0000\u0086\u0087\u0003\f\u0006\u0000\u0087\u00ab\u0001\u0000"+
		"\u0000\u0000\u0088\u0089\u0005\u0016\u0000\u0000\u0089\u008a\u0005\u0007"+
		"\u0000\u0000\u008a\u008b\u0005\u0011\u0000\u0000\u008b\u008c\u0005&\u0000"+
		"\u0000\u008c\u008d\u0005\u0017\u0000\u0000\u008d\u008e\u0003\u000e\u0007"+
		"\u0000\u008e\u008f\u0005\u000e\u0000\u0000\u008f\u0090\u0003\u000e\u0007"+
		"\u0000\u0090\u0091\u0005\u000e\u0000\u0000\u0091\u0092\u0005&\u0000\u0000"+
		"\u0092\u0093\u0005\u0017\u0000\u0000\u0093\u0094\u0003\u000e\u0007\u0000"+
		"\u0094\u0095\u0005\u000b\u0000\u0000\u0095\u0096\u0003\f\u0006\u0000\u0096"+
		"\u00ab\u0001\u0000\u0000\u0000\u0097\u0098\u0005\u0018\u0000\u0000\u0098"+
		"\u0099\u0005\u0007\u0000\u0000\u0099\u009a\u0003\u000e\u0007\u0000\u009a"+
		"\u009b\u0005\u000b\u0000\u0000\u009b\u009c\u0005\u000e\u0000\u0000\u009c"+
		"\u00ab\u0001\u0000\u0000\u0000\u009d\u009e\u0005&\u0000\u0000\u009e\u009f"+
		"\u0005\u0017\u0000\u0000\u009f\u00a0\u0003\u000e\u0007\u0000\u00a0\u00a1"+
		"\u0005\u000e\u0000\u0000\u00a1\u00ab\u0001\u0000\u0000\u0000\u00a2\u00a3"+
		"\u0005&\u0000\u0000\u00a3\u00a4\u0005\t\u0000\u0000\u00a4\u00a5\u0003"+
		"\u000e\u0007\u0000\u00a5\u00a6\u0005\n\u0000\u0000\u00a6\u00a7\u0005\u0017"+
		"\u0000\u0000\u00a7\u00a8\u0003\u000e\u0007\u0000\u00a8\u00a9\u0005\u000e"+
		"\u0000\u0000\u00a9\u00ab\u0001\u0000\u0000\u0000\u00aar\u0001\u0000\u0000"+
		"\u0000\u00aaz\u0001\u0000\u0000\u0000\u00aa\u0082\u0001\u0000\u0000\u0000"+
		"\u00aa\u0088\u0001\u0000\u0000\u0000\u00aa\u0097\u0001\u0000\u0000\u0000"+
		"\u00aa\u009d\u0001\u0000\u0000\u0000\u00aa\u00a2\u0001\u0000\u0000\u0000"+
		"\u00ab\r\u0001\u0000\u0000\u0000\u00ac\u00ad\u0005%\u0000\u0000\u00ad"+
		"\u00cc\u0003\u0010\b\u0000\u00ae\u00af\u0005\u0019\u0000\u0000\u00af\u00cc"+
		"\u0003\u0010\b\u0000\u00b0\u00b1\u0005\u001a\u0000\u0000\u00b1\u00cc\u0003"+
		"\u0010\b\u0000\u00b2\u00b3\u0005&\u0000\u0000\u00b3\u00cc\u0003\u0010"+
		"\b\u0000\u00b4\u00b5\u0005\u001b\u0000\u0000\u00b5\u00cc\u0003\u0010\b"+
		"\u0000\u00b6\u00b7\u0005\u001c\u0000\u0000\u00b7\u00b8\u0005\u0011\u0000"+
		"\u0000\u00b8\u00b9\u0005\t\u0000\u0000\u00b9\u00ba\u0003\u000e\u0007\u0000"+
		"\u00ba\u00bb\u0005\n\u0000\u0000\u00bb\u00bc\u0003\u0010\b\u0000\u00bc"+
		"\u00cc\u0001\u0000\u0000\u0000\u00bd\u00be\u0005\u001c\u0000\u0000\u00be"+
		"\u00bf\u0005&\u0000\u0000\u00bf\u00c0\u0005\u0007\u0000\u0000\u00c0\u00c1"+
		"\u0005\u000b\u0000\u0000\u00c1\u00cc\u0003\u0010\b\u0000\u00c2\u00c3\u0005"+
		"\u001d\u0000\u0000\u00c3\u00c4\u0003\u000e\u0007\u0000\u00c4\u00c5\u0003"+
		"\u0010\b\u0000\u00c5\u00cc\u0001\u0000\u0000\u0000\u00c6\u00c7\u0005\u0007"+
		"\u0000\u0000\u00c7\u00c8\u0003\u000e\u0007\u0000\u00c8\u00c9\u0005\u000b"+
		"\u0000\u0000\u00c9\u00ca\u0003\u0010\b\u0000\u00ca\u00cc\u0001\u0000\u0000"+
		"\u0000\u00cb\u00ac\u0001\u0000\u0000\u0000\u00cb\u00ae\u0001\u0000\u0000"+
		"\u0000\u00cb\u00b0\u0001\u0000\u0000\u0000\u00cb\u00b2\u0001\u0000\u0000"+
		"\u0000\u00cb\u00b4\u0001\u0000\u0000\u0000\u00cb\u00b6\u0001\u0000\u0000"+
		"\u0000\u00cb\u00bd\u0001\u0000\u0000\u0000\u00cb\u00c2\u0001\u0000\u0000"+
		"\u0000\u00cb\u00c6\u0001\u0000\u0000\u0000\u00cc\u000f\u0001\u0000\u0000"+
		"\u0000\u00cd\u00ce\u0005\u001e\u0000\u0000\u00ce\u00cf\u0005&\u0000\u0000"+
		"\u00cf\u00d8\u0005\u0007\u0000\u0000\u00d0\u00d5\u0003\u000e\u0007\u0000"+
		"\u00d1\u00d2\u0005\u000f\u0000\u0000\u00d2\u00d4\u0003\u000e\u0007\u0000"+
		"\u00d3\u00d1\u0001\u0000\u0000\u0000\u00d4\u00d7\u0001\u0000\u0000\u0000"+
		"\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d5\u00d6\u0001\u0000\u0000\u0000"+
		"\u00d6\u00d9\u0001\u0000\u0000\u0000\u00d7\u00d5\u0001\u0000\u0000\u0000"+
		"\u00d8\u00d0\u0001\u0000\u0000\u0000\u00d8\u00d9\u0001\u0000\u0000\u0000"+
		"\u00d9\u00da\u0001\u0000\u0000\u0000\u00da\u00db\u0005\u000b\u0000\u0000"+
		"\u00db\u00ea\u0003\u0010\b\u0000\u00dc\u00dd\u0005\u001e\u0000\u0000\u00dd"+
		"\u00de\u0005\u001f\u0000\u0000\u00de\u00ea\u0003\u0010\b\u0000\u00df\u00e0"+
		"\u0005\t\u0000\u0000\u00e0\u00e1\u0003\u000e\u0007\u0000\u00e1\u00e2\u0005"+
		"\n\u0000\u0000\u00e2\u00e3\u0003\u0010\b\u0000\u00e3\u00ea\u0001\u0000"+
		"\u0000\u0000\u00e4\u00e5\u0007\u0000\u0000\u0000\u00e5\u00e6\u0003\u000e"+
		"\u0007\u0000\u00e6\u00e7\u0003\u0010\b\u0000\u00e7\u00ea\u0001\u0000\u0000"+
		"\u0000\u00e8\u00ea\u0001\u0000\u0000\u0000\u00e9\u00cd\u0001\u0000\u0000"+
		"\u0000\u00e9\u00dc\u0001\u0000\u0000\u0000\u00e9\u00df\u0001\u0000\u0000"+
		"\u0000\u00e9\u00e4\u0001\u0000\u0000\u0000\u00e9\u00e8\u0001\u0000\u0000"+
		"\u0000\u00ea\u0011\u0001\u0000\u0000\u0000\u000f\u001617=RU\\bpv\u00aa"+
		"\u00cb\u00d5\u00d8\u00e9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}