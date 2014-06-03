/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ztintor.rest.serveri;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import net.wxbug.api.LiveWeatherData;
import org.foi.nwtis.ztintor.ws.klijenti.WeatherBugKlijent;

/**
 * REST Web Service
 *
 * @author zoran
 */
public class MeteoREST {

    private String zip;

    /**
     * Creates a new instance of MeteoREST
     */
    private MeteoREST(String zip) {
        this.zip = zip;
    }

    /**
     * Get instance of the MeteoREST
     */
    public static MeteoREST getInstance(String zip) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of MeteoREST class.
        return new MeteoREST(zip);
    }

    /**
     * Funkcija koja vraća podatke za REST u sređenom html formatu.
     * Retrieves representation of an instance of org.foi.nwtis.ztintor.rest.serveri.MeteoREST
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml() {
        LiveWeatherData lwd = WeatherBugKlijent.dajMeteoPodatke(zip);
        String ret = "<table class=\"tablicaREST\">";
        ret += "<tr><td>Grad</td><td>" + lwd.getCity() + "</td></tr>";
        ret += "<tr><td>Temperatura</td><td>" + lwd.getTemperature()+ "</td></tr>";
        ret += "<tr><td>Vlaznost</td><td>" + lwd.getHumidity()+ "</td></tr>";
        ret += "<tr><td>Opis</td><td>" + lwd.getCurrDesc()+ "</td></tr>";
        ret += "</table>";
        return ret;
    }

    /**
     * PUT method for updating or creating an instance of MeteoREST
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }

    /**
     * DELETE method for resource MeteoREST
     */
    @DELETE
    public void delete() {
    }
}
