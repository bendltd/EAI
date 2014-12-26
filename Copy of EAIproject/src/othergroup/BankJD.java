/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package othergroup;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.Holder;

/**
 *
 * @author eai
 */
public class BankJD {

    /**
     * @param args the command line arguments
     */
    //Files for Kontokorrent
    
    static Holder<String> kVorname = new Holder<String>();
    static Holder<String> kNachname = new Holder<String>();
    static Holder<String> kAdresse = new Holder<String>();
    static Holder<String> kLand = new Holder<String>();
    static Holder<Integer> kRanking = new Holder<Integer>();
    static Holder<String> kIbanKontoNr = new Holder<String>();
    static Holder<Float> kKontostand = new Holder<Float>();
    static Holder<String> kBic = new Holder<String>();
    static String status;
    
    //Files for Sparkonto
    
    static Holder<String> sVname = new Holder<String>();
    static Holder<String> sNname = new Holder<String>();
    static Holder<String> sStrasse = new Holder<String>();
    static Holder<String> sPlzOrt = new Holder<String>();
    static Holder<Float> sZinsen = new Holder<Float>();
    static Holder<Long> sKontonummer = new Holder<Long>();
    static Holder<Long> sKontostand = new Holder<Long>();
    static String skAdresse;
    static String skStatus;
    static String land;
    static String skIban;
    static float skKontostand;
       
    
    
    public BankJD() {
        List<String> akontok = new ArrayList<String>();
//        akontok = listeKontokorrentNachname(); //comment, da noch fehlerhaft, von Basti
        
        List<String> y = new ArrayList<String>();
        
        for(String s : akontok){
            
//            holeKontoKorrent(s, kVorname, kNachname , kAdresse , kLand , kRanking,kIbanKontoNr , kKontostand , kBic ); //comment, da noch fehlerhaft, von Basti
            String vorname = kVorname.value;
            String nachname = kNachname.value;
            String adresse = kAdresse.value;
            String land = kLand.value;
            int ranking = kRanking.value;
            String iban = kIbanKontoNr.value;
            Float kontostand = kKontostand.value;
            String bic = kBic.value;
            
            //Ranking convertieren
            if(ranking <3) {
                status = "BRONZE";
            }
            if(ranking ==3) {
                status = "SILBER";
            }
            if(ranking >3) {
                status = "GOLD";
            }
            
            
            
            Fusion.KundenArray.add(new Kunde(Fusion.KIDCounter, vorname, nachname, adresse, land, status));
            Fusion.KontoArray.add(new Konto(Fusion.KIDCounter, iban, kontostand, "Kontokorrent"));
            Fusion.KIDCounter++;
            
//            System.out.println(Fusion.KundenArray.get(0));
//            System.out.println(bic+" "+kontostand+" "+iban+" "+ranking+" "+land+" "+adresse+" "+vorname + " " + s);
        }
        
        List<String> aspar = new ArrayList<String>();
//        aspar = listeSparkontoNachname(); //comment, da noch fehlerhaft, von Basti
        
        for(String s : aspar){
//            holeSparkonto(s, sVname,sNname, sStrasse, sPlzOrt, sZinsen, sKontonummer, sKontostand ); //comment, da noch fehlerhaft, von Basti
            String vname = sVname.value;
            String sadresse = sStrasse.value;
            String plz = sPlzOrt.value;
            Float zinsen = sZinsen.value;
            Long kontonr  = sKontonummer.value;
            Long kontostanda = sKontostand.value;
            
            //Convert adress
            skAdresse = sadresse +" ,"+plz;
            
            //create Land
            String[] segs = plz.split("\\s");
            int a = Integer.parseInt(segs[0]);
            if (a < 10000) {
            land = "CH";
             } else {
            land = "DE";
            }
            
            //create status
            status = "leer";            
            
            //create IBAN
            skIban = "CH" + "27" + "261000" + Long.toString(sKontonummer.value);
            
            //parse Kontostand
            skKontostand = (long) kontostanda;
            
            Fusion.KundenArray.add(new Kunde(Fusion.KIDCounter, vname, sNname.value, skAdresse, land, status));
            Fusion.KontoArray.add(new Konto(Fusion.KIDCounter, skIban, skKontostand, "Sparkonto"));
            Fusion.KIDCounter++;
            
            
//            System.out.println(kontostanda+" "+kontonr+" "+zinsen+" "+plz+" "+sadresse+" "+vname + " " + s);
        }
    }

    
    
//    private static java.util.List<java.lang.String> listeKontokorrentNachname() {
//        eai.bankjd.BankJDService service = new eai.bankjd.BankJDService();
//        eai.bankjd.BankJD port = service.getBankJDPort();
//        return port.listeKontokorrentNachname();
//    }
//
//    private static void holeKontoKorrent(java.lang.String queryNachname, javax.xml.ws.Holder<java.lang.String> vorname, javax.xml.ws.Holder<java.lang.String> nachname, javax.xml.ws.Holder<java.lang.String> adresse, javax.xml.ws.Holder<java.lang.String> land, javax.xml.ws.Holder<java.lang.Integer> ranking, javax.xml.ws.Holder<java.lang.String> ibanKontonummer, javax.xml.ws.Holder<java.lang.Float> kontostand, javax.xml.ws.Holder<java.lang.String> bic) {
//        eai.bankjd.BankJDService service = new eai.bankjd.BankJDService();
//        eai.bankjd.BankJD port = service.getBankJDPort();
//        port.holeKontoKorrent(queryNachname, vorname, nachname, adresse, land, ranking, ibanKontonummer, kontostand, bic);
//    }
//
//    private static void holeSparkonto(java.lang.String queryNachname, javax.xml.ws.Holder<java.lang.String> vorname, javax.xml.ws.Holder<java.lang.String> nachname, javax.xml.ws.Holder<java.lang.String> strasse, javax.xml.ws.Holder<java.lang.String> plzOrt, javax.xml.ws.Holder<java.lang.Float> zinsen, javax.xml.ws.Holder<java.lang.Long> kontonummer, javax.xml.ws.Holder<java.lang.Long> kontostand) {
//        eai.bankjd.BankJDService service = new eai.bankjd.BankJDService();
//        eai.bankjd.BankJD port = service.getBankJDPort();
//        port.holeSparkonto(queryNachname, vorname, nachname, strasse, plzOrt, zinsen, kontonummer, kontostand);
//    }
//
//    private static java.util.List<java.lang.String> listeSparkontoNachname() {
//        eai.bankjd.BankJDService service = new eai.bankjd.BankJDService();
//        eai.bankjd.BankJD port = service.getBankJDPort();
//        return port.listeSparkontoNachname();
//    }
    
    
}
