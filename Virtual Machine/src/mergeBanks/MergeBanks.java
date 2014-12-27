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
        int AnzahlVCTKunden = KundenArray.size();
        for(int i = 0; i < JDKunden.size(); i++){
        	boolean neu = true;
            for(int j = 0; j < AnzahlVCTKunden; j++){
            	
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
            	
        // Status erfassen falls noch nicht vorhanden (nach Gesamtvermögen)
        for(int i = 0; i < KundenArray.size(); i++){
        	float total = 0;
        	if(KundenArray.get(i).getStatus().equals(null)){
        		for(int j = 0; j < KontenArray.size(); j++){
        			if(KundenArray.get(i).getKundenid() == KontenArray.get(j).getKundenid()){
        				total += KontenArray.get(j).getKontostand();
        			}
        		}
        	}
          if(total < 500000){
        	  KundenArray.get(i).setStatus("Bronze");
          }
          else if(total <= 5000000){
        	  KundenArray.get(i).setStatus("Silber");
          }
          else if(total > 5000000){
        	  KundenArray.get(i).setStatus("Gold");
          }
      }
        
		
//        System.out.println("Ausgabe Kundenarray");
//        for (int i = 0; i < KundenArray.size(); i++) {
//            System.out.println(KundenArray.get(i).getKundenid() + "\t" + KundenArray.get(i).getVorname() + "\t" + KundenArray.get(i).getNachname() + "\t\t" + KundenArray.get(i).getAdresse() + "\t" + KundenArray.get(i).getLaendercode() + "\t" + KundenArray.get(i).getStatus());
//        }
//        System.out.println("\nAusgabe Kontenarray");
//        for (int i = 0; i < KontenArray.size(); i++) {
//            System.out.println(KontenArray.get(i).getKundenid() + "\t" + KontenArray.get(i).getIban() + "\t" + KontenArray.get(i).getKontostand() + "\t\t" + KontenArray.get(i).getKontoart());
//        }
        
        // Silv Test Stuff
        System.out.println("Kunden");
        for(int i = 0; i< KundenArray.size();i++){
            System.out.println(KundenArray.get(i).toString());
        }
        System.out.println("Konti");
        for(int i = 0; i < KontenArray.size(); i++){
            System.out.println(KontenArray.get(i).toString());
        }
        
	}

}
