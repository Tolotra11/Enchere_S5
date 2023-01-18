package cloud.model;

import java.sql.Timestamp;

import cloud.DAO.ObjectBDD;

public class Enchere extends ObjectBDD{
    private Integer id;
    private String description;
    private Double prixMinimal;
    private Double duree;
    private Timestamp dateEnchere;
    private Integer categorieId;
    private Integer utilisateurId;
    private Integer statut;
    private String titre;
    
   
	public Enchere(String description, Double prixMinimal, Double duree, Timestamp dateEnchere, Integer categorieId,
			Integer utilisateurId, Integer statut) {
		super();
		this.description = description;
		this.prixMinimal = prixMinimal;
		this.duree = duree;
		this.dateEnchere = dateEnchere;
		this.categorieId = categorieId;
		this.utilisateurId = utilisateurId;
		this.statut = statut;
	}
	 public String getTitre() {
			return titre;
		}
		public void setTitre(String titre) {
			this.titre = titre;
		}
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
    
    public void save() {
    	try {
			this.insert(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
