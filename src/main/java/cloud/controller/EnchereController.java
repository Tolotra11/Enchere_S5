package cloud.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cloud.model.Enchere;
import cloud.model.Error;
import cloud.model.Success;
import cloud.model.Utilisateur;
import cloud.model.V_enchere;
import cloud.util.TokenUtil;

@RestController
public class EnchereController {
    @CrossOrigin
    @GetMapping("/encheres")
    public HashMap<String,Object> getAll(@RequestHeader(name = "token",required = false) String token){
        HashMap<String,Object> map = null;
        if(token == null || token.equals("")){
            Error err = new Error();
            return err.getError("You're not autorizhed");
        }
        else{
        try{
            String user = new TokenUtil().getUserByToken(token); 
                if(user == null ){
                    Error err = new Error();
                    return err.getError("You're not autorizhed");
                }     
                int idUser = Integer.parseInt(user.split(" ")[1]);
            map = new HashMap<>();
            V_enchere ve = new V_enchere();
            ve.setUtilisateurId(idUser);
            Object [] lE = ve.find(null);
            map.put("data",lE);
        }
        catch(Exception e){
            Error err = new Error();
            err.setMessage(e.getMessage());
        }
    }
        return map;
    }
    @CrossOrigin
    @PostMapping("/encheres/{idEnchere}/rencherir")
    public HashMap<String,Object> rencherir(@PathVariable int idEnchere,@RequestHeader(name = "token",required = false) String token,@RequestHeader(name = "mise",required = true) double mise){
        HashMap<String,Object> map = new HashMap<>();
        if(token == null || token.equals("")){
            Error err = new Error();
            return err.getError("You're not autorizhed");
        }
        else{
            try{
                String user = new TokenUtil().getUserByToken(token); 
                if(user == null ){
                    Error err = new Error();
                    return err.getError("You're not autorizhed");
                }     
                int idUser = Integer.parseInt(user.split(" ")[1]);
                Utilisateur myUser = new Utilisateur();
                myUser.setId(idUser);
                myUser = (Utilisateur)myUser.find(null)[0];
                myUser.renchechir(mise, idEnchere);
                Success success = new Success();
                success.setMessage("reussi");
                map.put("success",success );
            }
            catch(Exception e){
                Error err = new Error();
                e.printStackTrace();
                return err.getError(e.getMessage());

            }
        }
        return map;
    }
    @CrossOrigin
    @PostMapping("/encheres")
    public HashMap<String,Object> saveNewEnchere(@RequestHeader(name="description", required=false) String description,
                               @RequestHeader(name="prixMinimal", required=false) Double prixMinimal, 
                               @RequestHeader(name="duree", required=false) Double duree,
                               @RequestHeader(name="categorieId", required=false) Integer categorieId,
                               @RequestHeader(name="token", required=false) String token,
                               @RequestHeader(name="titre", required=false) String titre
                             ) throws Exception{
                HashMap<String,Object> map = new HashMap<>();
                if(token == null || token.equals("")){
                    Error err = new Error();
                    return err.getError("You're not autorizhed");
                }
                else{
                    Connection con = null;
                    try{
                        con = cloud.util.Util.getConnection();
                        String user = new TokenUtil().getUserByToken(token); 
                        if(user == null ){
                            Error err = new Error();
                            return err.getError("You're not autorizhed");
                        }     
                        int idUser = Integer.parseInt(user.split(" ")[1]);
                        Enchere v = new Enchere();
                        v.setDescription(description);
                        v.setPrixMinimal(prixMinimal);
                        v.setDuree(duree);
                        v.setDateEnchere(Timestamp.valueOf(LocalDateTime.now()));
                        v.setCategorieId(categorieId);
                        v.setUtilisateurId(idUser);
                        v.setStatut(0);
                        v.setTitre(titre);
                        int idEnchere = v.insertReturningId(con);
                        v.setId(idEnchere);
                        v.create(con);
                        Success success = new Success();
                        success.setMessage("Insertion effectué avec succès");
                        map.put("success",success);
                        
                    }
                    catch(Exception e){
                        Error error = new Error();
                        return error.getError(e.getMessage());
                    }
                    finally{
                        if(con!= null){
                            con.close();
                        }
                    }
                }     
                return map; 
        }
    	@CrossOrigin
	    @GetMapping("/rechercheAvance")
	    public HashMap<String,Object> recherehe( @RequestHeader(name="titre", required=false) String titre,
	    		   @RequestHeader(name="description", required=false) String description,
				   @RequestHeader(name="prixMinimal", required=false) Double prixMinimal, 
				   @RequestHeader(name="prixMaximal", required=false) Double prixMaximal, 
				   @RequestHeader(name="dateDebut", required=false) Timestamp dateDebut,
				   @RequestHeader(name="dateFin", required=false) Timestamp dateFin,
				   @RequestHeader(name="categorieId", required=false) Integer categorieId,  
				   @RequestHeader(name="statut", required=false) Integer statut
	    			) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{	      
	 		
            HashMap<String,Object> map = new HashMap<>();    
            try{
                String requete = Enchere.construct_request(titre,description,prixMinimal,prixMaximal,dateDebut.toString(),dateFin.toString(),categorieId,statut);
                map.put("data", Enchere.rechereche_avancer(requete,null));
            }
            catch(Exception e){
                Error err = new Error();
                return err.getError(e.getMessage());
            } 		
            return map;
		 	   
	 	}
         @CrossOrigin
         @GetMapping("/fiches/{id}")
         public String getenchere(@PathVariable int id) throws Exception{	      
              String json = null;
              try {
                  json =Enchere.getListeEnchere(id);        
             } catch (Exception e) {
                 e.printStackTrace();
                 return e.getMessage();             
                }
              
              return json;
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
