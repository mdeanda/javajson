package com.thedeanda.javajson.parser;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonObject;
import com.thedeanda.javajson.JsonValue;

import java.util.ArrayList;
import java.util.Stack;

public class ParserVisitor implements JsonParserVisitor {
    private Stack<ParserState> stack = new Stack<>();

    @Override
    public Object visit(SimpleNode node, Object data) {
        return data;
    }

    @Override
    public Object visit(ASTparse node, Object data) {
        Object output = node.childrenAccept(this, null);
        return ((SimpleNode) node.children[0]).jjtGetValue();
    }

    @Override
    public Object visit(ASTobject node, Object data) {
        JsonObject output = new JsonObject();
        ParserState state = new ParserState();
        state.object = output;
        stack.push(state);

        node.jjtSetValue(output);
        Object children = node.childrenAccept(this, output);

        stack.pop();
        return output;
    }

    @Override
    public Object visit(ASTarray node, Object data) {
        JsonArray output = new JsonArray();
        ParserState state = new ParserState();
        state.array = output;
        stack.push(state);

        node.jjtSetValue(output);
        Object children = node.childrenAccept(this, output);

        stack.pop();
        return output;
    }

    @Override
    public Object visit(ASTmembers node, Object data) {
        ArrayList<Object> members = new ArrayList<>();
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTelements node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(ASTvalue node, Object data) {
        node.childrenAccept(this, data);
        Object value;
        if (node.children != null) {
            value = ((SimpleNode) node.children[0]).jjtGetValue();
        } else {
            value = node.jjtGetValue();
        }
        ParserState top = stack.peek();
        if (top.object != null) {
            ((JsonObject) data).put(top.key, value);
        } else {
            ((JsonArray) data).add(value);
        }
        return data;
    }

    @Override
    public Object visit(ASTkey node, Object data) {
        stack.peek().key = (String) node.jjtGetValue();
        return data;
    }

    @Override
    public Object visit(ASTstring node, Object data) {
        Object v = new JsonValue((String)node.jjtGetValue(), true);
        return v;
    }

    @Override
    public Object visit(ASTnumber node, Object data) {
        return data;
    }
}
