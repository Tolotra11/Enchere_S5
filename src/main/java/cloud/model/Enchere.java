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
import java.util.Vector;

import javax.lang.model.element.Element;
import javax.print.Doc;

import java.sql.Timestamp;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import cloud.DAO.ObjectBDD;
import cloud.util.ConnectionMongo;
import cloud.util.Util;
import net.bytebuddy.asm.Advice.This;



public class Enchere extends ObjectBDD {
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

	public Enchere(Integer id, String titre, String description, Double prixMinimal, Double duree,
			Timestamp dateEnchere, Integer categorieId, Integer utilisateurId, Integer statut) {
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

	public void setDuree(Double duree) throws Exception {
		Connection con = null;
		Parametre p = new Parametre();
		Object[] op = p.find(con);
		p = (Parametre) op[0];
//    	if(duree<p.getDureeEnchereMin()) {
//    		throw new Exception("duree trop courte");
//    	}if(duree>p.getDureeEnchereMax()) {
//    		throw new Exception("duree trop long");
//    	}
//    	if(duree>=p.getDureeEnchereMin() && duree<=p.getDureeEnchereMax()) {
		this.duree = duree;
//    	}

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

	public void init() {
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
			filtre.append("dateenchere",this.getDateEnchere().toString());
			double Duree = getDuree();
			filtre.append("Duree",getDuree().toString());
			int DureeEnSeconde = (int) Duree * 3600 * 24;
			System.out.println(localDateTime);
			localDateTime = localDateTime.plusSeconds(DureeEnSeconde);
			System.out.println(localDateTime);
			filtre.append("datefin", Timestamp.valueOf(localDateTime).toString());
			System.out.println(filtre.toJson());
			filtre.append("prixdepart", this.getPrixMinimal());
			filtre.append("description", this.getDescription());
			filtre.append("Titre", this.getTitre());
			System.out.println(filtre.toJson());
			Categorie ca = new Categorie();
			ca.setId(this.getCategorieId());
			ca = (Categorie) ca.find(c)[0];
			
			Utilisateur user = new Utilisateur();
			user.setId(this.getUtilisateurId());
			user =(Utilisateur) user.find(c)[0];
			
			filtre.append("Utilisateurid", user.getId());
			filtre.append("UtilisateurEmail", user.getLogin());
			filtre.append("Nom", user.getNom());
			filtre.append("Prenom", user.getPrenom());
			filtre.append("UserCredit", user.getCredit());
			
			filtre.append("Categorie", this.getCategorieId());
			filtre.append("NomCategorie", ca.getNomCat());
			filtre.append("Statut", this.getStatut().toString());
			System.out.println(filtre.toJson());
			collection.insertOne(filtre);
		} catch (Exception ex) {
			if (c != null)
				c.rollback();
			throw ex;
		}
	}
	
	
	public static String constructJsonMongo(String jsonInputString) throws Exception{
	     
        String[] ltObject = Enchere.splitObject(jsonInputString, "Document");
        System.out.println("ltobj: " + ltObject.length);
        String data=" ";
        for (int i = 0; i < ltObject.length; i++) {
            System.out.println(ltObject[i]);
        }
        for (int i = 0; i < ltObject.length; i++) {
           String requeteamboarina = ltObject[i].replace("{{_", "{");
           String requeteamboarinafarany = requeteamboarina.replace("}}", "}");   
           ltObject[i] = requeteamboarinafarany;
           }
        for (int i = 0; i < ltObject.length; i++) {
            System.out.println(ltObject[i]);
           data = data + ltObject[i] +";";}
       
        data = data.replace(";", "");
        System.out.println("data: "+data);
        return data;
     }
     
     
      public static String[] splitObject(String str,String var) throws Exception {
        String[] arrOfstr = str.split(var);
        return arrOfstr;
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

	public static String construct_request(String titre, String description, Double prixMinimal, Double prixMaximal,
			String dateDebut, String dateFin, Integer categorieId, Integer statut) {
			String dateDebut, String dateFin, String categorieId, String statut) {
		String requtes = "SELECT  * FROM enchere where 1=1 ";
		try {
			if (titre != null) {
			if (!titre.equalsIgnoreCase("")) {
				requtes += "AND titre like '%" + titre + "%'";
			}
			if (description != null) {
			if (!description.equalsIgnoreCase("")) {
				requtes += "AND description like '%" + description + "%'";
			}
			if (statut != null) {
			if (!statut.toString().equalsIgnoreCase("")) {
				requtes += " AND statut='" + statut + "'";
			}
			if (categorieId != null) {
			if (!categorieId.toString().equalsIgnoreCase("")) {
				requtes += " AND categorieid='" + categorieId + "'";
			}
			if (dateFin != null || dateDebut != null) {
				if (dateFin != null && dateDebut != null) {
			System.out.println("erico");
			System.out.println(dateFin +"||"+dateDebut);
			if ((!dateFin.equalsIgnoreCase("")) || (!dateDebut.equalsIgnoreCase(""))) {
				if (!dateFin.equalsIgnoreCase("") && !dateDebut.equalsIgnoreCase("")) {
					requtes += " AND (dateenchere BETWEEN '" + dateDebut + "' and '" + dateFin + "')";
				}
				if (dateFin != null && dateDebut == null) {
				if (!dateFin.equalsIgnoreCase("") && dateDebut.equalsIgnoreCase("")) {
					requtes += " AND (dateenchere <= '" + dateFin + "')";
				}
				if (dateFin == null && dateDebut != null) {
				if (dateFin.equalsIgnoreCase("") && !dateDebut.equalsIgnoreCase("")) {
					requtes += " AND (dateenchere >= '" + dateDebut + "')";
				}
			}
			if (prixMaximal != null || prixMinimal != null) {
				if (prixMaximal != null && prixMinimal != null) {
			if (prixMaximal != 0 || prixMinimal != 0) {
				if (prixMaximal != 0 && prixMinimal != 0) {
					requtes += " AND (prixminimal BETWEEN '" + prixMinimal + "' and '" + prixMaximal + "')";
				}
				if (prixMaximal != null && prixMinimal == null) {
				if (prixMaximal != 0 && prixMinimal == 0) {
					requtes += " AND (prixminimal <= '" + prixMaximal + "')";
				}
				if (prixMaximal == null && prixMinimal != null) {
				if (prixMaximal == 0 && prixMinimal != 0) {
					requtes += " AND (prixminimal >= '" + prixMinimal + "')";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return requtes;
	}

	public static List<Enchere> rechereche_avancer(String request, Connection con)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		List<Enchere> reponse = new ArrayList<Enchere>();
		java.sql.Statement statement = null;
		ResultSet result = null;
		boolean conNull = Util.connectionNull(con);
		if (conNull) {
			con = Util.getConnection();
		}
		try {
			statement = con.createStatement();
			result = statement.executeQuery(request);
			while (result.next()) {
				Enchere var = new Enchere(result.getInt(1), result.getString(2), result.getString(3),
						result.getDouble(4), result.getDouble(5), result.getTimestamp(6), result.getInt(7),
						result.getInt(8), result.getInt(9));
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
