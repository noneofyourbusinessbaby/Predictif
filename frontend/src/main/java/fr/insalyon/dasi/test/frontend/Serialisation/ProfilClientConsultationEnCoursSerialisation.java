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
import fr.insalyon.dasi.predictif.models.Employe;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;

/**
 *
 * @author nhajjhassa
 */
public class ProfilClientConsultationEnCoursSerialisation extends Serialisation{

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) {
        
         Consultation consultation = (Consultation) request.getAttribute("consultationEnCours");
         Employe employe = (Employe) request.getAttribute("employe");
         if (consultation == null){
             response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
             return;
         }

        JsonObject container = new JsonObject();

        Client client = consultation.getClient();

        container.add("profilAstral", ServiceSerialisation.toJsonProfilAstral(client));
        container.add("profilClient", ServiceSerialisation.toJsonObjectPersonne(client));
        container.add("consultations", ServiceSerialisation.toJsonObjectListeConsultation(employe));

        try (PrintWriter out = response.getWriter()) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            
            gson.toJson(container, out);
            
            out.close();
        } catch (Exception e){
            e.printStackTrace();
            response.setStatus(HttpStatus.SC_METHOD_FAILURE);
        }
    }
}

