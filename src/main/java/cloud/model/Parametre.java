package cloud.model;

import cloud.DAO.ObjectBDD;

public class Parametre extends ObjectBDD{
    private Integer id;
    private Double comission;
    private Double dureeEnchereMin;
    private Double dureeEnchereMax;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Double getComission() {
        return comission;
    }
    public void setComission(Double comission) {
        this.comission = comission;
    }
    public Double getDureeEnchereMin() {
        return dureeEnchereMin;
    }
    public void setDureeEnchereMin(Double dureeEnchereMin) {
        this.dureeEnchereMin = dureeEnchereMin;
    }
    public Double getDureeEnchereMax() {
        return dureeEnchereMax;
    }
    public void setDureeEnchereMax(Double dureeEnchereMax) {
        this.dureeEnchereMax = dureeEnchereMax;
    }
    public void init(){
        this.setNomDeTable("parametre");
        this.setPkey("id");
    }
    public Parametre() {
        this.init();
    }
    
}
