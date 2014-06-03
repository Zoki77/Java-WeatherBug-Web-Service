/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ztintor.ws.klijenti;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.wxbug.api.LiveWeatherData;
import net.wxbug.api.UnitType;

/**
 *
 * @author zoran
 */
public class WeatherBugKlijent {

    String zip;
    LiveWeatherData meteoPodatak;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public LiveWeatherData getMeteoPodatak() {
        meteoPodatak = dajMeteoPodatke(zip);
        return meteoPodatak;
    }

    /**
     * Funkcija koja daje WS podatke na temelju koda koji se dohvaća iz web.xml-a.
     * @param zip
     * @return 
     */
    public static LiveWeatherData dajMeteoPodatke(String zip) {
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            String kod = (String) env.lookup("kod");
            return getLiveWeatherByUSZipCode(zip, UnitType.METRIC, kod);
        } catch (NamingException ex) {
            return null;
        }
    }

    private static LiveWeatherData getLiveWeatherByUSZipCode(java.lang.String zipCode, net.wxbug.api.UnitType unittype, java.lang.String aCode) {
        net.wxbug.api.WeatherBugWebServices service = new net.wxbug.api.WeatherBugWebServices();
        net.wxbug.api.WeatherBugWebServicesSoap port = service.getWeatherBugWebServicesSoap();
        return port.getLiveWeatherByUSZipCode(zipCode, unittype, aCode);
    }

    public static void main(String[] args) {
        LiveWeatherData lwd = dajMeteoPodatke("10001");
        System.out.println("Grad: " + lwd.getCity()
                + " temp:" + lwd.getTemperature() + " "
                + lwd.getCurrDesc());
    }
}
