package net.sourceforge.javajson.converter.test;

import java.util.Date;

public class SimpleObject {
	private String field1;

	private int field2;

	private float floatField;
	
	private Date today;

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public int getField2() {
		return field2;
	}

	public void setField2(int field2) {
		this.field2 = field2;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public float getFloatField() {
		return floatField;
	}

	public void setFloatField(float floatField) {
		this.floatField = floatField;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("field1: " + field1);
		sb.append("\n");
		sb.append("field2: " + field2);
		sb.append("\n");
		sb.append("floatField: " + floatField);
		sb.append("\n");
		sb.append("today: " + today);
		return sb.toString();
	}
}
