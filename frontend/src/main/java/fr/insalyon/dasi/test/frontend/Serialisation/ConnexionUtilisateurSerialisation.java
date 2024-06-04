/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.models.Personne;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;

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
              
        if (personne == null){
           response.setStatus(HttpStatus.SC_BAD_REQUEST);
           
           return;
        }
        
        container.addProperty("isClient", personne instanceof Client);
           
        container.addProperty("personneId", personne.getId());
        
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            
            gson.toJson(container, out);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(HttpStatus.SC_FORBIDDEN);
        }
       
    }
    
}
