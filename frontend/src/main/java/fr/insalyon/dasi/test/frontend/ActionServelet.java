/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend;

import fr.insalyon.dasi.test.frontend.Action.RecupererAccueilClientConnecte;
import fr.insalyon.dasi.test.frontend.Serialisation.CreerConsultationSerialisation;
import fr.insalyon.dasi.test.frontend.Action.CreerConsultationAction;
import fr.insalyon.dasi.test.frontend.Action.GetConsultationEnCoursAction;
import fr.insalyon.dasi.test.frontend.Serialisation.InscriptionClientSerialisation;
import fr.insalyon.dasi.test.frontend.Serialisation.ConnexionUtilisateurSerialisation;
import fr.insalyon.dasi.predictif.persistence.JpaUtil;
import fr.insalyon.dasi.predictif.services.Services;
import fr.insalyon.dasi.test.frontend.Action.AuthentifierUtilisateurAction;
import fr.insalyon.dasi.test.frontend.Action.GetMediumsAction;
import fr.insalyon.dasi.test.frontend.Action.InscrireUtilisateurAction;
import fr.insalyon.dasi.test.frontend.Action.RecupererAcueilEmployeConnecteAction;
import fr.insalyon.dasi.test.frontend.Action.RecupererProfilConnecteAction;
import fr.insalyon.dasi.test.frontend.Action.RecupererResultatPredictionAction;
import fr.insalyon.dasi.test.frontend.Serialisation.AccueilClientConnecteSerialisation;
import fr.insalyon.dasi.test.frontend.Serialisation.AccueilEmployeConnecteSerialisation;
import fr.insalyon.dasi.test.frontend.Serialisation.ProfilClientConsultationEnCoursSerialisation;
import fr.insalyon.dasi.test.frontend.Serialisation.ListeMediumsSerialisation;
import fr.insalyon.dasi.test.frontend.Serialisation.PredictionResultatSerialisation;
import fr.insalyon.dasi.test.frontend.Serialisation.ProfilUtilisateurBase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nhajjhassa
 */
@WebServlet(name = "ActionServelet", urlPatterns = {"/ActionServelet"})
public class ActionServelet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.creerFabriquePersistance();
    }

    @Override
    public void destroy() {
        JpaUtil.fermerFabriquePersistance();
        super.destroy(); 
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");     
        
        String todo = request.getParameter("action");
        
        Services service = new Services();
                
        switch (todo){
                      
            // http://localhost:8080/frontend/ActionServelet?action=SeConnecter&email=martion.camille@gmail.com&password=123456789
            case "SeConnecter" : {
                new AuthentifierUtilisateurAction(service).execute(request);
                new ConnexionUtilisateurSerialisation().appliquer(request, response); // Si la personne n'est pas logged on a un null qui est renvoyé
                break;  
            } //ok
           
           // http://localhost:8080/frontend/ActionServelet?action=SeEnregistrer&prenom=Kiki&nom=hprt&email=kiki@kik2i&password=123456789&genre=M&dateNaissance=07/10/96%204:5%20PM,%20PDT&telephone=01236547&adressePostale=69100
            case "SeEnregistrer" : {
                new InscrireUtilisateurAction(service).execute(request);
                new InscriptionClientSerialisation().appliquer(request, response);
                break;
            }
            
            // http://localhost:8080/frontend/ActionServelet?action=GetProfilPersonneConnecte
            case "GetProfilPersonneConnecte" : {
                new RecupererProfilConnecteAction(service).execute(request);
                new ProfilUtilisateurBase().appliquer(request, response);
                break;
            }
            // http://localhost:8080/frontend/ActionServelet?action=GetAccueilClientConnecte
            case "GetAccueilClientConnecte" : {
                new RecupererAccueilClientConnecte(service).execute(request);
                new AccueilClientConnecteSerialisation().appliquer(request, response);
                break;
            }
            // http://localhost:8080/frontend/ActionServelet?action=GetAcueilEmployeConnecte
            case "GetAcueilEmployeConnecte" : {
                new RecupererAcueilEmployeConnecteAction(service).execute(request);
                new AccueilEmployeConnecteSerialisation().appliquer(request,response);
                break;
            }
            // http://localhost:8080/frontend/ActionServelet?action=GetPredictions&amour=1&sante=1&travail=1
            case "GetPredictions" : {
                new RecupererResultatPredictionAction(service).execute(request);
                new PredictionResultatSerialisation().appliquer(request, response);
                break;
            }
            // http://localhost:8080/frontend/ActionServelet?action=GetMediums
            case "GetMediums" : {
                new GetMediumsAction(service).execute(request);
                new ListeMediumsSerialisation().appliquer(request, response);
                break;
            }
            // http://localhost:8080/frontend/ActionServelet?action=GetConsultationEnCours
            case "GetConsultationEnCours" : { //reuqete pour deux pages 
                new GetConsultationEnCoursAction(service).execute(request);
                new ProfilClientConsultationEnCoursSerialisation().appliquer(request, response);     
                break;
            } 
            // http://localhost:8080/frontend/ActionServelet?action=CreateConsultation&mediumId=1
            case "CreateConsultation" : {
                new CreerConsultationAction(service).execute(request);
                new CreerConsultationSerialisation(service).appliquer(request ,response);
                break;
            }
            default : {
                break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
