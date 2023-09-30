package com.EmailClient;

abstract class Recipient {
    private final String name;
    private final String email;

    static int noOfObjects=0;




    public Recipient(String name,String email){
        this.name=name;
        this.email=email;
        noOfObjects+=1;
    }

    public String getEmail() {
        return email;
    }

    public String getName(){return name;}
}
