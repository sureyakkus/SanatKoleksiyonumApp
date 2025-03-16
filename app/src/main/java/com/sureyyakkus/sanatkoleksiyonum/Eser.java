package com.sureyyakkus.sanatkoleksiyonum;

public class Eser {
    private int id;
    private String eserAdi;
    private String sanatciAdi;
    private int eserYili;

    // Yapıcı metod
    public Eser(int id, String eserAdi, String sanatciAdi, int eserYili) {
        this.id = id;
        this.eserAdi = eserAdi;
        this.sanatciAdi = sanatciAdi;
        this.eserYili = eserYili;
    }

    // Getter metodları
    public String getEserAdi() {
        return eserAdi;
    }

    public void setEserAdi(String eserAdi) {
        this.eserAdi = eserAdi;
    }

    public String getSanatciAdi() {
        return sanatciAdi;
    }

    public void setSanatciAdi(String sanatciAdi) {
        this.sanatciAdi = sanatciAdi;
    }

    public int getEserYili() {
        return eserYili;
    }

    public void setEserYili(int eserYili) {
        this.eserYili = eserYili;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
