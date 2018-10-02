package com.appodeal.support.test;

public class Network {
    private String name;
    private String founder;
    private int flagg;

    public Network(String name, String founder, int flag){
        this.name=name;
        this.founder=founder;
        this.flagg=flag;
    }

    //get
    public String getName() {
        return this.name;
    }
    public String getfounder() {
        return this.founder;
    }
    public int getflagg() {
        return this.flagg;
    }

    //set
    public void setName(String name) {
        this.name = name;
    }
    public void setfounder(String founder) {
        this.founder = founder;
    }
    public void setflagg(int flagg) {
        this.flagg = flagg;
    }
}
