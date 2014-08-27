import java_cup.runtime.*;

%%

%public
%class Scanner

%unicode
%standalone

%line
%column

%cup
%cupdebug


%{
  StringBuilder string = new StringBuilder();
  
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }
%}


LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]


Comment = {TraditionalComment} | {EndOfLineComment}  

TraditionalComment = "/*" [^*] ~"*/" 
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?

Identifier = [:jletter:][:jletterdigit:]*

DecIntegerLiteral = 0 | [1-9][0-9]*
    
FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}) {Exponent}? [fF]

FLit1    = [0-9]+ \. [0-9]* 
FLit2    = \. [0-9]+ 
FLit3    = [0-9]+ 
Exponent = [eE] [+-]? [0-9]+


StringCharacter = [^\r\n\"\\]

%state STRING

%%
   
<YYINITIAL> {

  /* keywords */
   "if"   			 	 { return new Symbol(sym.IF); }
   "int"   			 	 { System.out.println(yytext());return symbol(sym.INT); }
   "for"   			 	 { return symbol(sym.FOR); }
   "else"   			 { return symbol(sym.ELSE); }
   "void"   			 { return symbol(sym.VOID); }
   "break"   			 { return symbol(sym.BREAK); }
   "class"   			 { System.out.println(yytext());return symbol(sym.CLASS); }
   "float"   			 { return symbol(sym.FLOAT); }
   "while"   			 { return symbol(sym.WHILE); }
   "return"   		 	 { System.out.println(yytext());return symbol(sym.RETURN); }
   "boolean"   		 	 { return symbol(sym.BOOLEAN_LITERAL); }
   "continue"   		 { return symbol(sym.CONTINUE); }

  /* boolean literals */
  "true"                         { return symbol(sym.BOOLEAN_LITERAL, true); }
  "false"                        { return symbol(sym.BOOLEAN_LITERAL, false); }
  
  
  /* separators */
  "("                            { System.out.println(yytext());return symbol(sym.LPAREN); }
  ")"                            { System.out.println(yytext());return symbol(sym.RPAREN); }
  "{"                            { System.out.println(yytext());return symbol(sym.LBRACE); }
  "}"                            { System.out.println(yytext());return symbol(sym.RBRACE); }
  "["                            { return symbol(sym.LBRACK); }
  "]"                            { return symbol(sym.RBRACK); }
  ";"                            { System.out.println(yytext());return symbol(sym.SEMICOLON); }
  ","                            { return symbol(sym.COMMA); }
  
  /* operators */
  "="                            { System.out.println(yytext());return symbol(sym.EQ); }
  ">"                            { return symbol(sym.GT); }
  "<"                            { return symbol(sym.LT); }
  "!"                            { return symbol(sym.NOT); }
  "=="                           { System.out.println(yytext());return symbol(sym.EQEQ); }
  "<="                           { return symbol(sym.LTEQ); }
  ">="                           { return symbol(sym.GTEQ); }
  "!="                           { return symbol(sym.NOTEQ); }
  "&&"                           { return symbol(sym.ANDAND); }
  "||"                           { return symbol(sym.OROR); }
  "+"                            { System.out.println(yytext());return symbol(sym.PLUS); }
  "-"                            { return symbol(sym.MINUS); }
  "*"                            { return symbol(sym.MULT); }
  "/"                            { return symbol(sym.DIV); }
  "%"                            { return symbol(sym.MOD); }
  
  
  {DecIntegerLiteral}            { return symbol(sym.INT_LITERAL, new Integer(yytext())); }
  
  {FloatLiteral}                 { return symbol(sym.FLOAT_LITERAL, new Float(yytext().substring(0,yylength()-1))); }
  
  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */ 
  {Identifier}                   { System.out.println(yytext());return symbol(sym.ID, yytext()); }  

  .         { System.out.println("error");}
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return symbol(sym.STRING_LITERAL, string.toString()); }
  
  {StringCharacter}+             { string.append( yytext() ); }
  
  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  
  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new RuntimeException("Unterminated string at end of line"); }
}
