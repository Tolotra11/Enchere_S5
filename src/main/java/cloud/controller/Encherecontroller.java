package cloud.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cloud.model.Enchere;

@RestController
public class Encherecontroller {
	 	@CrossOrigin
	    @PostMapping("/enchere")
	    public void saveNewEnchere(@RequestHeader(name="description", required=false) String description,
	    						   @RequestHeader(name="prixMinimal", required=false) Double prixMinimal, 
	    						   @RequestHeader(name="duree", required=false) Double duree,
	    						   @RequestHeader(name="dateEnchere", required=false) Timestamp dateEnchere,
	    						   @RequestHeader(name="categorieId", required=false) Integer categorieId,
	    						   @RequestHeader(name="utilisateurId", required=false) Integer utilisateurId,
	    						   @RequestHeader(name="titre", required=false) String titre,
	    						   @RequestHeader(name="statut", required=false) Integer statut) throws Exception{	        
		 	Enchere v = new Enchere();
	        v.setDescription(description);
	        v.setPrixMinimal(prixMinimal);
	        v.setDuree(duree);
	        v.setDateEnchere(dateEnchere);
	        v.setCategorieId(categorieId);
	        v.setUtilisateurId(utilisateurId);
	        v.setStatut(statut);
	        v.setTitre(titre);
	        v.save();
	    }
	 	
	 	
	 	@CrossOrigin
	    @GetMapping("/enchere")
	    public List<Enchere> recherehe( @RequestHeader(name="titre", required=false) String titre,
	    		   @RequestHeader(name="description", required=false) String description,
				   @RequestHeader(name="prixMinimal", required=false) Double prixMinimal, 
				   @RequestHeader(name="prixMaximal", required=false) Double prixMaximal, 
				   @RequestHeader(name="dateDebut", required=false) Timestamp dateDebut,
				   @RequestHeader(name="dateFin", required=false) Timestamp dateFin,
				   @RequestHeader(name="categorieId", required=false) Integer categorieId,  
				   @RequestHeader(name="statut", required=false) Integer statut
	    			) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{	      
	 		Connection con=null;
	 		String requete = Enchere.construct_request(titre,description,prixMinimal,prixMaximal,dateDebut.toString(),dateFin.toString(),categorieId,statut);
	 		System.out.println(requete);     
	 		return Enchere.rechereche_avancer(requete,con);
		 	   
	 	}
	 	
	 	@CrossOrigin
	    @PostMapping("/enchere/mongo")
	    public void createMongo(@RequestHeader(name="description", required=false) String description,
				   @RequestHeader(name="prixMinimal", required=false) Double prixMinimal, 
				   @RequestHeader(name="duree", required=false) Double duree,
				   @RequestHeader(name="dateEnchere", required=false) Timestamp dateEnchere,
				   @RequestHeader(name="categorieId", required=false) Integer categorieId,
				   @RequestHeader(name="utilisateurId", required=false) Integer utilisateurId,
				   @RequestHeader(name="titre", required=false) String titre,
				   @RequestHeader(name="statut", required=false) Integer statut) throws Exception
	    		{	
	 		Enchere v = new Enchere();
	        v.setDescription(description);
	        v.setPrixMinimal(prixMinimal);
	        v.setDuree(duree);
	        v.setDateEnchere(dateEnchere);
	        v.setCategorieId(categorieId);
	        v.setUtilisateurId(utilisateurId);
	        v.setStatut(statut);
	        v.setTitre(titre);
	 		Connection con=null;
	 		v.create(con);
		 	   
	 	}
	 	
}