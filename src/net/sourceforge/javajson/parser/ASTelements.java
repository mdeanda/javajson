/* Generated By:JJTree: Do not edit this line. ASTelements.java */

package net.sourceforge.javajson.parser;

public class ASTelements extends SimpleNode {
	public ASTelements(int id) {
		super(id);
	}

	public ASTelements(JsonParser p, int id) {
		super(p, id);
	}

	@Override
	protected void interpret() {
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof SimpleNode) {
				SimpleNode node = (SimpleNode) children[i];
				node.interpret();
			}
		}
	}

	@Override
	protected void push(Object o) {
		((SimpleNode) parent).push(o);
	}

}
