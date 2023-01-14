package cloud.model;

import cloud.DAO.ObjectBDD;

public class Categorie extends ObjectBDD{
    private Integer id;
    private String nomCat;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNomCat() {
        return nomCat;
    }
    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }
    public Categorie() {
        this.init();
    }
    public void init(){
        this.setNomDeTable("categorie");
        this.setPkey("id");
    }
}
