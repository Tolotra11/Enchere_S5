package cloud.model;

import cloud.DAO.ObjectBDD;

public class Admin extends ObjectBDD{
    private Integer id;
    private String identifiant;
    private String password;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getIdentifiant() {
        return identifiant;
    }
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void init(){
        this.setNomDeTable("admin");
        this.setPkey("id");
    }
    public Admin(){
        this.init();
    }
    
}
