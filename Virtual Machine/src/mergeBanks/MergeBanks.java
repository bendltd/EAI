package mergeBanks;

import java.util.ArrayList;

public class MergeBanks {
	static ArrayList<Kunde> KundenArray = new ArrayList<Kunde>();
	static ArrayList<Konto> KontenArray = new ArrayList<Konto>();
	static int kundenidcnt = 1;
	

	public static void main(String[] args) {
		
		// Laden der VCT Daten
		VCTBankDBConnection.getVCTDatas();
		// Laden der JD Daten
        BankJD DataJD = new BankJD();
        ArrayList<Kunde> JDKunden = DataJD.getKundenliste();
        ArrayList<Konto> JDKonti = DataJD.getKontoliste();
        
        // Merge Kunden
        for(int i = 0; i < JDKunden.size(); i++){
        	boolean neu = true;
            for(int j = 0; j < KundenArray.size(); j++){
            	
            	// Falls Vorname, Nachname & Adresse Ã¼bereinstimmt wird kein neuer Kunde erfasst
                if(JDKunden.get(i).getVorname().equals(KundenArray.get(j).getVorname()) &&
                	JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname()) &&
                	JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse())){
                	
                	// Status wird von JD übernommen
                	KundenArray.get(j).setStatus(JDKunden.get(i).getStatus());                	
                    neu = false;
                    
                    // Konto wird existierendem Kunden zugeteilt
                    int id = KundenArray.get(j).getKundenid();
                    JDKonti.get(i).setKundenid(id);
                    KontenArray.add(JDKonti.get(i));
                    break;
                }
                // Falls von den Attributen Vorname, Nachname und Adresse zwei Ã¼bereinstimmen, wird eine Meldung geworfen, dass evtl. der Kunde mehrmals erfasst wurde
                else if((JDKunden.get(i).getVorname().equals(KundenArray.get(j).getVorname()) && JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname())) || 
                        (JDKunden.get(i).getVorname().equals(KundenArray.get(j).getVorname()) && JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse())) ||
                        (JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse()) && JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname()))){
                    JDKunden.get(i).setFehler("Möglicherweise mehrfach vorhanden");
                    break;
                }
            }
            // Falls Kunde nicht bereits vorhanden ist, werden Kunde & Konto nun in die Liste eingefÃ¼gt
            if(neu){
                JDKunden.get(i).setKundenid(kundenidcnt);
                KundenArray.add(JDKunden.get(i));
                JDKonti.get(i).setKundenid(kundenidcnt);
                KontenArray.add(JDKonti.get(i));
                kundenidcnt++;
            }
        }
            	
        // Status erfassen falls noch nicht vorhanden (nach Gesamtvermögen) - Machi fertig wenni heichume
//        for(int i = 0; i < KundenArray.size(); i++){
//        	float gesamtvermögen;
//        	if(KundenArray.get(i).getStatus().equals(null)){
//        		int id = KundenArray.get(i).getKundenid();
//        		
//        	}
//        }
        
		
        System.out.println("Ausgabe Kundenarray");
        for (int i = 0; i < KundenArray.size(); i++) {
            System.out.println(KundenArray.get(i).getKundenid() + "\t" + KundenArray.get(i).getVorname() + "\t" + KundenArray.get(i).getNachname() + "\t\t" + KundenArray.get(i).getAdresse() + "\t" + KundenArray.get(i).getLaendercode() + "\t" + KundenArray.get(i).getStatus());
        }
        System.out.println("\nAusgabe Kontenarray");
        for (int i = 0; i < KontenArray.size(); i++) {
            System.out.println(KontenArray.get(i).getIban() + "\t" + KontenArray.get(i).getKontostand() + "\t\t" + KontenArray.get(i).getKontoart());
        }
        
        // Silv Test Stuff
//        System.out.println("JD Kunden");
//        for(int i = 0; i<DataJD.getKundenliste().size();i++){
//            System.out.println(DataJD.getKundenliste().get(i).toString());
//        }
//        System.out.println("JD Konti");
//        for(int i = 0; i < DataJD.getKontoliste().size(); i++){
//            System.out.println(DataJD.getKontoliste().get(i).toString());
//        }
        
	}

}
