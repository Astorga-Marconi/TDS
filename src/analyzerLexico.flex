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
%state STRING
%state ONE_COMMENT
%state MULTI_COMMENT

%eofval{
  return new Symbol(sym.EOF);
%eofval}

%unicode
%line
%class Scanner
%public
%cup
  
%%
<YYINITIAL>{

  "//"                  {yybegin(ONE_COMMENT);}
  "/*"                  {yybegin(MULTI_COMMENT);}

  \"                    {yybegin(STRING);}

  "if"                  {return new Symbol(sym.IF,yyline+1,yycolumn+1,yytext());}
  "else"                {return new Symbol(sym.ELSE,yyline+1,yycolumn+1,yytext());}

  [ \t\r\n\f]           {}

  "int"                 {return new Symbol(sym.TINT,yyline+1,yycolumn+1,yytext());} 
  "for"                 {return new Symbol(sym.FOR,yyline+1,yycolumn+1,yytext());}  
  "true"                {return new Symbol(sym.BOOL_LITERAL,yyline+1,yycolumn+1,yytext());}
  "void"                {return new Symbol(sym.VOID,yyline+1,yycolumn+1,yytext());}
  "break"               {return new Symbol(sym.BREAK,yyline+1,yycolumn+1,yytext());}
  "float"               {return new Symbol(sym.TFLOAT,yyline+1,yycolumn+1,yytext());}
  "class"               {return new Symbol(sym.CLASS,yyline+1,yycolumn+1,yytext());}
  "while"               {return new Symbol(sym.WHILE,yyline+1,yycolumn+1,yytext());}
  "false"               {return new Symbol(sym.BOOL_LITERAL,yyline+1,yycolumn+1,yytext());}
  "return"              {return new Symbol(sym.RETURN,yyline+1,yycolumn+1,yytext());}
  "boolean"             {return new Symbol(sym.TBOOLEAN,yyline+1,yycolumn+1,yytext());}
  "continue"            {return new Symbol(sym.CONTINUE,yyline+1,yycolumn+1,yytext());}
  "+"                   {return new Symbol(sym.PLUS,yyline+1,yycolumn+1,yytext());}
  "&&"                  {return new Symbol(sym.ANDAND,yyline+1,yycolumn+1,yytext());}
  "||"                  {return new Symbol(sym.OROR,yyline+1,yycolumn+1,yytext());}
  "-"                   {return new Symbol(sym.MINUS,yyline+1,yycolumn+1,yytext());}
  "*"                   {return new Symbol(sym.MULT,yyline+1,yycolumn+1,yytext());}
  "/"                   {return new Symbol(sym.DIV,yyline+1,yycolumn+1,yytext());}
  "%"                   {return new Symbol(sym.MOD,yyline+1,yycolumn+1,yytext());}
  ">="                  {return new Symbol(sym.GTEQ,yyline+1,yycolumn+1,yytext());}
  "<="                  {return new Symbol(sym.LTEQ,yyline+1,yycolumn+1,yytext());}
  ">"                   {return new Symbol(sym.GT,yyline+1,yycolumn+1,yytext());}
  "<"                   {return new Symbol(sym.LT,yyline+1,yycolumn+1,yytext());}
  "=="                  {return new Symbol(sym.EQEQ,yyline+1,yycolumn+1,yytext());}
  "!="                  {return new Symbol(sym.NOTEQ,yyline+1,yycolumn+1,yytext());}
  "!"                   {return new Symbol(sym.NOT,yyline+1,yycolumn+1,yytext());}
  "="                   {return new Symbol(sym.EQ,yyline+1,yycolumn+1,yytext());}
  "+="                  {return new Symbol(sym.PLUSEQ,yyline+1,yycolumn+1,yytext());}
  "-="                  {return new Symbol(sym.MINUSEQ,yyline+1,yycolumn+1,yytext());}
  "("                   {return new Symbol(sym.LPAREN,yyline+1,yycolumn+1,yytext());}
  ")"                   {return new Symbol(sym.RPAREN,yyline+1,yycolumn+1,yytext());}
  "{"                   {return new Symbol(sym.LBRACE,yyline+1,yycolumn+1,yytext());}
  "}"                   {return new Symbol(sym.RBRACE,yyline+1,yycolumn+1,yytext());}
  "["                   {return new Symbol(sym.LBRACK,yyline+1,yycolumn+1,yytext());}
  "]"                   {return new Symbol(sym.RBRACK,yyline+1,yycolumn+1,yytext());}
  ";"                   {return new Symbol(sym.SEMICOLON,yyline+1,yycolumn+1,yytext());}
  ","                   {return new Symbol(sym.COMMA,yyline+1,yycolumn+1,yytext());}

  "externinvk"          {return new Symbol(sym.EXTERNINVK,yyline+1,yycolumn+1,yytext());}
  [a-zA-Z][a-zA-Z0-9_]* {return new Symbol(sym.ID,yyline+1,yycolumn+1,yytext());}
  [0-9][0-9]*           {return new Symbol(sym.INT_LITERAL,yyline+1,yycolumn+1,yytext());}
  [0-9]+"."[0-9]+       {return new Symbol(sym.FLOAT_LITERAL,yyline+1,yycolumn+1,yytext());}
  .                     {System.out.println("NO RECONOCIDO");}
}
<STRING> {
  \"                    {yybegin(YYINITIAL);return new Symbol(sym.STRING_LITERAL,yyline+1,yycolumn+1,yytext());}
  .                     {}
}
<ONE_COMMENT> {
  [\n]                  {yybegin(YYINITIAL);}
  .                     {}
}
<MULTI_COMMENT> {
  "*/"                  {yybegin(YYINITIAL);}
  [ \t\r\n\f]           {}
  .                     {}
}