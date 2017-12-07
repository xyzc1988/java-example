package com.etoak.enums;

public enum ColorEnum {
    RED(1, "红色"), YELLOW(2, "黄色"), GREEN(3, "绿色");
    Integer num;
    String name;

    ColorEnum(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

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

    public static ColorEnum getColor(String name) {
        ColorEnum[] colorEnums = ColorEnum.values();
        for (ColorEnum colorEnum : colorEnums) {
            if (colorEnum.getName().equals(name)) {
                return colorEnum;
            }
        }
        return null;
    }

    public static ColorEnum getColor(Integer num) {
        ColorEnum[] colorEnums = ColorEnum.values();
        for (ColorEnum colorEnum : colorEnums) {
            if (colorEnum.getNum().equals(num)) {
                return colorEnum;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        for (ColorEnum colorEnum : ColorEnum.values()) {
            System.out.println(colorEnum.name + ":" + colorEnum.ordinal());
        }
        ColorEnum colorEnum = ColorEnum.RED;
        switch (colorEnum) {
            case RED:
                System.out.println("是红色!!");
            case YELLOW:
                System.out.println("是黄色!!");
        }
    }
}
