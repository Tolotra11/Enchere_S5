package cloud.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cloud.model.Error;
import cloud.model.V_enchere;

@RestController
public class V_enchereController {
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
}
