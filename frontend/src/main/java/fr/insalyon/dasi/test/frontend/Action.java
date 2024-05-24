/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend;

import fr.insalyon.dasi.predictif.services.Services;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author nhajjhassa
 */
public abstract class Action {
    
    Services service;
    
    public Action(Services service){
        this.service = service;
    }
    
    public abstract void execute(HttpServletRequest request);    
}
