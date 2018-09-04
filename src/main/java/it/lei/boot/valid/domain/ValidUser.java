package it.lei.boot.valid.domain;

import it.lei.boot.valid.NormalValid;
import it.lei.boot.valid.PersonAge;

import java.util.Date;

public class ValidUser {

    private  String username;
    @PersonAge(minAge = 10,groups = {NormalValid.class})
    private Integer age;
    private Date birthday;


    @Override
    public String toString() {
        return "ValidUser{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
