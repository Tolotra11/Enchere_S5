package cloud.model;

import cloud.DAO.ObjectBDD;

public class Utilisateur extends ObjectBDD{
    private Integer id;
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private Double credit;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public Double getCredit() {
        return credit;
    }
    public void setCredit(Double credit) {
        this.credit = credit;
    }
    public void init(){
        this.setNomDeTable("utilisateur");
        this.setPkey("id");
    }
    public Utilisateur() {
        this.init();
    }
    

    
}
