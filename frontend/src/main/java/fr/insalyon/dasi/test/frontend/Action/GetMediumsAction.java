package fr.insalyon.dasi.test.frontend.Action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.insalyon.dasi.predictif.models.Medium;
import fr.insalyon.dasi.predictif.services.Services;

public class GetMediumsAction extends Action {

    public GetMediumsAction(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        
        List<Medium> mediums = Services.getMediums();

        request.setAttribute("mediums", mediums);
    }
    
}
