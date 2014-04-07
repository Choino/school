package visitor;

import visitor.*;
import java.util.ArrayList;
import syntaxtree.*;

public class SymbolClass {

	String name;
	ArrayList<SymbolVariable> memberVariables;
	ArrayList<SymbolMethod> methods;

	public SymbolClass(String name) {
		this.name = name;
		memberVariables = new ArrayList<SymbolVariable>();
		methods = new ArrayList<SymbolMethod>();
	}

	public void addVariable(SymbolVariable t) {
		memberVariables.add(t);
	}

	public boolean hasParameter(SymbolVariable t) {
		return memberVariables.contains(t);
	}

	public void defineParameter(SymbolVariable t) {
		memberVariables.get(memberVariables.indexOf(t)).defined = true;
	}

	public void addMethod(SymbolMethod m) {
		methods.add(m);
	}

}