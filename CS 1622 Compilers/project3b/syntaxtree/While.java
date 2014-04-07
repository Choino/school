package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import visitor.SymbolTreeBuilder;

public class While extends Statement {
  public Exp e;
  public Statement s;

  public While(Exp ae, Statement as) {
    e=ae; s=as; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
  
  public void accept(SymbolTreeBuilder v) {
    v.visit(this);
  }
}

