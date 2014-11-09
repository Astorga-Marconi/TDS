/**
 * Copyright (C) 2014
 * All rights reserved.
 *
 * File Name: CodeGenVisitor.java
 * Project: Compilador
 * Authors: Astorga Dario - Marconi Pablo
 * Version: 0.1 
 * Description the class: Generador de codigo de tres direcciones
 */
package ir.CodeGen;

import java.util.LinkedList;
import java.util.List;
import ir.ASTVisitor;
import ir.ast.*;
import error.Error;
import semanticAnalyzer.*;


public class CodeGenVisitor implements ASTVisitor<Expression> {
	
	
	private List<Error> errors;
	private List<InstrCode> instrList ;
	private Integer labelsIdGen;
	private List<Expression> jmpLabels;	// Lista que guarda los labels de los posibles saltos a realizarse
										// al encontrar un BREAK o CONTINUE. Cada vez que se encuentra 
										// con un ciclo se guarda el label inicio y fin de ciclo.
	private int methodInstrIndex;		// Indice que especifica el principio de un metodo, es usado
										// para definir los String usados en las invocaciones externas.

	private List<InstrCode> floatDecl;	// Lista usada para almacenar las declaraciones de los flotantes
										// que luego seran escritas en labels al final del assembler del metodo.

	public CodeGenVisitor() {
		labelsIdGen = 0;
		errors = new LinkedList<Error>();
		instrList = new LinkedList<InstrCode>();
		jmpLabels = new LinkedList<Expression>();
		floatDecl = new LinkedList<InstrCode>();
	}

	public List<InstrCode> getInstrList() {
		return instrList;
	}

	public void instrMethodLabel(FunctionDescriptor f) {
		methodInstrIndex = instrList.size();	// Seteo el indice en la lista que especifica el comienzo de un metodo.
		instrList.add(new InstrCode(Operator.METHODLABEL, null, null, (new IntLiteral(f.getName()) )));
	}

	public void instrMethodEnd() {
		instrList.add(new InstrCode(Operator.METHODEND, null, null, null));
		instrList.addAll(floatDecl);	// Agrego los labels de los respectivos floats.
		floatDecl = new LinkedList<InstrCode>();	// Limpio la lista con los float's ya agregados.
	}

	public void initVar(Location loc) {
		if (loc instanceof VarLocation) {
			//System.out.println("se incializa una variable local");
			if (loc.getType() == Type.TINT || loc.getType() == Type.TBOOLEAN) {
				instrList.add(new InstrCode(Operator.EQ, (new IntLiteral("0")), null, loc));
			} else if(loc.getType() == Type.TFLOAT) {
				Expression labelFloat = new IntLiteral("LC" + Integer.toString(labelsIdGen++)); // Usado como nombre no como entero
				instrList.add(new InstrCode(Operator.INITFLOATLOCATION, labelFloat, loc, null));
				floatDecl.add(new InstrCode(Operator.INITLOCALFLOAT, labelFloat, loc, (new FloatLiteral("0.0"))));
			}
		} else if (loc instanceof GlobalVarLocation) {
			//System.out.println("se incializa una variable global");
			instrList.add(new InstrCode(Operator.INITGLOBALVAR, loc, null, null));
		}
	}

	public void initArray(Location loc) {
		if (loc instanceof ArrayLocation) {
			//System.out.println("se incializa un arreglo local");
		} else if (loc instanceof GlobalArrayLocation) {
			//System.out.println("se incializa un arreglo global");
			instrList.add(new InstrCode(Operator.INITGLOBALARRAY, loc, null, null));
		}
	}

	//			visit statements
	
