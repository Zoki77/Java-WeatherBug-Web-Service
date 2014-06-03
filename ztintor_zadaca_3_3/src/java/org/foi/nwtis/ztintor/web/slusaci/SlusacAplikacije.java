/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ztintor.web.slusaci;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.ztintor.konfiguracije.bp.BP_Konfiguracija;

/**
 * Web application lifecycle listener.
 *
 * @author zoran
 */
@WebListener()
public class SlusacAplikacije implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String path = sce.getServletContext().getRealPath("WEB-INF");

        String datoteka = path + File.separator
                + sce.getServletContext().getInitParameter("konfiguracija");
        System.out.println("Datoteka konfiguracije: " + datoteka + "<br/>");
        BP_Konfiguracija konfig = new BP_Konfiguracija(datoteka);
        System.out.println("Konfiguracija ucitana");
        sce.getServletContext().setAttribute("BP_Konfiguracija", konfig);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
