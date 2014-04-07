package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import visitor.SymbolTreeBuilder;
import visitor.SymbolVisitor;

public abstract class Type {

  public abstract void accept(Visitor v);
  public abstract Type accept(TypeVisitor v);
  public abstract String accept(SymbolTreeBuilder v);
  
  public abstract String toString();
}