	public Expression visit(AssignStmt stmt) {
		Expression expr = stmt.getExpression().accept(this);
		Expression loc = stmt.getLocation();
		Expression resExpr;
		Expression arrayExpr = null;
		if (loc instanceof ArrayLocation) {
			// Si el location es ArrayLocation la asignacion EQ tendra un operando mas, sino será null.
			arrayExpr =  ((ArrayLocation)loc).getExpression().accept(this);
		}
		if (loc instanceof GlobalArrayLocation) {
			// Si el location es GlobalArrayLocation la asignacion EQ tendra un operando mas, sino será null.
			arrayExpr =  ((GlobalArrayLocation)loc).getExpression().accept(this);
		}
		switch (stmt.getOperator()) {
    		case EQ:
    			instrList.add(new InstrCode(Operator.EQ, expr, arrayExpr, loc));
    			break;
    		case PLUSEQ:
    			resExpr = new VarLocation("assignRes" + Integer.toString(labelsIdGen++));
	    		instrList.add(new InstrCode(Operator.PLUS, loc.accept(this), expr, resExpr));
	    		instrList.add(new InstrCode(Operator.EQ, resExpr, arrayExpr, loc));
    			break;
    		case MINUSEQ:
	    		resExpr = new VarLocation("assignRes" + Integer.toString(labelsIdGen++));
	    		instrList.add(new InstrCode(Operator.MINUS, loc.accept(this), expr, resExpr));
	    		instrList.add(new InstrCode(Operator.EQ, resExpr, arrayExpr, loc));
    			break;
    	}
		return null;
	}
	
  	public Expression visit(ReturnStmt stmt) {
  		Expression retExpr = null;
  		if (stmt.getExpression() != null) {
  			retExpr = stmt.getExpression().accept(this);
  		}
  		instrList.add(new InstrCode(Operator.RET, null, null, retExpr));
  		return null;
  	}

	public Expression visit(IfStmt stmt)  {
		Expression ifCond = stmt.getCondition().accept(this);
		Expression endIfLabel = new IntLiteral("endIfLabel" + Integer.toString(labelsIdGen++));
		instrList.add(new InstrCode(Operator.CMP, ifCond, (new BoolLiteral("true")), null));
		if (stmt.getElseBlock() == null) {	// Si no tengo un else
			instrList.add(new InstrCode(Operator.JNE, null, null, endIfLabel));
			// Genero las instrucciones del bloque if
			Expression ifInstrs = stmt.getIfBlock().accept(this);
			instrList.add(new InstrCode(Operator.LABEL, null, null, endIfLabel));
		} else {	// Si tengo un else
			Expression elseLabel = new IntLiteral("elseLabel" + Integer.toString(labelsIdGen++));
			instrList.add(new InstrCode(Operator.JNE, null, null, elseLabel));
			// Genero las instrucciones del bloque if
			Expression ifInstrs = stmt.getIfBlock().accept(this);
			instrList.add(new InstrCode(Operator.JMP, null, null, endIfLabel));
			instrList.add(new InstrCode(Operator.LABEL, null, null, elseLabel));
			// Genero las instrucciones del bloque else.
			Expression elseInstrs = stmt.getElseBlock().accept(this);
			instrList.add(new InstrCode(Operator.LABEL, null, null, endIfLabel));
		}
  		return null;
  	}
	
	public Expression visit(ForStmt stmt) {
		Expression initialValExpr = stmt.getInitialValue().accept(this);
		Expression forCondExpr = stmt.getCondition().accept(this);
		VarLocation loc = stmt.getIdLocation();
		Expression forLabel = new IntLiteral("forLabel" + Integer.toString(labelsIdGen++));
		Expression endForLabel = new IntLiteral("endForLabel" + Integer.toString(labelsIdGen++));
		jmpLabels.add(forLabel);	// Agrego los labels para el uso de break's y continue's.
		jmpLabels.add(endForLabel);
		instrList.add(new InstrCode(Operator.EQ, initialValExpr, null, loc));
		instrList.add(new InstrCode(Operator.LABEL, null, null, forLabel));
		instrList.add(new InstrCode(Operator.CMP, loc, forCondExpr, null));
		instrList.add(new InstrCode(Operator.JGE, null, null, endForLabel));
		// Genero las instrucciones del bloque for.
		Expression forBlockInstrs = stmt.getForBlock().accept(this);
		instrList.add(new InstrCode(Operator.INC, loc, null, null));
		instrList.add(new InstrCode(Operator.JMP, null, null, forLabel));
		instrList.add(new InstrCode(Operator.LABEL, null, null, endForLabel));
		jmpLabels.remove(jmpLabels.size()-2);	// Quito los labels, una vez fuera del ciclo son innecesarios.
		jmpLabels.remove(jmpLabels.size()-1);
  		return null;
  	}
	
	public Expression visit(BreakStmt stmt) {
		instrList.add(new InstrCode(Operator.JMP, null, null, jmpLabels.get(jmpLabels.size()-1)));
		return null;
	}

