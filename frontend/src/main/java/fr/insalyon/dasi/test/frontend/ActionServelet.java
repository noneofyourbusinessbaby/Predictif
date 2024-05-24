/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend;

import fr.insalyon.dasi.predictif.persistence.JpaUtil;
import fr.insalyon.dasi.predictif.services.Services;
import fr.insalyon.dasi.test.frontend.Action.AuthentifierUtilisateurAction;
import fr.insalyon.dasi.test.frontend.Action.GetMediumsAction;
import fr.insalyon.dasi.test.frontend.Action.InscrireUtilisateurAction;
import fr.insalyon.dasi.test.frontend.Action.RecupererProfilClient;
import fr.insalyon.dasi.test.frontend.Action.RecupererProfilEmploye;
import fr.insalyon.dasi.test.frontend.Serialisation.ListeMediumsSerialisation;
import fr.insalyon.dasi.test.frontend.Serialisation.ProfilUtilisateurCompletSerialisation;

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
        
        String todo = request.getParameter("todo");
        
        Services service = new Services();
        
        switch (todo){
            
            case "connecter" : {
                new AuthentifierUtilisateurAction(service).execute(request);
                // todo : récupérer infos utilisateurs dans la session
                break;
            }
            case "inscrire" : {
                new InscrireUtilisateurAction(service).execute(request);
                // todo : récupérer infos utilisateurs dans la session
                break;
            }
            // le paramètre personneId est envoyé par le front-end en paramètre à faire à travers un token
            case "profilClient" : {
                new RecupererProfilClient(service).execute(request);
                new ProfilUtilisateurCompletSerialisation().appliquer(request, response);
                break;
            }
            case "profilEmploye" : {
                new RecupererProfilEmploye(service).execute(request);
                new ProfilUtilisateurCompletSerialisation().appliquer(request, response);
                break;
            }
            case "getMediums" : {
                new GetMediumsAction(service).execute(request);
                new ListeMediumsSerialisation().appliquer(request, response);
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
