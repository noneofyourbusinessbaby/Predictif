package fr.insalyon.dasi.test.frontend.Serialisation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.predictif.models.Astrologue;
import fr.insalyon.dasi.predictif.models.Cartomancien;

import fr.insalyon.dasi.predictif.models.Medium;
import fr.insalyon.dasi.predictif.models.Spirite;
import fr.insalyon.dasi.test.frontend.Serialisation.Serialisation;

public class ListeMediumsSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) {
        
        List<Medium> mediums = (List<Medium>) request.getAttribute("mediums");

        JsonObject container = new JsonObject();
    
        if (mediums != null) {

            JsonArray jsonListeMediums = new JsonArray();
            
            for (Medium medium : mediums) {

                JsonObject jsonMedium = new JsonObject();

                jsonMedium.addProperty("denomination", medium.getDenomination());

                jsonMedium.addProperty("genre", medium.getGenre());

                jsonMedium.addProperty("presentation", medium.getPresentation());
                
                if (medium instanceof Astrologue){
                    jsonMedium.addProperty("formation", ((Astrologue) medium).getFormation());
                    jsonMedium.addProperty("promotion", ((Astrologue) medium).getPromotion());
                }
                else if (medium instanceof Spirite){
                    JsonArray jsonSupport = new JsonArray();
                    for (String support : ((Spirite) medium).getSupports()){
                        jsonSupport.add(support);
                    }
                    jsonMedium.add("support", jsonSupport);
                }
                else{
                    // ON FAIT RIEN 
                }
                jsonListeMediums.add(jsonMedium);
            }
            container.add("mediums", jsonListeMediums);
        }
        else{
            container.add("mediums", null);
        }
        
        try (PrintWriter out = response.getWriter()) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(container, out);
            
        } catch (IOException ex) {
            // Logger.getLogger(ListeMediumsSerialisation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}