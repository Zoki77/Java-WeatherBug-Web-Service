/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ztintor.ws.serveri;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import net.wxbug.api.LiveWeatherData;
import org.foi.nwtis.ztintor.ws.klijenti.WeatherBugKlijent;


/**
 *
 * @author nwtis_4
 */
@WebService(serviceName = "MeteoWS")
public class MeteoWS {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajMeteoWSPodatkeZaZip")
    public LiveWeatherData dajMeteoWSPodatkeZaZip(@WebParam(name = "zip") String zip) {
        return WeatherBugKlijent.dajMeteoPodatke(zip);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajMeteoWSPodatkeZaViseZip")
    public java.util.List<net.wxbug.api.LiveWeatherData> dajMeteoWSPodatkeZaViseZip(@WebParam(name = "zipovi") java.util.List<java.lang.String> zipovi) {
        List<LiveWeatherData> lista = new ArrayList<LiveWeatherData>();
        for (String zip : zipovi) {
            lista.add(WeatherBugKlijent.dajMeteoPodatke(zip));
        }
        return lista;
    }
}
