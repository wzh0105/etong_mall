package cn.xiaocool.android_etong.bean.json;

import java.io.Serializable;

/**
 * Created by JustYu on 2015/6/15.
 */
public class Carowner  implements Serializable {
    public int getReg_time() {
        return reg_time;
    }

    public void setReg_time(int reg_time) {
        this.reg_time = reg_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(int last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public int getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(int last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReg_ip() {
        return reg_ip;
    }

    public void setReg_ip(int reg_ip) {
        this.reg_ip = reg_ip;
    }

    /*
        返回的json 格式数据
        [
        {
                "uid": "1",
                "avatar": "",//头像
                "nickname": "yunzhu",//昵称
                "sex": "0",
                "birthday": "0000-00-00",
                "phone": "18363866803",
                "score": "10",
                "login": "5",
                "reg_ip": "0",
                "reg_time": "1433750515",

                "last_login_ip": "1020535572",
                "last_login_time": "1433812626",
                "status": "1"
        }
        ]*/
   private int id;
   private String avatar;
   private String nickname;
   private String sex;
   private String birthday;
   private String phone;
   private int score;
   private int login;
   private int last_login_ip;
   private int last_login_time;
   private String status;
    private int reg_ip;
    private int reg_time;

    public  Carowner(){super();}

    public Carowner(int id, String avatar, String nickname, String sex, String birthday, String phone, int score, int login, int last_login_ip, int last_login_time, String status, int reg_ip, int reg_time) {
        super();
        this.id = id;
        this.avatar = avatar;
        this.nickname = nickname;
        this.sex = sex;
        this.birthday = birthday;
        this.phone = phone;
        this.score = score;
        this.login = login;
        this.last_login_ip = last_login_ip;
        this.last_login_time = last_login_time;
        this.status = status;
        this.reg_ip = reg_ip;
        this.reg_time = reg_time;
    }

}
