/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package othergroup;

/**
 *
 * @author eai
 */
public class Konto {
    private int kid;
    private String iban;
    private Float kontostand;
    private String kontoart;
    
    public Konto (int a, String b, float c, String d) {
        this.kid = a;
        this.iban = b;
        this.kontostand = c;
        this.kontoart = d;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
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
