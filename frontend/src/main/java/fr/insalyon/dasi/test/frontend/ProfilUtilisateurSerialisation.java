/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nhajjhassa
 */
public class ProfilUtilisateurSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) {
        
        TestUtilisateur user = (TestUtilisateur)request.getAttribute("user");
                    
        JsonObject container = new JsonObject();
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        
        
        if(user != null){            
            JsonObject userJson = new JsonObject();
            
            userJson.addProperty("id" , user.getId());
            userJson.addProperty("prenom" , user.getPrenom());
            userJson.addProperty("nom" , user.getNom());
            userJson.addProperty("mail" , user.getMail()); 
            
            container.add("utilisateur", userJson);
        }
        else {
            container.add("utilisateur", null);
        }
        
        container.addProperty("connexion", user != null);

        
        try (PrintWriter out = response.getWriter()) {
            
            out.println(gson.toJson(container));

            out.close()  ;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
           
}
