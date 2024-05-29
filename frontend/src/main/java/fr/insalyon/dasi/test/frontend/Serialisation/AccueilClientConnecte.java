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
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;

/**
 *
 * @author nhajjhassa
 */
public class AccueilClientConnecte extends Serialisation{

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) {
        
        Client client = (Client)request.getAttribute("client");
        
        // il faut récupérer les consultations et le profil Astral
        
        JsonObject container = new JsonObject();
        
        container.add("profilAstral", ServiceSerialisation.toJsonProfilAstral(client));
        container.add("consultations", ServiceSerialisation.toJsonObjectListeConsultation(client));
                   
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

