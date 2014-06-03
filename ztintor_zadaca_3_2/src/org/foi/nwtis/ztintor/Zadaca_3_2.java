/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ztintor;

import java.util.ArrayList;
import java.util.List;
import net.wxbug.api.LiveWeatherData;
import org.foi.nwtis.ztintor.rest.klijenti.MeteoRESTKlijent;

/**
 *
 * @author zoran
 */
public class Zadaca_3_2 {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Trebam min. 2 argumenta");
            MeteoRESTKlijent mrk = new MeteoRESTKlijent("10001");
            System.out.println(mrk.getHtml());
            return;
        }
        if (args[0].equals("1")) {
            List<String> zipovi = new ArrayList<>();
            for (int i = 1; i < args.length; i++) {
                zipovi.add(args[i]);
            }
            List<LiveWeatherData> lwds = dajMeteoWSPodatkeZaViseZip(zipovi);
            for (LiveWeatherData lwd : lwds) {
                System.out.println("Grad:" + lwd.getCity() + ", Temp:"
                        + lwd.getTemperature() + ",vlaznost:" + lwd.getHumidity());
            }

        } else if (args[0].equals("2")) {
            MeteoRESTKlijent mrk = new MeteoRESTKlijent(args[1]);
            System.out.println(mrk.getHtml());
        }
    }

    private static java.util.List<net.wxbug.api.LiveWeatherData> dajMeteoWSPodatkeZaViseZip(java.util.List<java.lang.String> zipovi) {
        org.foi.nwtis.ztintor.ws.serveri.MeteoWS_Service service = new org.foi.nwtis.ztintor.ws.serveri.MeteoWS_Service();
        org.foi.nwtis.ztintor.ws.serveri.MeteoWS port = service.getMeteoWSPort();
        return port.dajMeteoWSPodatkeZaViseZip(zipovi);
    }

    private static LiveWeatherData dajMeteoWSPodatkeZaZip(java.lang.String zip) {
        org.foi.nwtis.ztintor.ws.serveri.MeteoWS_Service service = new org.foi.nwtis.ztintor.ws.serveri.MeteoWS_Service();
        org.foi.nwtis.ztintor.ws.serveri.MeteoWS port = service.getMeteoWSPort();
        return port.dajMeteoWSPodatkeZaZip(zip);
    }
    
}
