/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.persistence;

import fr.insalyon.dasi.predictif.models.Client;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author lfaustmann
 */
public class ClientDao {
    public List<Client> getClients () {
        List<Client> clients = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery query = em.createQuery("SELECT c FROM Client c ", Client.class);
            clients = query.getResultList();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return clients;
    }
}
