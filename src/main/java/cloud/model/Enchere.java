package cloud.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.Element;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cloud.DAO.ObjectBDD;
import cloud.util.ConnectionMongo;
import cloud.util.Util;


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
    public void setDuree(Double duree) throws Exception {
    	Parametre p=new Parametre();
    	Object[] op = p.find(null);
    	p = (Parametre)op[0];
    	if(duree<p.getDureeEnchereMin()) {
    		throw new Exception("duree trop courte");
    	}if(duree>p.getDureeEnchereMax()) {
    		throw new Exception("duree trop long");
    	}
    	if(duree>=p.getDureeEnchereMin() && duree<=p.getDureeEnchereMax()) {
        this.duree = duree;
    	}
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
    public static String construct_request(String titre,String description,Double prixMinimal,Double prixMaximal,String dateDebut,String dateFin,Integer categorieId,Integer statut) {
    	String requtes = "SELECT  * FROM enchere where 1=1 ";
    	try {
            if (!titre.equals("")) {
                requtes+="AND titre like '%"+titre+"%'";
            }
            if (!description.equals("")) {
                requtes+="AND description like '%"+description+"%'";
            }
            if (statut!=null) {
                requtes+=" AND statut='"+statut+"'";
            }
            if (categorieId!=null) {
                requtes+=" AND categorieid='"+categorieId+"'";
            }
            if (!dateFin.equals("") || !dateDebut.equals("")) {
                if(!dateFin.equals("") && !dateDebut.equals("")){
                    requtes+=" AND (dateenchere BETWEEN '"+dateDebut+"' and '"+dateFin+"')"; 
                }
                if(!dateFin.equals("") && dateDebut.equals("")){
                    requtes+=" AND (dateenchere <= '"+dateFin+"')"; 
                }
                if(dateFin.equals("") && !dateDebut.equals("")){
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
				throw e;
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
            if(conNull){
                con.close();
            }
          } catch (Exception e) {
            throw e;
          }
        }

        return reponse;

      }
      public Enchere(Integer id, String titre, String description, Double prixMinimal, Double duree, Timestamp dateEnchere,
			Integer categorieId, Integer utilisateurId, Integer statut) {
		super();
        this.init();
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
        this.init();
		this.description = description;
		this.prixMinimal = prixMinimal;
		this.duree = duree;
		this.dateEnchere = dateEnchere;
		this.categorieId = categorieId;
		this.utilisateurId = utilisateurId;
		this.statut = statut;
	}
    public void create(Connection c) throws Exception {
		try {

//            String currentId = "Enchere_" + Integer.toString(this.currentSequence(c));
			System.out.println(1);
			this.setId(1);
//            Enchere en=(Enchere) this.find(c)[0];
			MongoDatabase database = ConnectionMongo.getMongoConnection();
			MongoCollection<Document> collection = database.getCollection("enchere");
			Document filtre = new Document("idEnchere", this.getId());
			System.out.println(filtre.toJson());
			Instant instant = Instant.ofEpochMilli(this.getDateEnchere().getTime());
			LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			System.out.println(localDateTime);
			double Duree = getDuree();
			int DureeEnSeconde = (int) Duree * 3600 * 24;
			System.out.println(localDateTime);
			localDateTime = localDateTime.plusSeconds(DureeEnSeconde);
			System.out.println(localDateTime);
			filtre.append("datefin", Timestamp.valueOf(localDateTime).toString());
			System.out.println(filtre.toJson());
			filtre.append("prixdepart", this.getPrixMinimal()).append("description", this.getDescription())
					.append("Nom", this.getTitre());
			System.out.println(filtre.toJson());
			Categorie ca = new Categorie();
			ca.setId(this.getCategorieId());
			ca = (Categorie) ca.find(c)[0];
			filtre.append("Categorie", ca.getNomCat());
			System.out.println(filtre.toJson());
//            this.setEncherir(new ArrayList<>());
//            filtre.append("encherir", this.getEncherir());
			collection.insertOne(filtre);
		} catch (Exception ex) {
			if (c != null)
				c.rollback();
			throw ex;
		}
	}

	public static String getListeEnchere() throws Exception {
		String json ="";
		List<Enchere> retour = new ArrayList<>();
		MongoDatabase database = ConnectionMongo.getMongoConnection();
		MongoCollection<Document> collection = database.getCollection("enchere");
		FindIterable<Document> iterDoc = collection.find();
		Iterator it = iterDoc.iterator();
		List<Element> copy = new ArrayList<Element>();
		while (it.hasNext()) {
			String stemp=it.next().toString();
			System.out.println(stemp);
			if(stemp.contains("Document")==true) {
				json = json + stemp;
			}
			else {break;}
		}
		System.out.println(it);
		return json;

	}

}
