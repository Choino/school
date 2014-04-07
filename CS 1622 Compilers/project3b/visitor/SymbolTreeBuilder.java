package visitor;

import syntaxtree.*;
import visitor.*;
import java.util.HashMap;

public class SymbolTreeBuilder implements SymbolVisitor {

  // pls

  SymbolTable table = new SymbolTable();
  SymbolClass currentClass;
  SymbolMethod currentMethod;
  HashMap<String, Type> typeChecker = new HashMap<String, Type>();
  HashMap<String, SymbolMethod> methodMap = new HashMap<String, SymbolMethod>();

  boolean canUseThis = false;

  public boolean redefinition(String r) {
    return table.definedInCurrentScope(r);
  }

  // MainClass m;
  // ClassDeclList cl;
  public void visit(Program n) {
    table.pushScope();
    n.m.accept(this);
    for ( int i = 0; i < n.cl.size(); i++ ) {
        n.cl.elementAt(i).accept(this);
    }
  }
  
  // Identifier i1,i2;
  // Statement s;
  public void visit(MainClass n) {

    canUseThis = false;
    currentClass = new SymbolClass(n.i1.s);

    table.pushScope();
    table.addSymbol(n.i1.s, n.i1);
    n.i1.accept(this);

    table.addSymbol(n.i2.s, n.i2);
    n.i2.accept(this);
    
    n.s.accept(this);

    table.destroyScope();
    canUseThis = true;
  }

  // Identifier i;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclSimple n) {

    currentClass = new SymbolClass(n.i.s);

    table.pushScope();
    table.addSymbol(n.i.s, n.i);
    n.i.accept(this);
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept(this);
        SymbolVariable tempVar = new SymbolVariable(n.vl.elementAt(i).i.s, n.vl.elementAt(i).t);
        typeChecker.put(tempVar.name, tempVar.type);

