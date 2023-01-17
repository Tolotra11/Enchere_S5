package cloud.model;

import java.sql.Connection;

import java.sql.Timestamp;


import cloud.DAO.ObjectBDD;


public class Enchere extends ObjectBDD{
    private Integer id;
    private String titre;
    private String  description;
    private Double prixMinimal;
    private Double duree;
    private Timestamp dateEnchere;
    private Integer categorieId;
    private Integer utilisateurId;
    private Integer statut;
    private Mouvement_encheres[] me;
    
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrixMinimal() {
        return prixMinimal;
    }
    public void setPrixMinimal(Double prixMinimal) {
        this.prixMinimal = prixMinimal;
    }
    public Double getDuree() {
        return duree;
    }
    public void setDuree(Double duree) {
        this.duree = duree;
    }
    public Timestamp getDateEnchere() {
        return dateEnchere;
    }
    public void setDateEnchere(Timestamp dateEnchere) {
        this.dateEnchere = dateEnchere;
    }
    public Integer getCategorieId() {
        return categorieId;
    }
    public void setCategorieId(Integer categorieId) {
        this.categorieId = categorieId;
    }
    public Integer getUtilisateurId() {
        return utilisateurId;
    }
    public void setUtilisateurId(Integer utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
    public Integer getStatut() {
        return statut;
    }
    public void setStatut(Integer statut) {
        this.statut = statut;
    }
    public void init(){
        this.setNomDeTable("enchere");
        this.setPkey("id");
    }
    public Enchere() {
        this.init();
    }
    public boolean haveAlreadyEnchere() throws Exception{
        boolean val = true;
        try{
            int numberOfRows = this.getMe().length;
            if(numberOfRows == 0){
                val = false;
            }
        }
        catch(Exception e){
            throw e;
        }
        return val;
     }
    public Mouvement_encheres[] getMe() throws Exception {
        if(this.me == null){
            Mouvement_encheres mes = new Mouvement_encheres();
            mes.setEnchereId(this.id);
            Object [] liste = mes.find(null);
            this.me = new Mouvement_encheres[liste.length];
            for(int i=0; i<liste.length; i++){
                this.me[i] = (Mouvement_encheres)liste[i];
            }
        }
        return me;
    }
    public void setMe(Mouvement_encheres[] me) {
        this.me = me;
    }
    public Mouvement_encheres getMaxEnchere(Connection con) throws Exception{
        Mouvement_encheres m = new Mouvement_encheres();
        m.setEnchereId(this.id);
        m =(Mouvement_encheres)m.find(con," 1=1 ORDER BY valeurEnchere DESC LIMIT 1")[0];
        return m;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
}
