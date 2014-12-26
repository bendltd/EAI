package mergeBanks;

public class Konto {
    private int kundenid;
    private String iban;
    private Float kontostand;
    private String kontoart;
    
    public Konto (int kundenid, String iban, float kontostand, String kontoart) {
        this.kundenid = kundenid;
        this.iban = iban;
        this.kontostand = kontostand;
        this.kontoart = kontoart;
    }

    public int getKundenid() {
        return kundenid;
    }

    public void setKundenid(int kundenid) {
        this.kundenid = kundenid;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Float getKontostand() {
        return kontostand;
    }

    public void setKontostand(Float kontostand) {
        this.kontostand = kontostand;
    }

    public String getKontoart() {
        return kontoart;
    }

    public void setKontoart(String kontoart) {
        this.kontoart = kontoart;
    }
    
    
    
}
