package mergeBanks;

import java.util.Iterator;
import java.util.LinkedList;

public class Konto implements Comparable<Konto> {
    private int kundenid;
    private String iban;
    private Float kontostand;
    private String kontoart;
    private LinkedList<String> fehler;
    
    public Konto(){
        this.fehler = new LinkedList<String>();
    }
    
    public Konto (int kundenid, String iban, float kontostand, String kontoart) {
        this.kundenid = kundenid;
        this.iban = iban;
        this.kontostand = kontostand;
        this.kontoart = kontoart;
        this.fehler = new LinkedList<String>();
    }

    
    // toString für Objekt Konto
    @Override
    public String toString(){
        String s = this.kundenid + " | ";
        s += this.iban + " | ";
        s += this.kontostand + " | ";
        s += this.kontoart + " | ";
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

    public LinkedList<String> getFehler() {
        return fehler;
    }

    public void setFehler(String fehler) {
        this.fehler.add(fehler);
    }

	@Override
	public int compareTo(Konto konto) {
		return this.kundenid;
	}
  
}
