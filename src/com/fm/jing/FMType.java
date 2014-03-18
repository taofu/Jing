package com.fm.jing;

public enum FMType {
	
	EXPLORE(0x1000,"explore"),
	ILIKE(0x1001,"ilike"),
	MESSAGE(0x1002,"message");
	
	public int id;
	public String tag;
	FMType(int id,String tag){
		this.id = id;
		this.tag = tag;
	}
}
