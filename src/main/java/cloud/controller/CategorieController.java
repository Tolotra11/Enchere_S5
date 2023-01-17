package cloud.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cloud.model.Categorie;
import cloud.model.Error;
import cloud.model.Success;
import cloud.util.TokenUtil;

@RestController
public class CategorieController {
    @CrossOrigin
    @PostMapping("/categories")
    public HashMap<String,Object> addCategorie(@RequestBody Categorie cat ,@RequestHeader(name="token",required=false) String token){
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
                map = new HashMap<>();
                cat.insert(null);
                Success success = new Success();
                success.setMessage("Insertion infectué avec succes");
                map.put("success",success);
            }
            catch(Exception e){
                Error err = new Error();
                return err.getError(e.getMessage());
            }
        }
        return map;
    }
   
}
