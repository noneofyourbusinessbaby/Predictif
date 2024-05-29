package fr.insalyon.dasi.test.frontend.Serialisation;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.predictif.models.Medium;
import org.apache.http.HttpStatus;

public class ListeMediumsSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) {
        
        List<Medium> mediums = (List<Medium>) request.getAttribute("mediums");

        JsonObject container = new JsonObject();
        
        JsonArray jsonListeMediums = new JsonArray();
    
        if (mediums != null) {
            for (Medium medium : mediums) {
                jsonListeMediums.add(ServiceSerialisation.toJsonObjectMedium(medium));
            }
        }
        
        container.add("mediums", jsonListeMediums);
        
        try (PrintWriter out = response.getWriter()) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            
            gson.toJson(container, out);
            
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(HttpStatus.SC_METHOD_FAILURE);
        }
    }



}