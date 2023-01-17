package cloud.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cloud.model.Error;
import cloud.model.Success;
import cloud.model.Utilisateur;
import cloud.model.V_enchere;
import cloud.util.TokenUtil;

@RestController
public class EnchereController {
    @CrossOrigin
    @GetMapping("/encheres")
    public HashMap<String,Object> getAll(){
        HashMap<String,Object> map = null;
        try{
            map = new HashMap<>();
            V_enchere ve = new V_enchere();
            ve.setStatut(0);
            Object [] lE = ve.find(null);
            map.put("data",lE);
        }
        catch(Exception e){
            Error err = new Error();
            err.setMessage(e.getMessage());
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
}
