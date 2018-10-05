package com.stn.pojo;

import java.sql.Timestamp;
import java.util.Date;

public class Comments {
    Integer id=-1;
    Integer idPost=-1;
    Integer idUser=-1;
    String cont="";
    Timestamp dop;
    String username;
    Integer userClass;
    Timestamp lastEdit;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserClass() {
        return userClass;
    }

    public void setUserClass(Integer userClass) {
        this.userClass = userClass;
    }

    public Timestamp getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Timestamp lastEdit) {
        this.lastEdit = lastEdit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public Timestamp getDop() {
        return dop;
    }

    public void setDop(Timestamp dop) {
        this.dop = dop;
    }
}
