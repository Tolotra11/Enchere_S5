package cloud.model;

import cloud.DAO.ObjectBDD;

public class Image extends ObjectBDD{
    private Integer id;
    private Byte photo;
    private String nomImage;
    private Integer enchereId;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Byte getPhoto() {
        return photo;
    }
    public void setPhoto(Byte photo) {
        this.photo = photo;
    }
    public String getNomImage() {
        return nomImage;
    }
    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }
    public Integer getEnchereId() {
        return enchereId;
    }
    public void setEnchereId(Integer enchereId) {
        this.enchereId = enchereId;
    }
    public void init(){
        this.setNomDeTable("image");
        this.setPkey("id");
    }
    public Image() {
        this.init();
    }
    
    
}
