package com.smart.helper;

public class Massage {
     private String contant;
     private String type;
	public String getContant() {
		return contant;
	}
	public void setContant(String contant) {
		this.contant = contant;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "massage [contant=" + contant + ", type=" + type + "]";
	}
	public Massage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Massage(String contant, String type) {
		super();
		this.contant = contant;
		this.type = type;
	}
     
}
