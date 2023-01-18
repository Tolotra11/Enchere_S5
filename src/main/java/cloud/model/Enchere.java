package cloud.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cloud.DAO.ObjectBDD;
import cloud.util.Util;

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
    
   
	@Override
	public String toString() {
		return "Enchere [id=" + id + ", description=" + description + ", prixMinimal=" + prixMinimal + ", duree="
				+ duree + ", dateEnchere=" + dateEnchere + ", categorieId=" + categorieId + ", utilisateurId="
				+ utilisateurId + ", statut=" + statut + ", titre=" + titre + "]";
	}

	public Enchere(Integer id, String titre, String description, Double prixMinimal, Double duree, Timestamp dateEnchere,
			Integer categorieId, Integer utilisateurId, Integer statut) {
		super();
		this.id = id;
		this.description = description;
		this.prixMinimal = prixMinimal;
		this.duree = duree;
		this.dateEnchere = dateEnchere;
		this.categorieId = categorieId;
		this.utilisateurId = utilisateurId;
		this.statut = statut;
		this.titre = titre;
	}
	
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
    
    
    public static String construct_request(String titre,String description,Double prixMinimal,Double prixMaximal,String dateDebut,String dateFin,Integer categorieId,Integer statut) {
    	String requtes = "SELECT  * FROM enchere where 1=1 ";
    	try {
            if (titre!=null) {
                requtes+="AND titre like '%"+titre+"%'";
            }
            if (description!=null) {
                requtes+="AND description like '%"+description+"%'";
            }
            if (statut!=null) {
                requtes+=" AND statut='"+statut+"'";
            }
            if (categorieId!=null) {
                requtes+=" AND categorieid='"+categorieId+"'";
            }
            if (dateFin!=null || dateDebut!=null) {
                if(dateFin!=null && dateDebut!=null){
                    requtes+=" AND (dateenchere BETWEEN '"+dateDebut+"' and '"+dateFin+"')"; 
                }
                if(dateFin!=null && dateDebut==null){
                    requtes+=" AND (dateenchere <= '"+dateFin+"')"; 
                }
                if(dateFin==null && dateDebut!=null){
                    requtes+=" AND (dateenchere >= '"+dateDebut+"')"; 
                }
            }
            if (prixMaximal!=null || prixMinimal!=null) {
                if(prixMaximal!=null && prixMinimal!=null){
                    requtes+=" AND (prixminimal BETWEEN '"+prixMinimal+"' and '"+prixMaximal+"')"; 
                }
                if(prixMaximal!=null && prixMinimal==null){
                    requtes+=" AND (prixminimal <= '"+prixMaximal+"')"; 
                }
                if(prixMaximal==null && prixMinimal!=null){
                    requtes+=" AND (prixminimal >= '"+prixMinimal+"')"; 
                }
            }
    		}catch (Exception e) {
				// TODO: handle exception
			}
            return requtes;
    }
    
    
    public static List<Enchere> rechereche_avancer(String request,Connection con) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

    	List<Enchere> reponse = new ArrayList<Enchere>();
    	java.sql.Statement statement = null;
        ResultSet result = null;
         boolean conNull = Util.connectionNull(con);
        if(conNull){
            con = Util.getConnection(); 
        }
        try {
          statement = con.createStatement();
          result = statement.executeQuery(request);
          while(result.next()) { 
            Enchere var = new Enchere(result.getInt(1),result.getString(2),result.getString(3),result.getDouble(4),result.getDouble(5),
            		result.getTimestamp(6),result.getInt(7),result.getInt(8),result.getInt(9));
            reponse.add(var);
          }
        } catch (Exception e) {
          System.out.println(e);
        } finally {
          try {
            result.close();
            statement.close();
            con.close();
          } catch (Exception e) {
            System.out.println(e);
          }
        }

        return reponse;

      }
    
}
