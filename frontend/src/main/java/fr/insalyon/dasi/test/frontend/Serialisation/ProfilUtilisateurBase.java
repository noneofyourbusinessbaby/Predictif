/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Serialisation;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.predictif.models.Personne;
import org.apache.http.HttpStatus;

/**
    * Serializes the complete user profile and sends it as a JSON response.
    * 
    */
public class ProfilUtilisateurBase extends Serialisation{

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) {
        
        Personne personne = (Personne)request.getAttribute("personne");
        
        if (personne == null) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            return;
        }
                    
        JsonObject container = new JsonObject();

        container.add("personne", ServiceSerialisation.toJsonObjectPersonne(personne));
        
        try (PrintWriter out = response.getWriter()) {
            
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            out.println(gson.toJson(container));
            out.close()  ;

        } catch (Exception e){
            e.printStackTrace();
            response.setStatus(HttpStatus.SC_METHOD_FAILURE);
        }
    }
}

