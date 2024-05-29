/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
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
public class AccueilEmployeConnecteSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) {
        
        Employe employe = (Employe) request.getAttribute("employe");
      
        if (employe == null){
            response.setStatus(HttpStatus.SC_BAD_REQUEST); // todo chnager en throw
            return;
        }
        
        JsonArray container = ServiceSerialisation.toJsonObjectListeConsultation(employe);
        
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