	public Expression visit(ContinueStmt stmt) {
  		instrList.add(new InstrCode(Operator.JMP, null, null, jmpLabels.get(jmpLabels.size()-2)));
  		return null;
	} 

	public Expression visit(WhileStmt stmt) {
		Expression beginWhilelabel = new VarLocation("beginWhileLabel" + Integer.toString(labelsIdGen++));
		Expression endWhilelabel = new VarLocation("endWhileLabel" + Integer.toString(labelsIdGen++));
    	jmpLabels.add(beginWhilelabel);	// Agrego los labels que pertenecen al principio y al fin del ciclo respectivamente.
    	jmpLabels.add(endWhilelabel);
		instrList.add(new InstrCode(Operator.LABEL, null, null, beginWhilelabel));
    	Expression condition = stmt.getCondition().accept(this);	 
    	instrList.add(new InstrCode(Operator.CMP, condition, (new BoolLiteral("true")), null));
		instrList.add(new InstrCode(Operator.JNE, null, null, endWhilelabel));
		Expression block = stmt.getBlock().accept(this);
  	    instrList.add(new InstrCode(Operator.JMP, null, null, beginWhilelabel));	
		instrList.add(new InstrCode(Operator.LABEL, null, null, endWhilelabel));
   		jmpLabels.remove(jmpLabels.size()-1);	// Remuevo los labels que pertenecen al principio y al fin del ciclo respectivamente.
    	jmpLabels.remove(jmpLabels.size()-1);
		return null;
	}
	
	public Expression visit(LoopStmt stmt) {
		return null;
	}
	
	public Expression visit(Block stmt) {
		for (Statement s: stmt.getStatements()) {
			s.accept(this);
		}
		return null;
	}
	
	public Expression visit(MethodCallStmt stmt) {
		List lParams = stmt.getParameters();
		for (int i = lParams.size()-1; i >= 0 ; i--) {
			Expression parameter = ((Expression)lParams.get(i)).accept(this);
			instrList.add(new InstrCode(Operator.PUSH, parameter, null, null));
		}
		Expression nameMethod = new IntLiteral(stmt.getId());
		instrList.add(new InstrCode(Operator.CALL, nameMethod, null, null));
		if (stmt.getParameters().size() > 0) {
			Expression  numParams = new IntLiteral(""+stmt.getParameters().size());
			// DELPARAMS saca los parametros metidos en la pila con anterioridad.
			instrList.add(new InstrCode(Operator.DELPARAMS, numParams, null, null));
		}
		return null;
	}

	public Expression visit(ExternInvkStmt stmt) {
		List lParams = stmt.getParameters();
		for (int i = lParams.size()-1; i >= 0 ; i--) {
			Expression parameter = ((Expression)lParams.get(i)).accept(this);
			instrList.add(new InstrCode(Operator.PUSH, parameter, null, null));
		}
		Expression nameMethod = new IntLiteral(stmt.getId());
		instrList.add(new InstrCode(Operator.CALL, nameMethod, null, null));
		if (stmt.getParameters().size() > 0) {
			Expression  numParams = new IntLiteral(""+stmt.getParameters().size());
			// DELPARAMS saca los parametros metidos en la pila con anterioridad.
			instrList.add(new InstrCode(Operator.DELPARAMS, numParams, null, null));
		}
		return null;
	}

	//			visit Expressions

	public Expression visit(BinOpExpr expr) {
		return null;
	}
	
	public Expression visit(MethodCallExpr expr) {
		List lParams = expr.getParameters();
		for (int i = lParams.size()-1; i >= 0 ; i--) {
			Expression parameter = ((Expression)lParams.get(i)).accept(this);
			instrList.add(new InstrCode(Operator.PUSH, parameter, null, null));
		}
		Expression nameMethod = new IntLiteral(expr.getId());
		VarLocation res = new VarLocation("methodReturn" + Integer.toString(labelsIdGen++));
    	instrList.add(new InstrCode(Operator.CALL, nameMethod, null, null));	
		if (expr.getParameters().size() > 0) {
			Expression  numParams = new IntLiteral(""+expr.getParameters().size());
			// DELPARAMS saca los parametros metidos en la pila con anterioridad.
			instrList.add(new InstrCode(Operator.DELPARAMS, numParams, null, null));
		}
		instrList.add(new InstrCode(Operator.METHODRET, null, null, res));
		return res;
	}

