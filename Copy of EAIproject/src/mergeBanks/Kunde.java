package mergeBanks;

import java.util.Iterator;
import java.util.LinkedList;

public class Kunde {
    
    private int kundenid;
    private String vorname;
    private String nachname;
    private String adresse;
    private String laendercode;
    private String status;
    private LinkedList<String> fehler;
    
    
public enum status{
        BRONZE, SILBER, GOLD
    }

    // leerer Konstruktor
    public Kunde(){
        this.fehler = new LinkedList();
    }


    // Konstruktor ohne KundenID für JD
    public Kunde(String vorname, String nachname, String adresse, String laendercode, String status){
        this.vorname = vorname;
        this.nachname = nachname;
        this.adresse = adresse;
        this.laendercode = laendercode;
        this.status = status;
        this.fehler = new LinkedList();
    }

    public Kunde(int kundenid, String vorname, String nachname, String adresse, String laendercode, String status){
        this.kundenid = kundenid;
        this.vorname = vorname;
        this.nachname = nachname;
        this.adresse = adresse;
        this.laendercode = laendercode;
        this.status = status;
        this.fehler = new LinkedList();
    }
    
    // toString neu für Objekt Kunde
    @Override
    public String toString(){
        String s = this.kundenid + " | ";
        s += this.vorname + " | ";
        s += this.nachname + " | ";
        s += this.adresse + " | ";
        s += this.laendercode + " | ";
        s += this.status + " | ";
        for(Iterator<String> i = this.fehler.iterator(); i.hasNext();){
            s += i.next() + "; ";
        }
        return s;
    }

    public int getKundenid() {
        return kundenid;
    }

    public void setKundenid(int kundenid) {
        this.kundenid = kundenid;
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

    public LinkedList<String> getFehler() {
        return fehler;
    }

    public void setFehler(String fehler) {
        this.fehler.add(fehler);
    }
    
}
