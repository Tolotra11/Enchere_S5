package cloud.model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

import cloud.DAO.ObjectBDD;
import cloud.util.TokenUtil;
import cloud.util.Util;

public class Utilisateur extends ObjectBDD{
    private Integer id;
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private Double credit;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public Double getCredit() {
        return credit;
    }
    public void setCredit(Double credit) {
        this.credit = credit;
    }
    public void init(){
        this.setNomDeTable("utilisateur");
        this.setPkey("id");
    }
    public Utilisateur() {
        this.init();
    }
    public void renchechir(double mise,int idEnchere) throws Exception{
        Connection con = null;
        if(mise > this.getCredit()){
            throw new Exception("Credit insuffisant");
        }
        try{
            con = Util.getConnection();
            con.setAutoCommit(false);
            Enchere enchere = new Enchere();
            enchere.setId(idEnchere);
            enchere = (Enchere)enchere.find(con)[0];
            Mouvement_encheres mves = null;
            if(!enchere.haveAlreadyEnchere()){
                if(mise < enchere.getPrixMinimal()){
                    throw new Exception("Votre mise doit être superieur au prix minimum d'enchere");
                }
            }
            else{
                mves = enchere.getMaxEnchere(con);
                if(mves.getValeurEnchere() > mise){
                    throw new Exception("mise trop bas");
                }
                Utilisateur lastUser = new Utilisateur();
                lastUser.setId(mves.getUtilisateurId());
                lastUser = (Utilisateur)lastUser.find(con)[0];
                lastUser.setCredit(lastUser.getCredit() + mves.getValeurEnchere());
                System.out.println("djkshd:"+lastUser.getCredit());
                lastUser.update("id", con);
            }
            this.setCredit(this.getCredit() - mise);
            //Le nouvel mouvement à inserer
            Mouvement_encheres newMouvement = new Mouvement_encheres();
            LocalDateTime now = LocalDateTime.now();
            newMouvement.setDateMouvement(Timestamp.valueOf(now));
            newMouvement.setValeurEnchere(mise);
            newMouvement.setUtilisateurId(this.id);
            newMouvement.setEnchereId(idEnchere);
            newMouvement.insert(con);
            //Update des credits des utilisateurs
            this.update("id", con);
            con.commit();
        }
        catch(Exception e){
            con.rollback();
            throw e;
        }
        finally{
            if(con != null){
                con.close();
            }
        }

    }
    public HashMap<String,Object> login() throws Exception{
        HashMap<String,Object> val = new HashMap<>();
        HashMap<String,Object> fin = new HashMap<>();
        TokenUtil utilToken = new TokenUtil();
        this.setPassword(Util.getMd5(this.getPassword()));
        Utilisateur result = (Utilisateur)this.find(null)[0];
        if(result == null){
            throw new Exception("Mot de passe ou identifiant incorrect");
        }
        else{
            String token = utilToken.generateToken(result);
            val.put("token", token);
            val.put("idUtilisateur",result.getId());
            val.put("login", result.getLogin());
            fin.put("data", val);
        }
        return fin;
    }
    

    
}