	public Expression visit(ExternInvkExpr expr) {
		List lParams = expr.getParameters();
		for (int i = lParams.size()-1; i >= 0 ; i--) {
			Expression parameter = ((Expression)lParams.get(i)).accept(this);
			instrList.add(new InstrCode(Operator.PUSH, parameter, null, null));
		}
		Expression nameMethod = new IntLiteral(expr.getId());
		instrList.add(new InstrCode(Operator.CALL, nameMethod, null, null));
		if (expr.getParameters().size() > 0) {
			Expression  numParams = new IntLiteral(""+expr.getParameters().size());
			// DELPARAMS saca los parametros metidos en la pila con anterioridad.
			instrList.add(new InstrCode(Operator.DELPARAMS, numParams, null, null));
		}
		VarLocation res = new VarLocation("externInvkReturn" + Integer.toString(labelsIdGen++));
		instrList.add(new InstrCode(Operator.METHODRET, null, null, res));
		return res;
	}
	
	public Expression visit(ExternInvkArgExpr expr) {
		return null;
	}
	
	public Expression visit(ExternInvkArgStringLit expr) {
		Expression  stringLabel = new IntLiteral(".LC" + Integer.toString(labelsIdGen++));
		Expression  stringLit = new IntLiteral(expr.getString());
		instrList.add(methodInstrIndex, new InstrCode(Operator.STRING, stringLabel, stringLit, null));
		return stringLabel;
	}

	public Expression visit(NegativeExpr expr) {
    	Expression oper = expr.getExpression().accept(this);
  		VarLocation res = new VarLocation("negRes" + Integer.toString(labelsIdGen++));
  		res.setType(oper.getType());
		instrList.add(new InstrCode(Operator.NEG, oper, null, res));
    	return res;
	}
	
	public Expression visit (NotExpr expr)   {
    	Expression boolExpr = expr.getExpression().accept(this);
  		Expression res = new VarLocation("notRes" + Integer.toString(labelsIdGen++));
		instrList.add(new InstrCode(Operator.NOT, boolExpr, null, res));
    	return res;
 	}

  	public Expression visit (ParentExpr expr) {
  		return expr.getExpression().accept(this);
  	}
	
  	public Expression visit (ArithExpr expr) {
  		Expression leftOperand = expr.getLeftOperand().accept(this);
  		if (leftOperand instanceof IntLiteral) {	// Devuelve el literal, no la posicion en donde se guarda
  			Expression resL = new VarLocation("int" + Integer.toString(labelsIdGen++));
  			resL.setType(Type.TINT);
			instrList.add(new InstrCode(Operator.EQ, leftOperand, null, resL));
			leftOperand = resL;
  		}
    	Expression rightOperand = expr.getRightOperand().accept(this);
    	if (rightOperand instanceof IntLiteral) {
  			Expression resR = new VarLocation("int" + Integer.toString(labelsIdGen++));
  			resR.setType(Type.TINT);
			instrList.add(new InstrCode(Operator.EQ, rightOperand, null, resR));
			rightOperand = resR;
  		}
    	Operator operator = null;
    	switch (expr.getOperator()) {
    		case PLUS:
    			operator = Operator.PLUS;
    			break;
    		case MINUS:
    			operator = Operator.MINUS;
    			break;
    		case MULT:
    			operator = Operator.MULT;
    			break;
    		case DIV:
    			operator = Operator.DIV;
    			break;
    		case MOD:
    			operator = Operator.MOD;
    			break;
    	}
    	VarLocation res = new VarLocation("arithRes" + Integer.toString(labelsIdGen++));
    	res.setType(rightOperand.getType());	// Defino el tipo de resultado a partir de los operandos
    											// a partir de este sera el codigo assembler generado.
    	instrList.add(new InstrCode(operator, leftOperand, rightOperand, res));
    	return res;
 	}

