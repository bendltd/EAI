package mergeBanks;

import java.util.List;
import java.util.ArrayList;
import javax.xml.ws.Holder;


public class BankJD {
    
    private ArrayList<Kunde> Kundenliste = new ArrayList();
    private ArrayList<Konto> Kontoliste = new ArrayList();
    
    // Kunden in einer Liste speichern
    public BankJD(){
        
        String status = new String();
        int tempKundenID = 1;
        
        // Holder fÃ¼r Kontokorrent initialisieren
        Holder<String> kkvorname = new Holder();
        Holder<String> kknachname = new Holder();
        Holder<String> kkadresse = new Holder();
        Holder<String> kkland = new Holder();
        Holder<Integer> kkranking = new Holder();
        Holder<String> kkibanKontonummer = new Holder();
        Holder<Float> kkkontostand = new Holder();
        Holder<String> kkbic = new Holder();
        
        // Kunden mit Kontokorrent aus WSDL auslesen
        List<String> NamenKK = listeKontokorrentNachname();
        for(int i = 0; i<NamenKK.size();i++){
            
            holeKontoKorrent("", NamenKK.get(i), kkvorname, kknachname, kkadresse, kkland, kkranking, kkibanKontonummer, kkkontostand, kkbic);
        
            // Ranking gemÃ¤ss Absprache mit Holger
            if(kkranking.value == 1){
                status = "Gold";
            }
            else if(kkranking.value == 2 || kkranking.value == 3){
                status = "Silber";
            }
            else if(kkranking.value == 4 || kkranking.value == 5){
                status = "Bronze";
            }
            // Zürich ausbügeln
            kkadresse.value = kkadresse.value.replace("ZH", "Z\00FCrich");
            
            // Konto aus Kontokorrent erzeugen
            Konto konto = new Konto(tempKundenID, kkibanKontonummer.value, kkkontostand.value, "Kontokorrent");
            // Fehlerhafte IBAN-LÃ¤nge ausweisen
            if(kkibanKontonummer.value.length() != 21){
                konto.setFehler("IBAN ung\00FCltig: Muss 21 Zeichen lang sein");
            }
            Kontoliste.add(konto);
            
            // Kunde aus Kontokorrent erzeugen
            Kunde kunde = new Kunde(tempKundenID, kkvorname.value, kknachname.value, kkadresse.value, kkland.value, status);
            Kundenliste.add(kunde);
            tempKundenID++;
        
        }
        
        // Holder fÃ¼r Sparkonto initialisieren 
        Holder<String> skvorname = new Holder();
        Holder<String> sknachname = new Holder();
        Holder<String> skstrasse = new Holder();
        Holder<String> skplzOrt = new Holder();
        Holder<Float> skzinsen = new Holder();
        Holder<Long> skkontonummer = new Holder();
        Holder<Long> skkontostand = new Holder();
        
        // Kunden mit Sparkonto aus WSDL auslesen
        List<String> NamenSK = listeSparkontoNachname();
        for(int i = 0; i<NamenSK.size();i++){
            
            holeSparkonto("", NamenSK.get(i),skvorname,sknachname,skstrasse,skplzOrt, skzinsen,skkontonummer,skkontostand);
            
            // Kunde und Konto Objekt erstellen
            Kunde kunde = new Kunde();
            kunde.setVorname(skvorname.value);
            kunde.setNachname(sknachname.value);
            Konto konto = new Konto();
            
            // Adresse ans Zielformat anpassen
            String strasse = skstrasse.value.replace(", ", "");
            String adresse = strasse + ", " + skplzOrt.value;
            kunde.setAdresse(adresse);
            
            // LÃ¤ndercode hinzufÃ¼gen
            String laendercode;
            String[] segment = skplzOrt.value.split("\\s");
            if(segment[0].length() == 4){
                laendercode = "CH";
            }
            else if(segment[0].length() == 5){
                laendercode = "DE";
            }
            else if(segment[0].length() == 6){
                laendercode = "NL";
            }
            else{
                laendercode = "leer";
                kunde.setFehler("L\00E4ndercode unbekannt");
            }
            kunde.setLaendercode(laendercode);
                        
            // IBAN fÃ¼r Sparkonto generieren
            String nullen = new String();
            String knr = skkontonummer.value.toString();
            // EinfÃ¼gen von Nullen damit IBAN LÃ¤nge von 21 erreicht wird
            for(int y = knr.length();  y < 12; y++){
                nullen += "0";
            }
            String iban = "CH" + "27" + "22010" + nullen + knr;
            konto.setIban(iban);
            
            // Kontostand nach Float konvertieren
            float Kontostand = (float)skkontostand.value;
            konto.setKontostand(Kontostand);
            
            // PrÃ¼fen ob Person bereits vorhanden
            boolean neu = true;
            for(int j = 0; j < Kundenliste.size(); j++){
            	
            	String[] adr1 = Kundenliste.get(j).getAdresse().split("\\s");
            	String[] adr2 = adresse.split("\\s");
            	String adresseneu = new String();
                
                if(adr1.length > adr2.length){
                	adresseneu = Kundenliste.get(j).getAdresse();
                }
                else{
                	adresseneu = adresse;
                }
                
                // Falls Vorname, Nachname & Adresse übereinstimmt wird kein neuer Kunde erfasst
                if(Kundenliste.get(j).getVorname().equals(skvorname.value) &&
                   Kundenliste.get(j).getNachname().equals(sknachname.value) &&
                   Kundenliste.get(j).getAdresse().equals(adresse)){
                    neu = false;
                    // Konto wird existierendem Kunden zugeteilt
                    int id = Kundenliste.get(j).getKundenid();
                    konto.setKundenid(id);
                    konto.setKontoart("Sparkonto");
                    Kontoliste.add(konto);
                    break;
                }
                // Falls Vorname, Nachname & Strassenname übereinstimmt wird kein neuer Kunde erfasst, aber eine Meldung geworfen
                else if(Kundenliste.get(j).getVorname().equals(skvorname.value) &&
                   Kundenliste.get(j).getNachname().equals(sknachname.value) &&
                   adr1[0].replace(",","").equals(adr2[0].replace(",", "")) &&
                   adr1[adr1.length-1].equals(adr2[adr2.length-1])){
                    neu = false;
                    // Konto wird existierendem Kunden zugeteilt
                    int id = Kundenliste.get(j).getKundenid();
                    Kundenliste.get(j).setFehler("Korrekte Hausnummer bei Kunde erfragen");
                    Kundenliste.get(j).setAdresse(adresseneu);
                    konto.setKundenid(id);
                    konto.setKontoart("Sparkonto");
                    Kontoliste.add(konto);
                    break;
                }
                
                // Falls von den Attributen Vorname, Nachname und Adresse zwei übereinstimmen, wird eine Meldung geworfen, dass evtl. der Kunde mehrmals erfasst wurde
                else if((Kundenliste.get(j).getVorname().equals(skvorname.value) && Kundenliste.get(j).getNachname().equals(sknachname.value)) || 
                        (Kundenliste.get(j).getVorname().equals(skvorname.value) && Kundenliste.get(j).getAdresse().equals(adresse)) ||
                        (Kundenliste.get(j).getAdresse().equals(adresse) && Kundenliste.get(j).getNachname().equals(sknachname.value))){
                    kunde.setFehler("M\00F6glicherweise mehrfach vorhanden");
                    break;
                }
            }
            // Falls Kunde nicht bereits vorhanden ist, werden Kunde & Konto nun in die Liste eingefügt
            if(neu){
                kunde.setKundenid(tempKundenID);
                Kundenliste.add(kunde);
                konto.setKundenid(tempKundenID);
                konto.setKontoart("Sparkonto");
                Kontoliste.add(konto);
                tempKundenID++;
            }
        }
        
    }

    
        
    
    
    
    
    
    
    
    
