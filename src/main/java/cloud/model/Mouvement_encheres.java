package cloud.model;

import java.sql.Date;

import cloud.DAO.ObjectBDD;

public class Mouvement_encheres extends ObjectBDD{
    private Integer id;
    private Date dateMouvement;
    private Double valeurEnchere;
    private Integer utilisateurId;
    private Integer enchereId;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getDateMouvement() {
        return dateMouvement;
    }
    public void setDateMouvement(Date dateMouvement) {
        this.dateMouvement = dateMouvement;
    }
    public Double getValeurEnchere() {
        return valeurEnchere;
    }
    public void setValeurEnchere(Double valeurEnchere) {
        this.valeurEnchere = valeurEnchere;
    }
    public Integer getUtilisateurId() {
        return utilisateurId;
    }
    public void setUtilisateurId(Integer utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
    public Integer getEnchereId() {
        return enchereId;
    }
    public void setEnchereId(Integer enchereId) {
        this.enchereId = enchereId;
    }
    public void init(){
        this.setNomDeTable("mouvement_encheres");
        this.setPkey("id");
    }
    public Mouvement_encheres() {
    }
    
}
