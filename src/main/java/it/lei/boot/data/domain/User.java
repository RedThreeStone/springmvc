package it.lei.boot.data.domain;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ssmUserId;
    @Column(name = "user_age")
    private Integer userAge;
    @Column
    private String username;
    @Column
    private String password;
    @Column(name = "userSex")
    private String userSex;

    @Override
    public String toString() {
        return "User{" +
                "ssmUserId=" + ssmUserId +
                ", userAge=" + userAge +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userSex='" + userSex + '\'' +
                '}';
    }

    public Integer getSsmUserId() {
        return ssmUserId;
    }

    public void setSsmUserId(Integer ssmUserId) {
        this.ssmUserId = ssmUserId;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
    }
}