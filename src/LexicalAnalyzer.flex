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

%%
   
<YYINITIAL> {

   "if"   			 	              { return new Symbol(sym.IF); }
   "int"   			 	              { return symbol(sym.TINT); }
   "for"   			 	              { return symbol(sym.FOR); }
   "else"   			              { return symbol(sym.ELSE); }
   "void"   			              { return symbol(sym.TVOID); }
   "break"   			              { return symbol(sym.BREAK); }
   "class"   			              { return symbol(sym.CLASS); }
   "float"   			              { return symbol(sym.TFLOAT); }
   "while"   			              { return symbol(sym.WHILE); }
   "return"   		 	            { return symbol(sym.RETURN); }
   "boolean"   		 	            { return symbol(sym.TBOOLEAN); }
   "continue"   		            { return symbol(sym.CONTINUE); }
   "false" | "true"             { return symbol(sym.BOOL_LITERAL, yytext()); }
  
  "("                           { return symbol(sym.LPAREN); }
  ")"                           { return symbol(sym.RPAREN); }
  "{"                           { return symbol(sym.LBRACE); }
  "}"                           { return symbol(sym.RBRACE); }
  "["                           { return symbol(sym.LBRACK); }
  "]"                           { return symbol(sym.RBRACK); }
  ";"                           { return symbol(sym.SEMICOLON); }
  ","                           { return symbol(sym.COMMA); }
  
  "="                           { return symbol(sym.EQ); }
  ">"                           { return symbol(sym.GT); }
  "<"                           { return symbol(sym.LT); }
  "!"                           { return symbol(sym.NOT); }
  "=="                          { return symbol(sym.EQEQ); }
  "<="                          { return symbol(sym.LTEQ); }
  ">="                          { return symbol(sym.GTEQ); }
  "!="                          { return symbol(sym.NOTEQ); }
  "&&"                          { return symbol(sym.ANDAND); }
  "||"                          { return symbol(sym.OROR); }
  "+"                           { return symbol(sym.PLUS); }
  "-"                           { return symbol(sym.MINUS); }
  "*"                           { return symbol(sym.MULT); }
  "/"                           { return symbol(sym.DIV); }
  "%"                           { return symbol(sym.MOD); }
  "+="                          { return symbol(sym.PLUSEQ); }
  "-="                          { return symbol(sym.MINUSEQ); }

  "externinvk"                  { return new Symbol(sym.EXTERNINVK,yyline+1,yycolumn+1,yytext());}

  {DecIntegerLiteral}           { return symbol(sym.INT_LITERAL, yytext()); }
  
  {FloatLiteral}                { return symbol(sym.FLOAT_LITERAL, yytext()); }
 
  {Comment}                     { /*System.out.println("COMMENT ");System.out.println(yytext());*/ }

  {WhiteSpace}                  { /* ignore */ }

  {Identifier}                  { return symbol(sym.ID, yytext()); }  

  \"[^\"]*\"                    { return new Symbol(sym.STRING_LITERAL, yyline, yycolumn, yytext().substring(1,yytext().length()-1));}

  .                             { System.out.println("NO RECONOCIDO");}
}