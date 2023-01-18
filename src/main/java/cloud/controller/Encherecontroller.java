package cloud.controller;

import java.sql.Timestamp;

import org.springframework.web.bind.annotation.CrossOrigin;
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
	    						   @RequestHeader(name="statut", required=false) Integer statut){	        
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
}