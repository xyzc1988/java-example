package com.etoak.enums;

public class ETColor {
	public static final ETColor RED = new ETColor();
	public static final ETColor GREEN = new ETColor() ;
	public static final ETColor YELLOW =  new ETColor();
	private static final ETColor[] VALUES={RED,GREEN,YELLOW};
	private ETColor(){};
	String name;
	
	private ETColor(String name){this.name = name;}
	public static ETColor valueOf(String str){
		if(str.equals(RED.toString()))return RED;
		if(str.equals(GREEN.toString()))return GREEN;
		if(str.equals(YELLOW.toString()))return YELLOW;
		return null;
		
	}
	public String toString(){
		if(this==RED)return "RED";
		if(this==GREEN) return "GREEN";
		if(this == YELLOW)return "YELOOW";
		return null;
	}
	public static ETColor[] values(){
		return VALUES;
	}
}
