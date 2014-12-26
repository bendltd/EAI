/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package othergroup;

/**
 *
 * @author eai
 */
public class Kunde {
    
    private int kid;
    private String vorname;
    private String nachname;
    private String adresse;
    private String laendercode;
    private String status;
    
    
public enum status{
        BRONZE, SILBER, GOLD
    }
    
    public Kunde(int a, String b, String c, String d, String e, String f){
        this.kid = a;
        this.vorname = b;
        this.nachname = c;
        this.adresse = d;
        this.laendercode = e;
        this.status = f;
    }   

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getLaendercode() {
        return laendercode;
    }

    public void setLaendercode(String laendercode) {
        this.laendercode = laendercode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
            
    
}
