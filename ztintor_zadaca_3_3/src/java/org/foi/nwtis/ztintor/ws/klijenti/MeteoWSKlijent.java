/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ztintor.ws.klijenti;

import net.wxbug.api.LiveWeatherData;

/**
 *
 * @author zoran
 */
public class MeteoWSKlijent {

    public static LiveWeatherData dajMeteoWSPodatkeZaZip(java.lang.String zip) {
        org.foi.nwtis.ztintor.ws.serveri.MeteoWS_Service service = new org.foi.nwtis.ztintor.ws.serveri.MeteoWS_Service();
        org.foi.nwtis.ztintor.ws.serveri.MeteoWS port = service.getMeteoWSPort();
        return port.dajMeteoWSPodatkeZaZip(zip);
    }

    public static java.util.List<net.wxbug.api.LiveWeatherData> dajMeteoWSPodatkeZaViseZip(java.util.List<java.lang.String> zipovi) {
        org.foi.nwtis.ztintor.ws.serveri.MeteoWS_Service service = new org.foi.nwtis.ztintor.ws.serveri.MeteoWS_Service();
        org.foi.nwtis.ztintor.ws.serveri.MeteoWS port = service.getMeteoWSPort();
        return port.dajMeteoWSPodatkeZaViseZip(zipovi);
    }
    
}
