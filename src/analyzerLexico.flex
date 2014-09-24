/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: analisisLexico.flex
 * To Create: jflex analisisLexico.flex
 * Project: Compilador
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

%state STRING

%%
   
<YYINITIAL> {

  \"                            { System.out.println(yytext()); yybegin(STRING); string.setLength(0); }

   "if"   			 	              { System.out.println(yytext());return new Symbol(sym.IF); }
   "int"   			 	              { System.out.println(yytext());return symbol(sym.TINT); }
   "for"   			 	              { System.out.println(yytext());return symbol(sym.FOR); }
   "else"   			              { System.out.println(yytext());return symbol(sym.ELSE); }
   "void"   			              { System.out.println(yytext());return symbol(sym.TVOID); }
   "break"   			              { System.out.println(yytext());return symbol(sym.BREAK); }
   "class"   			              { System.out.println(yytext());return symbol(sym.CLASS); }
   "float"   			              { System.out.println(yytext());return symbol(sym.TFLOAT); }
   "while"   			              { System.out.println(yytext());return symbol(sym.WHILE); }
   "return"   		 	            { System.out.println(yytext());return symbol(sym.RETURN); }
   "boolean"   		 	            { System.out.println(yytext());return symbol(sym.TBOOLEAN); }
   "continue"   		            { System.out.println(yytext());return symbol(sym.CONTINUE); }
   "false" | "true"             { System.out.println(yytext()); return symbol(sym.BOOL_LITERAL); }
  
  "("                           { System.out.println(yytext());return symbol(sym.LPAREN); }
  ")"                           { System.out.println(yytext());return symbol(sym.RPAREN); }
  "{"                           { System.out.println(yytext());return symbol(sym.LBRACE); }
  "}"                           { System.out.println(yytext());return symbol(sym.RBRACE); }
  "["                           { System.out.println(yytext());return symbol(sym.LBRACK); }
  "]"                           { System.out.println(yytext());return symbol(sym.RBRACK); }
  ";"                           { System.out.println(yytext());return symbol(sym.SEMICOLON); }
  ","                           { System.out.println(yytext());return symbol(sym.COMMA); }
  
  "="                           { System.out.println(yytext());return symbol(sym.EQ); }
  ">"                           { System.out.println(yytext());return symbol(sym.GT); }
  "<"                           { System.out.println(yytext());return symbol(sym.LT); }
  "!"                           { System.out.println(yytext());return symbol(sym.NOT); }
  "=="                          { System.out.println(yytext());return symbol(sym.EQEQ); }
  "<="                          { System.out.println(yytext());return symbol(sym.LTEQ); }
  ">="                          { System.out.println(yytext());return symbol(sym.GTEQ); }
  "!="                          { System.out.println(yytext());return symbol(sym.NOTEQ); }
  "&&"                          { System.out.println(yytext());return symbol(sym.ANDAND); }
  "||"                          { System.out.println(yytext());return symbol(sym.OROR); }
  "+"                           { System.out.println(yytext());return symbol(sym.PLUS); }
  "-"                           { System.out.println(yytext());return symbol(sym.MINUS); }
  "*"                           { System.out.println(yytext());return symbol(sym.MULT); }
  "/"                           { System.out.println(yytext());return symbol(sym.DIV); }
  "%"                           { System.out.println(yytext());return symbol(sym.MOD); }
  "+="                          { System.out.println(yytext());return symbol(sym.PLUSEQ); }
  "-="                          { System.out.println(yytext());return symbol(sym.MINUSEQ); }

  "externinvk"                  {System.out.println(yytext());return new Symbol(sym.EXTERNINVK,yyline+1,yycolumn+1,yytext());}

  {DecIntegerLiteral}           { System.out.println(yytext());return symbol(sym.INT_LITERAL, yytext()); }
  
  {FloatLiteral}                { System.out.println(yytext());return symbol(sym.FLOAT_LITERAL, yytext()); }
 
  {Comment}                     { System.out.println("COMMENT ");System.out.println(yytext()); }

  {WhiteSpace}                  { /* ignore */ }

  {Identifier}                  { System.out.println(yytext());return symbol(sym.ID, yytext()); }  

  .                             { System.out.println("NO RECONOCIDO");}
}

<STRING> {
  \"                            {yybegin(YYINITIAL);return new Symbol(sym.STRING_LITERAL,yyline+1,yycolumn+1,yytext());}
  .                             {}
}