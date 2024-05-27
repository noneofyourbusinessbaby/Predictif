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
import fr.insalyon.dasi.predictif.models.Consultation;
import fr.insalyon.dasi.predictif.models.Medium;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nhajjhassa
 */
public class ConsultationSerialisation extends Serialisation{

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) {
        
         Consultation consultation = (Consultation) request.getAttribute("consultationEnCours");
         
         if (consultation == null){
             response.setStatus(HttpServletResponse.SC_NOT_FOUND);
             return;
         }else {
             
             Client client = consultation.getClient();
             Medium medium = consultation.getMedium();
             
             JsonObject container = new JsonObject();
               
            container.addProperty("denominationMedium", medium.getDenomination());
            container.addProperty("prenomClient", client.getPrenom());
            container.addProperty("nomClient", client.getNom());
      
            try (PrintWriter out = response.getWriter()) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                out.println(gson.toJson(container));
                out.close()  ;

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
