package com.etoak.jsonTest;
 
public class Birthday {
    private String birthday;
    
    public Birthday(String birthday) {
        super();
        this.birthday = birthday;
    }
    //setter、getter
    public Birthday() {}
    
    @Override
    public String toString() {
        return this.birthday;
    }
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
    
}	