package net.sourceforge.javajson.converter.test;

import java.util.LinkedList;
import java.util.List;

public class ComplexObject {
	private SimpleObject simpleObject = new SimpleObject();

	private List<Integer> intList = new LinkedList<Integer>();

	private List<SimpleObject> simpleList = new LinkedList<SimpleObject>();

	public ComplexObject() {
		intList.add(5);
		intList.add(7);
		intList.add(0);

		simpleList.add(new SimpleObject());
		simpleList.add(new SimpleObject());
	}

	public List<Integer> getIntList() {
		return intList;
	}

	public List<SimpleObject> getSimpleList() {
		return simpleList;
	}

	public SimpleObject getSimpleObject() {
		return simpleObject;
	}

	
	public void setIntList(List<Integer> intList) {
		this.intList = intList;
	}

	public void setSimpleList(List<SimpleObject> simpleList) {
		this.simpleList = simpleList;
	}

	public void setSimpleObject(SimpleObject simpleObject) {
		this.simpleObject = simpleObject;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("simpleObject: " + simpleObject);
		sb.append("\n");
		sb.append("intList: [");
		for (Integer i : intList) {
			sb.append(i);
			sb.append(" ");
		}
		sb.append("]");
		sb.append("\n");
		sb.append("simpleList: [");
		for (SimpleObject i : simpleList) {
			sb.append(i);
			sb.append(" ");
		}
		sb.append("]");
		
		return sb.toString();
	}
}
