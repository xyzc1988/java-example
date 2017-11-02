package com.etoak.enums;

public enum Color {
	RED(1,"红色"),YELLOW(2,"黄色"),GREEN(3,"绿色");
	Integer num;
	String name;
	private Color(Integer num,String name){
		this.num = num;
		this.name = name;
	}
	
	private Color(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public static Color getColor(String name){
		Color[] colors = Color.values();
		for(Color color : colors){
			if(color.getName().equals(name)){
				return color;
			}
		}
		return null;
	}
	public static Color getColor(Integer num){
		Color[] colors = Color.values();
		for(Color color : colors){
			if(color.getNum() == num){
				return color;
			}
		}
		return null;
	}

    public static void main(String[] args) {
        for (Color color : Color.values()) {
            System.out.println(color.name + ":" + color.ordinal());
        }
        Color color = Color.RED;
        switch (color) {
            case RED:
                System.out.println("是红色!!");
            case YELLOW:
                System.out.println("是黄色!!");
        }
    }
}
