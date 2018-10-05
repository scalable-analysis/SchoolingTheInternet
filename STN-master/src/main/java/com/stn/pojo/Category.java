package com.stn.pojo;

public class Category {

    private int id;
    private int pinned;
    private String categorie ;
    private String descriere ;
    private int idFacultate;
    private int idSerie;
    private int idGrupa;
    private int totalTopics;
    private int totalPosts;

    public Category(int id, int pinned, String categorie, String descriere, int idFacultate, int idSerie, int idGrupa) {
        this.id = id;
        this.pinned = pinned;
        this.categorie = categorie;
        this.descriere = descriere;
        this.idFacultate = idFacultate;
        this.idSerie = idSerie;
        this.idGrupa = idGrupa;
    }

    public Category(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPinned() {
        return pinned;
    }

    public void setPinned(int pinned) {
        this.pinned = pinned;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getIdFacultate() {
        return idFacultate;
    }

    public void setIdFacultate(int idFacultate) {
        this.idFacultate = idFacultate;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    public int getIdGrupa() {
        return idGrupa;
    }

    public void setIdGrupa(int idGrupa) {
        this.idGrupa = idGrupa;
    }

    public void setTotalTopics(int totalTopics) {
        this.totalTopics = totalTopics;
    }

    public int getTotalTopics() {
        return totalTopics;
    }

    public int getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(int totalPosts) {
        this.totalPosts = totalPosts;
    }
}
