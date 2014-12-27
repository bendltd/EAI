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
        
        // Holder für Kontokorrent initialisieren
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
        
            // Ranking gemäss Absprache mit Holger
            if(kkranking.value == 1){
                status = "Gold";
            }
            else if(kkranking.value == 2 || kkranking.value == 3){
                status = "Silber";
            }
            else if(kkranking.value == 4 || kkranking.value == 5){
                status = "Bronze";
            }
            
            // Konto aus Kontokorrent erzeugen
            Konto konto = new Konto(tempKundenID, kkibanKontonummer.value, kkkontostand.value, "Kontokorrent");
            // Fehlerhafte IBAN-Länge ausweisen
            if(kkibanKontonummer.value.length() != 21){
                konto.setFehler("IBAN ungültig: Muss 21 Zeichen lang sein");
            }
            Kontoliste.add(konto);
            
            // Kunde aus Kontokorrent erzeugen
            Kunde kunde = new Kunde(tempKundenID, kkvorname.value, kknachname.value, kkadresse.value, kkland.value, status);
            Kundenliste.add(kunde);
            tempKundenID++;
        
        }
        
        // Holder für Sparkonto initialisieren 
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
            String adresse = skstrasse.value + ", " + skplzOrt.value;
            kunde.setAdresse(adresse);
            
            // Ländercode hinzufügen
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
                kunde.setFehler("Ländercode unbekannt");
            }
            kunde.setLaendercode(laendercode);
            
            // Status generieren (nach Kontostand)
//            if(skkontostand.value < 1000000){
//                status = "Bronze";
//            }
//            else if(skkontostand.value <= 10000000){
//                status = "Silber";
//            }
//            else if(skkontostand.value > 10000000){
//                status = "Gold";
//            }
//            kunde.setStatus(status);          
            
            // IBAN für Sparkonto generieren
            String nullen = new String();
            String knr = skkontonummer.value.toString();
            // Einfügen von Nullen damit IBAN Länge von 21 erreicht wird
            for(int y = knr.length();  y < 12; y++){
                nullen += "0";
            }
            String iban = "CH" + "27" + "22010" + nullen + knr;
            konto.setIban(iban);
            
            // Kontostand nach Float konvertieren
            Float Kontostand = (float)skkontostand.value;
            konto.setKontostand(Kontostand);
            
            // Prüfen ob Person bereits vorhanden
            boolean neu = true;
            for(int j = 0; j < Kundenliste.size(); j++){
                
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
                // Falls von den Attributen Vorname, Nachname und Adresse zwei übereinstimmen, wird eine Meldung geworfen, dass evtl. der Kunde mehrmals erfasst wurde
                else if((Kundenliste.get(j).getVorname().equals(skvorname.value) && Kundenliste.get(j).getNachname().equals(sknachname.value)) || 
                        (Kundenliste.get(j).getVorname().equals(skvorname.value) && Kundenliste.get(j).getAdresse().equals(adresse)) ||
                        (Kundenliste.get(j).getAdresse().equals(adresse) && Kundenliste.get(j).getNachname().equals(sknachname.value))){
                    kunde.setFehler("Möglicherweise mehrfach vorhanden");
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
