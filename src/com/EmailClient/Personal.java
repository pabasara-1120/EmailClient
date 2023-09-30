package com.EmailClient;

class Personal extends Recipient {
    private String nickName;
    private final String birthDay;

    public Personal(String name, String nickName,String email,String birthDay) {
        super(name, email);
        this.nickName=nickName;
        this.birthDay=birthDay;

    }
    public boolean checkBirthday(String birthDay){
        return (this.birthDay).substring(5,10).equals(birthDay);
    }
}
