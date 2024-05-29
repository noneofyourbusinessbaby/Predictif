/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.predictif.models.Consultation;
import fr.insalyon.dasi.predictif.services.Services;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;

/**
 *
 * @author nhajjhassa
 */
public class CreerConsultationSerialisation extends Serialisation{

    public CreerConsultationSerialisation(Services service) {
    }

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) {
        
        Consultation consultation = (Consultation) request.getAttribute("consultation");
        
        if (consultation == null){
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            
            return;
        }
        
        JsonObject container = new JsonObject();
            
        container.addProperty("consultationId", consultation.getId());
                    
        try (PrintWriter out = response.getWriter()) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            gson.toJson(container, out);

        } catch (Exception e){
            e.printStackTrace();
            response.setStatus(HttpStatus.SC_METHOD_FAILURE);
        }
    }
    
}
