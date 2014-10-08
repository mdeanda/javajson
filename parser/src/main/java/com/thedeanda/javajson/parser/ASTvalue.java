/* Generated By:JJTree: Do not edit this line. ASTvalue.java */

package com.thedeanda.javajson.parser;

public class ASTvalue extends SimpleNode {
	String val;

	public ASTvalue(int id) {
		super(id);
	}

	public ASTvalue(JsonParser p, int id) {
		super(p, id);
	}

	@Override
	protected void interpret() {
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				if (children[i] instanceof SimpleNode) {
					SimpleNode node = (SimpleNode) children[i];
					node.interpret();
				}
			}
		} else {
			if ("true".equals(val))
				push(Boolean.TRUE);
			else if ("false".equals(val))
				push(Boolean.FALSE);
			else if ("null".equals(val))
				push(null);
		}
	}

	@Override
	protected void push(Object o) {
		((SimpleNode) parent).push(o);
	}

}
