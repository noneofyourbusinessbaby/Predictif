/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.predictif.models.Personne;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nhajjhassa
 */
public class ConnexionUtilisateurSerialisation extends Serialisation {

    public ConnexionUtilisateurSerialisation() {
    }

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) {
        
       JsonObject container = new JsonObject();
       
        Personne personne = (Personne)request.getAttribute("personne");
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
       
       if (personne != null){
           container.addProperty("personneId",personne.getId());
       }
       else{
            container.add("personneId", null);
       }
        
        try (PrintWriter out = response.getWriter()) {
            gson.toJson(container, out);
            
        } catch (IOException ex) {
            // Logger.getLogger(ListeMediumsSerialisation.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
}
