package mergeBanks;

public class Kunde {
    
    private int kundenid;
    private String vorname;
    private String nachname;
    private String adresse;
    private String laendercode;
    private String status;
    
    
public enum status{
        BRONZE, SILBER, GOLD
    }
    
    public Kunde(int kundenid, String vorname, String nachname, String adresse, String laendercode, String status){
        this.kundenid = kundenid;
        this.vorname = vorname;
        this.nachname = nachname;
        this.adresse = adresse;
        this.laendercode = laendercode;
        this.status = status;
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
    
            
    
}