  	public Expression visit (RelExpr expr) {
  		Expression leftOperand = expr.getLeftOperand().accept(this);
    	Expression rightOperand = expr.getRightOperand().accept(this);
    	VarLocation res = new VarLocation("relRes" + Integer.toString(labelsIdGen++));
    	Operator operator = null;
    	switch (expr.getOperator()) {
    		case GT:
    			operator = Operator.GT;
    			break;
    		case LT:
    			operator = Operator.LT;
    			break;
    		case LTEQ:
    			operator = Operator.LTEQ;
    			break;
    		case GTEQ:
    			operator = Operator.GTEQ;
    			break;
    	}
    	instrList.add(new InstrCode(operator, leftOperand, rightOperand, res));
    	return res;
  	}

  	public Expression visit (CondExpr expr)   {
  		Expression leftOperand = expr.getLeftOperand().accept(this);
    	Expression rightOperand = expr.getRightOperand().accept(this);
    	VarLocation res = new VarLocation("condRes" + Integer.toString(labelsIdGen++));  
    	Operator operator = null;
    	switch (expr.getOperator()) {
    		case ANDAND:
    			operator = Operator.ANDAND;
    			break;
    		case OROR:
    			operator = Operator.OROR;
    			break;
    	}
    	instrList.add(new InstrCode(operator, leftOperand, rightOperand, res));
    	return res;
  	}

  	public Expression visit (EqExpr expr)   {
  		Expression leftOperand = expr.getLeftOperand().accept(this);
    	Expression rightOperand = expr.getRightOperand().accept(this);
    	VarLocation res = new VarLocation("eqRes" + Integer.toString(labelsIdGen++));  
    	Operator operator = null;
  		switch (expr.getOperator()) {
    		case EQEQ:
    			operator = Operator.EQEQ;
    			break;
    		case NOTEQ:
    			operator = Operator.NOTEQ;
    			break;
    	}
    	instrList.add(new InstrCode(operator, leftOperand, rightOperand, res));
    	return res;
  	}
  	
	//			visit literals
	
	public Expression visit(IntLiteral lit) {
    	return lit;
	}
	
	public Expression visit(FloatLiteral lit) {
		Expression res = new VarLocation("float" + Integer.toString(labelsIdGen++));
		res.setType(Type.TFLOAT);
		Expression labelFloat = new IntLiteral("LC" + Integer.toString(labelsIdGen++)); // Usado como nombre no como entero
		instrList.add(new InstrCode(Operator.INITFLOATLOCATION, labelFloat, res, null));
		floatDecl.add(new InstrCode(Operator.INITLOCALFLOAT, labelFloat, res, lit));
    	return res;
	}

	public Expression visit(BoolLiteral lit) {
		Expression res = new VarLocation("boolean" + Integer.toString(labelsIdGen++));
		res.setType(Type.TBOOLEAN);
		instrList.add(new InstrCode(Operator.EQ, lit, null, res));
    	return res;
	}

	//			visit locations	
	
	public Expression visit(VarLocation loc) {
		return loc;	
	}

	public Expression visit(GlobalVarLocation loc) {
		Expression res = new VarLocation("globalVar" + Integer.toString(labelsIdGen++));
		res.setType(loc.getType());
		instrList.add(new InstrCode(Operator.EQ, loc, null, res));
		return res;	
	}

	public Expression visit(ArrayLocation loc) {
		// Este metodo solo es llamado cuando el ArrayLocation es usado como Expression.
		Expression expr = loc.getExpression().accept(this);
		Expression res = new VarLocation("arrayValue" + Integer.toString(labelsIdGen++));
		res.setType(loc.getType());
		instrList.add(new InstrCode(Operator.ARRAYVALUE, loc, expr, res));
    	return res;
	}

	public Expression visit(GlobalArrayLocation loc) {
		// Este metodo solo es llamado cuando el ArrayLocation es usado como Expression.
		Expression expr = loc.getExpression().accept(this);
		Expression res = new VarLocation("arrayValue" + Integer.toString(labelsIdGen++));
		res.setType(loc.getType());
		instrList.add(new InstrCode(Operator.ARRAYVALUE, loc, expr, res));
    	return res;
	}

	public Expression visit(SemicolonStmt stmt) {
		return null;
	}

	public void printInstrs() {
		System.out.println("SE CREARON " + instrList.size() + " INSTRUCCIONES: ");
		for (InstrCode i : instrList) {
			System.out.println(i.toString());
		}
	}

	private void addError(AST a, String desc) {
		errors.add(new Error(0, 0, desc));
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
}