        tempVar.defined = false;
        currentClass.addVariable(tempVar);
    }

    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.elementAt(i).accept(this);

    /*    MethodDecl tempMethod = n.ml.elementAt(i);
        SymbolMethod tempSym = new SymbolMethod(tempMethod.i.s, tempMethod.t);
        tempSym.addParent(currentClass);

        currentMethod = tempSym;
        for ( int j = 0; j < tempMethod.vl.size(); j++ ) {
          tempMethod.vl.elementAt(j).accept(this);
          SymbolVariable tempVar = new SymbolVariable(tempMethod.vl.elementAt(j).i.s, tempMethod.vl.elementAt(j).t);
          typeChecker.put(tempVar.name, tempVar.type);


          tempVar.defined = true;
          tempSym.addParameter(tempVar);
          methodMap.put(tempSym.name, tempSym);
        }
        currentClass.addMethod(tempSym);*/
    }
    table.destroyScope();
  }
 
  // Identifier i;
  // Identifier j;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclExtends n) {
    currentClass = new SymbolClass(n.i.s);

    table.pushScope();
    table.addSymbol(n.i.s, n.i);
    n.i.accept(this);

    table.addSymbol(n.j.s, n.j);
    n.j.accept(this);
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.elementAt(i).accept(this);
        SymbolVariable tempVar = new SymbolVariable(n.vl.elementAt(i).i.s, n.vl.elementAt(i).t);
               typeChecker.put(tempVar.name, tempVar.type);

        tempVar.defined = false;
        currentClass.addVariable(tempVar);
    }
    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.elementAt(i).accept(this);
        MethodDecl tempMethod = n.ml.elementAt(i);
        SymbolMethod tempSym = new SymbolMethod(tempMethod.i.s, tempMethod.t);
        tempSym.addParent(currentClass);

        currentMethod = tempSym;

        for ( int j = 0; j < tempMethod.vl.size(); j++ ) {
          tempMethod.vl.elementAt(j).accept(this);
          SymbolVariable tempVar = new SymbolVariable(tempMethod.vl.elementAt(j).i.s, tempMethod.vl.elementAt(j).t);
                  typeChecker.put(tempVar.name, tempVar.type);

          tempVar.defined = true;
          tempSym.addParameter(tempVar);

          methodMap.put(tempSym.name, tempSym);
        }
        currentClass.addMethod(tempSym);
    }
    table.destroyScope();
  }

  // Type t;
  // Identifier i;
  public void visit(VarDecl n) {

    if (redefinition(n.i.s) && currentClass != null && currentClass.hasParameter(new SymbolVariable(n.i.s, n.t))) {
    }    

    if (redefinition(n.i.s) && currentMethod != null && currentMethod.hasParameter(new SymbolVariable(n.i.s, n.t))) {
    }

    if (n.t instanceof IdentifierType) {
      IdentifierType steve = (IdentifierType)n.t;
      table.addSymbol(steve.s, steve);
    }

    if (redefinition(n.i.s)) {
    }
   

    SymbolVariable tempVar = new SymbolVariable(n.i.s, n.t);
    typeChecker.put(n.i.s, n.t);

    if (currentClass != null && currentClass.hasParameter(new SymbolVariable(n.i.s, n.t))) {
      currentClass.defineParameter(tempVar);
    }
    if (currentMethod != null && currentMethod.hasParameter(new SymbolVariable(n.i.s, n.t))) {
      currentMethod.defineParameter(tempVar);
    }
    n.t.accept(this);

    table.addSymbol(n.i.s, n.i);
    n.i.accept(this);
  }

  // Type t;
  // Identifier i;
  // FormalList fl;
  // VarDeclList vl;
  // StatementList sl;
  // Exp e;
  public void visit(MethodDecl n) {
    table.pushScope();
    if (n.t instanceof IdentifierType) {
      IdentifierType steve = (IdentifierType)n.t;
      table.addSymbol(steve.s, steve);
    }
    SymbolMethod tempSym = new SymbolMethod(n.i.s, n.t);
    tempSym.addParent(currentClass);

    currentMethod = tempSym;

    for ( int j = 0; j < n.vl.size(); j++ ) {
      n.vl.elementAt(j).accept(this);
      SymbolVariable tempVar = new SymbolVariable(n.vl.elementAt(j).i.s, n.vl.elementAt(j).t);
      typeChecker.put(tempVar.name, tempVar.type);

      tempVar.defined = true;
      tempSym.addParameter(tempVar);

    }
    methodMap.put(n.i.s, tempSym);




    n.t.accept(this);

    table.addSymbol(n.i.s, n.i);
    n.i.accept(this);
    for ( int i = 0; i < n.fl.size(); i++ ) {
        n.fl.elementAt(i).accept(this);
    }
    for ( int i = 0; i < n.vl.size(); i++ ) {
 //       n.vl.elementAt(i).accept(this);
    }
    for ( int i = 0; i < n.sl.size(); i++ ) {
        n.sl.elementAt(i).accept(this);
    }
    n.e.accept(this);

    table.destroyScope();
  }

  // Type t;
  // Identifier i;
  public String visit(Formal n) {
    if (n.t instanceof IdentifierType) {
      IdentifierType steve = (IdentifierType)n.t;
      table.addSymbol(steve.s, steve);
    }
    n.t.accept(this);



    table.addSymbol(n.i.s, n.i);
    n.i.accept(this);
    return "";
  }

  public String visit(IntArrayType n) {
    return "int array";
  }

  public String visit(BooleanType n) {
    return "boolean";
  }

  public String visit(IntegerType n) {
    return "int";
  }

  // String s;
  public String visit(IdentifierType n) {
    return n.s;
  }

  // StatementList sl;
  public void visit(Block n) {
    for ( int i = 0; i < n.sl.size(); i++ ) {
        n.sl.elementAt(i).accept(this);
    }
  }

  // Exp e;
  // Statement s1,s2;
  public void visit(If n) {
   String temp1 =  n.e.accept(this);
    if (!temp1.equals("boolean")) {
     System.out.println("Non-boolean expression used as the condition of if statement at line %d, character %d");
    }

    n.s1.accept(this);
    n.s2.accept(this);
  }

  // Exp e;
  // Statement s;
  public void visit(While n) {
    String temp1 =  n.e.accept(this);

    if (!temp1.equals("boolean")) {
     System.out.println("Non-boolean expression used as the condition of while statement at line %d, character %d");
    }
    n.s.accept(this);

  }

  // Exp e;
  public void visit(Print n) {
    n.e.accept(this);
  }
  
  // Identifier i;
  // Exp e;
  public void visit(Assign n) {
    table.addSymbol(n.i.s, n.i);
    String type1 = n.i.accept(this);
    String type2 = n.e.accept(this);

    if (typeChecker.get(type1) == null) {
      type1 = "int";
    }
    else {
      type1 = typeChecker.get(type1).toString(); 
    }
    if (!type1.equals(type2)) {
      System.out.println("Type mismatch during assignment at line %d, character %d");
    }

  }

  // Identifier i;
  // Exp e1,e2;
  public void visit(ArrayAssign n) {
    table.addSymbol(n.i.s, n.i);
    n.i.accept(this);
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e1,e2;
  public String visit(And n) {
    String type1 = n.e1.accept(this);
    String type2 = n.e2.accept(this);

    if (!type1.equals("boolean") || !type2.equals("boolean")) {
      System.out.println("Attempt to use boolean operator %s on non-boolean operands at line %d, character %d");
    }
    return "boolean";
  }

  // Exp e1,e2;
  public String visit(LessThan n) {
    String type1 = n.e1.accept(this);
    String type2 = n.e2.accept(this);

    if (!type1.equals("int") || !type2.equals("int")) {
      System.out.println("Non-integer operand for operator < at line %d, character %d");
    }
    return "boolean";
  }

  // Exp e1,e2;
  public String visit(Plus n) {
   String type1 = n.e1.accept(this);
    String type2 = n.e2.accept(this);

    if (!type1.equals("int") || !type2.equals("int")) {
      System.out.println("Non-integer operand for operator + at line %d, character %d");
    }
    return "int";
  }

  // Exp e1,e2;
  public String visit(Minus n) {
    String type1 = n.e1.accept(this);
    String type2 = n.e2.accept(this);

    if (!type1.equals("int") || !type2.equals("int")) {
      System.out.println("Non-integer operand for operator - at line %d, character %d");
    }
    return "int";
  }

  // Exp e1,e2;
  public String visit(Times n) {
    String type1 = n.e1.accept(this);
    String type2 = n.e2.accept(this);

    if (!type1.equals("int") || !type2.equals("int")) {
      System.out.println("Non-integer operand for operator * at line %d, character %d");
    }
    return "int";
  }

  // Exp e1,e2;
  public String visit(ArrayLookup n) {
    n.e1.accept(this);
    n.e2.accept(this);
    return "int";
  }

  // Exp e;
  public String visit(ArrayLength n) {
    String temp = n.e.accept(this);
    if (!temp.equals("int array")) {
      System.out.println("Length property only applies to arrays, line %d, character %d");
    }
  
    return "int";
  }

  // Exp e;
  // Identifier i;
  // ExpList el;
  public String visit(Call n) {
    n.e.accept(this);
  
    table.addSymbol(n.i.s, n.i);
    n.i.accept(this); 

    //if (methodMap.get(n.i.s) == null) {
    if (typeChecker.get(n.i.s) != null) {
      System.out.println("Attempt to call a non-method at line %d, character %d" + n.i.s);
    }

    for ( int i = 0; i < n.el.size(); i++ ) {
        n.el.elementAt(i).accept(this);
    }

    return "int";
  }

  // int i;
  public String visit(IntegerLiteral n) {
    return "int";
  }

  public String visit(True n) {
    return "boolean";
  }

  public String visit(False n) {
    return "boolean";
  }

  // String s;
  public String visit(IdentifierExp n) {
    if (!redefinition(n.s)) {
   //   System.out.println("YO YOU DIDNT DECLARE THAT SHIT FIRST: " + n.s);
    }


    table.addSymbol(n.s, n);
    if (typeChecker.get(n.s) == null) {
      return "int";
    }
    else 
    return typeChecker.get(n.s).toString();
  }

  public String visit(This n) {
    if (!canUseThis) {
      System.out.println("Illegal use of keyword ‘this’ in static method at line %d, character %d");
    }

    return currentClass.name;
  }

  // Exp e;
  public String visit(NewArray n) {
    n.e.accept(this);
    return "int array";
  }

  // Identifier i;
  public String visit(NewObject n) {
    table.addSymbol(n.i.s, n.i);
    return n.i.s;
  }

  // Exp e;
  public String visit(Not n) {
    n.e.accept(this);
    
    return "boolean";
  }

  // String s;
  public String visit(Identifier n) {
    return n.s;
  }
}
