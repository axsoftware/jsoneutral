package com.axsoftware.test.util;

public class ObjectChild extends ObjectMaster {
	
	private String name;

	public ObjectChild(Long id, String name) {
		super(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}