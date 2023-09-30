package com.EmailClient;

class OfficeFriend extends Recipient{

    private String designation;
    private final String birthDay;

    public OfficeFriend(String name, String email,String designation,String birthDay) {
        super(name, email);
        this.designation=designation;
        this.birthDay=birthDay;

    }
    public boolean checkBirthday(String birthDay){
        return (this.birthDay).substring(5,10).equals(birthDay);
    }

}
