package cloud.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cloud.model.Error;
import cloud.model.Utilisateur;

@RestController
public class UtilisateurController {
    @CrossOrigin
    @PostMapping("/login")
    public HashMap<String,Object> login(@RequestBody Utilisateur admin){
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
}
