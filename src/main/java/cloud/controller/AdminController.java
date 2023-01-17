package cloud.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cloud.model.Admin;
import cloud.model.Demande_credit;
import cloud.model.Error;
import cloud.model.Parametre;
import cloud.model.Success;
import cloud.util.TokenUtil;

@RestController
public class AdminController {
    @CrossOrigin
    @PostMapping("/loginAdmin")
    public HashMap<String,Object> login(@RequestBody Admin admin){
        HashMap<String,Object> map = null;
       
            try{
                map = admin.login();
            }
            catch(Exception e){
                Error err = new Error();
                return err.getError("Email ou mot de passe incorrect");
            }
         
        return map;
    }
    @CrossOrigin
    @PutMapping("/parametres")
    public HashMap<String,Object> updateParametre(@RequestBody Parametre parametre,@RequestHeader(name="token",required=false) String token){
        HashMap<String,Object> map = null;
        if(token == null || token.equals("")){
            Error err = new Error();
            return err.getError("You're not autorizhed");
        }
        else{
            try{
                map = new HashMap<> ();
                    String user = new TokenUtil().getUserByToken(token);
                    if(user == null ){
                        Error err = new Error();
                        return err.getError("You're not autorizhed");
                    }
                parametre.setId(1);
                parametre.update();
                Success success = new Success();
                success.setMessage("Configuration effectuée avec succès");
                map.put("succes", success);
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
    @GetMapping("/parametres")
    public HashMap<String,Object> listeParametre(@RequestHeader(name="token",required=false) String token){
        HashMap<String,Object> map = null;
        if(token == null || token.equals("")){
            Error err = new Error();
            return err.getError("You're not autorizhed");
        }
        else{
            try{
                map = new HashMap<>();
                String user = new TokenUtil().getUserByToken(token);
                    if(user == null ){
                        Error err = new Error();
                        return err.getError("You're not autorizhed");
                    }
                Parametre parametre = (Parametre)new Parametre().find(null)[0];
                map.put("data",parametre);
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
    @PutMapping("/validationCredits/{id}")
    public HashMap<String,Object> validationCredit(@RequestHeader(name="token",required=false) String token, @PathVariable int id){
        HashMap<String, Object> map = null;
        if(token == null || token.equals("")){
            Error err = new Error();
            return err.getError("You're not autorizhed");
        }
        else{
            try{
                map = new HashMap<>();
                String user = new TokenUtil().getUserByToken(token);
                    if(user == null ){
                        Error err = new Error();
                        return err.getError("You're not autorizhed");
                    }
                Admin.validationCredit(id);
                Success success = new Success();
                success.setMessage("Succesful");
                map.put("Succes",success);
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
    @GetMapping("/demandeCredits")
    public HashMap<String,Object> listeDemandeCredit(@RequestHeader(name="token",required=false) String token) throws Exception{
        HashMap<String, Object> map = null;
        if(token == null || token.equals("")){
            Error err = new Error();
            return err.getError("You're not autorizhed");
        }
        else{
            try{
                    map = new HashMap<>();
                    String user = new TokenUtil().getUserByToken(token);
                        if(user == null ){
                            Error err = new Error();
                            return err.getError("You're not autorizhed");
                        }
                    Object [] listeDemande = new Demande_credit().find(null);
                    map.put("data", listeDemande);
            }
            catch(Exception e){
                Error err = new Error();
                e.printStackTrace();
                return err.getError(e.getMessage());

        }
        return map;
    }
}


}
