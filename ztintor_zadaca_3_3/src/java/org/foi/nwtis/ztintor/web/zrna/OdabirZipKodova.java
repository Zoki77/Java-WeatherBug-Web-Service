/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.ztintor.web.zrna;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.wxbug.api.LiveWeatherData;
import org.foi.nwtis.ztintor.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.ztintor.rest.klijenti.MeteoRESTKlijent;
import org.foi.nwtis.ztintor.ws.klijenti.MeteoWSKlijent;

/**
 *
 * @author zoran
 */
@ManagedBean
@SessionScoped
public class OdabirZipKodova {

    private List<String> zipKodovi = new ArrayList<>();
    private String zipKodDodaj;
    private List<String> odabraniZipKodovi = new ArrayList<>();
    private String zipKodBrisi;
    private List<LiveWeatherData> meteoWSPodaci = new ArrayList<>();
    private String meteoRESTPodaci;
    private Boolean porukaPetDoDeset = false;
    private Boolean porukaVecDodan = false;
    private Boolean porukaSelektirajZip = false;

    /**
     * Creates a new instance of OdabirZipKodova
     */
    public OdabirZipKodova() {
    }

    /**
     * Funkcija koja se izvršava pritiskom na gumb dodaj. U slučaju da 
     * nije selektiran niti jedan zip kod u 1. meniju samo se ispisuje 
     * poruka da treba odabrati 1 zip kod. Sljedeći uvjet provjerava
     * da li je selektirani kod već dodan. Ako je ispisuje poruku, a 
     * ako nije dodaje ga u listu koja je prikazana u 2. izborniku. 
     *    
     * @return 
     */
    public String dodajZipKod() {
        if (zipKodDodaj == null) {
            porukaVecDodan = false;
            porukaSelektirajZip = true;
        } else if (odabraniZipKodovi.contains(zipKodDodaj)) {
            porukaVecDodan = true;
            porukaSelektirajZip = false;
        } else {
            odabraniZipKodovi.add(zipKodDodaj);
            porukaVecDodan = false;
            porukaSelektirajZip = false;
        }
        porukaPetDoDeset = false;
        return "";
    }

    /**
     * Funkcija koja se izvršava pritiskom na gumb briši. Ukoliko 
     * niti jedan zip kod iz 2. izbornika nije selektiran ispisuje se
     * odgovarajuča poruka, inače se selektirani zip kod briše iz 
     * liste prikazane u 2. izborniku.
     * @return 
     */
    public String brisiZipKod() {
        if (zipKodBrisi == null) {
            porukaSelektirajZip = true;
        } else {
            odabraniZipKodovi.remove(zipKodBrisi);
            porukaSelektirajZip = false;
        }
        porukaPetDoDeset = false;
        porukaVecDodan = false;
        return "";
    }
    
    /**
     * Funkcija koja se izvršava pritiskom na gumb preuzmi podatke WS.
     * Ako se u listi odabranih kodova prikazanoj u 2. izborniku ne nalazi
     * između 5 i 10 zip kodova ispisuje se odgovarajuća poruka, inače se 
     * podatci za zip kodove u 2. izborniku ispisuju na stranici.
     * @return 
     */

    public String dajMeteoWSPodatke() {
        if (odabraniZipKodovi.size() < 5 || odabraniZipKodovi.size() > 10) {
            meteoWSPodaci = null;
            porukaPetDoDeset = true;
        } else {
            meteoWSPodaci = MeteoWSKlijent.dajMeteoWSPodatkeZaViseZip(odabraniZipKodovi);
            porukaPetDoDeset = false;
        }
        porukaVecDodan = false;
        porukaSelektirajZip = false;
        return "";
    }

    /**
     * Funkcija koja se izvršava pritiskom na gumb preuzmi podatke REST.
     * Ukoliko je lista odabranih kodova prazna ili nije selektiran niti jedan 
     * element iz 2. izbornika ispisuje se odgovarajuća poruka, inače se dohvaćaju
     * REST podatci i prikazuju na stranici.
     * @return 
     */
    public String dajMeteoRESTPodatke() {
        if (odabraniZipKodovi.isEmpty() || zipKodBrisi == null) {
            meteoRESTPodaci = "";
            porukaSelektirajZip = true;
        } else {
            MeteoRESTKlijent klijent = new MeteoRESTKlijent(zipKodBrisi);
            meteoRESTPodaci = klijent.getHtml();
            porukaSelektirajZip = false;
        }
        porukaPetDoDeset = false;
        porukaVecDodan = false;
        return "";
    }

    /**
     * Funkcija za dohvaćanje zip kodova iz tablice mycities iz baze.
     * Ukoliko se zip kodovi selektirani pomoću upita već nalaze u listi
     * ne dodaju se ponovo.
     * @return 
     */
    public List<String> getZipKodovi() {
        ServletContext c = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        BP_Konfiguracija konfig = (BP_Konfiguracija) c.getAttribute("BP_Konfiguracija");
        String url = konfig.getServer_database() + konfig.getUser_database();
        String korisnik = konfig.getUser_username();
        String lozinka = konfig.getUser_password();
        String driver = konfig.getDriver_database();

        String upit = "SELECT zip FROM mycities";

        try (Connection veza = DriverManager.getConnection(url, korisnik, lozinka);
                Statement stmt = veza.createStatement();
                ResultSet rs = stmt.executeQuery(upit);) {
            while (rs.next()) {
                String zip = rs.getString("zip");
                if (!zipKodovi.contains(zip)) {
                    zipKodovi.add(zip);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return zipKodovi;
    }

    public void setZipKodovi(List<String> zipKodovi) {
        this.zipKodovi = zipKodovi;
    }

    public String getZipKodDodaj() {
        return zipKodDodaj;
    }

    public void setZipKodDodaj(String zipKodDodaj) {
        this.zipKodDodaj = zipKodDodaj;
    }

    public List<String> getOdabraniZipKodovi() {
        return odabraniZipKodovi;
    }

    public void setOdabraniZipKodovi(List<String> odabraniZipKodovi) {
        this.odabraniZipKodovi = odabraniZipKodovi;
    }

    public String getZipKodBrisi() {
        return zipKodBrisi;
    }

    public void setZipKodBrisi(String zipKodBrisi) {
        this.zipKodBrisi = zipKodBrisi;
    }

    public List<LiveWeatherData> getMeteoWSPodaci() {
        return meteoWSPodaci;
    }

    public void setMeteoWSPodaci(List<LiveWeatherData> meteoWSPodaci) {
        this.meteoWSPodaci = meteoWSPodaci;
    }
    
    public String getMeteoRESTPodaci() {
        if (meteoRESTPodaci == null) {
            return "";
        } else {
            return meteoRESTPodaci;
        }

    }

    public void setMeteoRESTPodaci(String meteoRESTPodaci) {
        this.meteoRESTPodaci = meteoRESTPodaci;
    }

    public Boolean getPorukaPetDoDeset() {
        return porukaPetDoDeset;
    }

    public void setPorukaPetDoDeset(Boolean poruka) {
        this.porukaPetDoDeset = poruka;
    }

    public Boolean getPorukaVecDodan() {
        return porukaVecDodan;
    }

    public void setPorukaVecDodan(Boolean porukaVecDodan) {
        this.porukaVecDodan = porukaVecDodan;
    }

    public Boolean getPorukaSelektirajZip() {
        return porukaSelektirajZip;
    }

    public void setPorukaSelektirajZip(Boolean porukaSelektirajZip) {
        this.porukaSelektirajZip = porukaSelektirajZip;
    }
    
}
