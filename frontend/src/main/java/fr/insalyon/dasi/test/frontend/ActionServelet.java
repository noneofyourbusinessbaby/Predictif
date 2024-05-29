/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend;

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
import fr.insalyon.dasi.test.frontend.Action.RecupererProfilClient;
import fr.insalyon.dasi.test.frontend.Action.RecupererProfilEmploye;
import fr.insalyon.dasi.test.frontend.Serialisation.ProfilClientConsultationEnCoursSerialisation;
import fr.insalyon.dasi.test.frontend.Serialisation.ListeMediumsSerialisation;
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
            
            case "connecter" : {
                new AuthentifierUtilisateurAction(service).execute(request);
                new ConnexionUtilisateurSerialisation().appliquer(request, response); // Si la personne n'est pas logged on a un null qui est renvoyé
                break;  
            }
            case "inscrire" : {
                new InscrireUtilisateurAction(service).execute(request);
                new InscriptionClientSerialisation().appliquer(request, response);
                break;
            }
            case "profilPersonneConnecte" : {
            }
           
            /*            // paratge en deux selon la demande
            case "profilClient" : {
            new RecupererProfilClient(service).execute(request);
            new ProfilUtilisateurBase().appliquer(request, response);
            break;
            }
            case "profilEmploye" : {
            new RecupererProfilEmploye(service).execute(request);
            new ProfilUtilisateurBase().appliquer(request, response);
            break;
            }*/
            case "getMediums" : {
                new GetMediumsAction(service).execute(request);
                new ListeMediumsSerialisation().appliquer(request, response);
                break;
            }
            case "getConsultationEnCours" : { //reuqete pour deux pages 
                new GetConsultationEnCoursAction(service).execute(request);
                new ProfilClientConsultationEnCoursSerialisation().appliquer(request, response);     
            } 
            case "creerConsultation" : {
                new CreerConsultationAction(service).execute(request);
                new CreerConsultationSerialisation(service).appliquer(request ,response);
        
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
