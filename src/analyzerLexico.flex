/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: analisisLexico.flex
 * To Create: jflex analisisLexico.flex
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 */
import java_cup.runtime.*;
import ir.ast.*;

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

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?

Identifier = [:jletter:][:jletterdigit:]*

DecIntegerLiteral = 0 | [1-9][0-9]*

FloatLiteral = [0-9]*"."[0-9]*
    
StringCharacter = [^\r\n\"\\]

%state STRING

%%
   
<YYINITIAL> {

  \"                            { yybegin(STRING); string.setLength(0); }

  /* keywords */
   "if"   			 	              { return new Symbol(sym.IF); }
   "int"   			 	              { return new Symbol(sym.TINT); }
   "for"   			 	              { return new Symbol(sym.FOR); }
   "else"   			              { return new Symbol(sym.ELSE); }
   "void"   			              { return new Symbol(sym.VOID); }
   "break"   			              { return new Symbol(sym.BREAK); }
   "class"   			              { return new Symbol(sym.CLASS); }
   "float"   			              { return new Symbol(sym.TFLOAT); }
   "while"   			              { return new Symbol(sym.WHILE); }
   "return"   		 	            { return new Symbol(sym.RETURN); }
   "boolean"   		 	            { return new Symbol(sym.TBOOLEAN); }
   "continue"   		            { return new Symbol(sym.CONTINUE); }

  /* boolean literals */
  "false" | "true"              { return new Symbol(sym.BOOL_LITERAL); }

  /* separators */
  "("                            { return new Symbol(sym.LPAREN); }
  ")"                            { return new Symbol(sym.RPAREN); }
  "{"                            { return new Symbol(sym.LBRACE); }
  "}"                            { return new Symbol(sym.RBRACE); }
  "["                            { return new Symbol(sym.LBRACK); }
  "]"                            { return new Symbol(sym.RBRACK); }
  ";"                            { return new Symbol(sym.SEMICOLON); }
  ","                            { return new Symbol(sym.COMMA); }
  
  /* operators */
  "="                            { return new Symbol(sym.EQ); }
  ">"                            { return new Symbol(sym.GT); }
  "<"                            { return new Symbol(sym.LT); }
  "!"                            { return new Symbol(sym.NOT); }
  "=="                           { return new Symbol(sym.EQEQ); }
  "<="                           { return new Symbol(sym.LTEQ); }
  ">="                           { return new Symbol(sym.GTEQ); }
  "!="                           { return new Symbol(sym.NOTEQ); }
  "&&"                           { return new Symbol(sym.ANDAND); }
  "||"                           { return new Symbol(sym.OROR); }
  "+"                            { return new Symbol(sym.PLUS); }
  "-"                            { return new Symbol(sym.MINUS); }
  "*"                            { return new Symbol(sym.MULT); }
  "/"                            { return new Symbol(sym.DIV); }
  "%"                            { return new Symbol(sym.MOD); }
  "+="                           { return new Symbol(sym.PLUSEQ); }
  "-="                           { return new Symbol(sym.MINUSEQ); }

  "externinvk"                   { return new Symbol(sym.EXTERNINVK,yyline+1,yycolumn+1,yytext());}

  {DecIntegerLiteral}            { return new Symbol(sym.INT_LITERAL, new Integer(yytext())); }
  
  {FloatLiteral}                 { return new Symbol(sym.FLOAT_LITERAL, new Float(yytext().substring(0,yylength()-1))); }
  
  /* comments */
  {Comment}                      { System.out.println("COMMENT"); System.out.println(yytext()); }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */ 
  {Identifier}                   { return new Symbol(sym.ID, yytext()); }  
  .                              { System.out.println("NO RECONOCIDO");}
}

<STRING> {
  \" {yybegin(YYINITIAL);return new Symbol(sym.STRING_LITERAL,yyline+1,yycolumn+1,yytext());}
  . {}
}
