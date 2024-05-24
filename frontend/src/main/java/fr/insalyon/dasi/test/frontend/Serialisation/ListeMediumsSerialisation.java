import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import fr.insalyon.dasi.predictif.models.Medium;

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