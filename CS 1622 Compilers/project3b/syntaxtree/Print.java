package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import visitor.SymbolTreeBuilder;

public class Print extends Statement {
  public Exp e;

  public Print(Exp ae) {
    e=ae; 
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