    public ArrayList<Kunde> getKundenliste() {
        return Kundenliste;
    }

    public ArrayList<Konto> getKontoliste() {
        return Kontoliste;
    }

    public void setKundenliste(ArrayList<Kunde> Kundenliste) {
        this.Kundenliste = Kundenliste;
    }

    public void setKontoliste(ArrayList<Konto> Kontoliste) {
        this.Kontoliste = Kontoliste;
    }

    

    
    private static void holeKontoKorrent(java.lang.String queryVorname, java.lang.String queryNachname, javax.xml.ws.Holder<java.lang.String> vorname, javax.xml.ws.Holder<java.lang.String> nachname, javax.xml.ws.Holder<java.lang.String> adresse, javax.xml.ws.Holder<java.lang.String> land, javax.xml.ws.Holder<java.lang.Integer> ranking, javax.xml.ws.Holder<java.lang.String> ibanKontonummer, javax.xml.ws.Holder<java.lang.Float> kontostand, javax.xml.ws.Holder<java.lang.String> bic) {
        ch.fhnw.wi.eai.bankjd.BankJDService service = new ch.fhnw.wi.eai.bankjd.BankJDService();
        ch.fhnw.wi.eai.bankjd.BankJD port = service.getBankJDPort();
        port.holeKontoKorrent(queryVorname, queryNachname, vorname, nachname, adresse, land, ranking, ibanKontonummer, kontostand, bic);
    }

    private static void holeSparkonto(java.lang.String queryVorname, java.lang.String queryNachname, javax.xml.ws.Holder<java.lang.String> vorname, javax.xml.ws.Holder<java.lang.String> nachname, javax.xml.ws.Holder<java.lang.String> strasse, javax.xml.ws.Holder<java.lang.String> plzOrt, javax.xml.ws.Holder<java.lang.Float> zinsen, javax.xml.ws.Holder<java.lang.Long> kontonummer, javax.xml.ws.Holder<java.lang.Long> kontostand) {
        ch.fhnw.wi.eai.bankjd.BankJDService service = new ch.fhnw.wi.eai.bankjd.BankJDService();
        ch.fhnw.wi.eai.bankjd.BankJD port = service.getBankJDPort();
        port.holeSparkonto(queryVorname, queryNachname, vorname, nachname, strasse, plzOrt, zinsen, kontonummer, kontostand);
    }

    private static java.util.List<java.lang.String> listeKontokorrentNachname() {
        ch.fhnw.wi.eai.bankjd.BankJDService service = new ch.fhnw.wi.eai.bankjd.BankJDService();
        ch.fhnw.wi.eai.bankjd.BankJD port = service.getBankJDPort();
        return port.listeKontokorrentNachname();
    }

    private static java.util.List<java.lang.String> listeSparkontoNachname() {
        ch.fhnw.wi.eai.bankjd.BankJDService service = new ch.fhnw.wi.eai.bankjd.BankJDService();
        ch.fhnw.wi.eai.bankjd.BankJD port = service.getBankJDPort();
        return port.listeSparkontoNachname();
    }
    
}
