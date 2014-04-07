package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import visitor.SymbolTreeBuilder;

public abstract class Exp {
  public abstract void accept(Visitor v);
  public abstract Type accept(TypeVisitor v);
  public abstract String accept(SymbolTreeBuilder v);

}
