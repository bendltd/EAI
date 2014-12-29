package mergeBanks;

import java.util.ArrayList;

public class MergeBanks {
	static ArrayList<Kunde> KundenArray = new ArrayList<Kunde>();
	static ArrayList<Konto> KontenArray = new ArrayList<Konto>();
	static int kundenidcnt = 1;
	
	public static void merge(ArrayList<Kunde> KundenArray, ArrayList<Kunde> JDKunden, ArrayList<Konto> JDKonti, int i, int j){
		// Status wird von JD �bernommen
    	KundenArray.get(j).setStatus(JDKunden.get(i).getStatus());                	
                
        // Konto wird existierendem Kunden zugeteilt
        int id = KundenArray.get(j).getKundenid();
        int idJD = JDKunden.get(i).getKundenid();
        JDKonti.get(i).setKundenid(id);
        // Schlaufe durch JDKonti
        for(int a = 0; a < JDKonti.size(); a++){
        	if(JDKonti.get(a).getKundenid() == idJD){
        		JDKonti.get(a).setKundenid(id);
                KontenArray.add(JDKonti.get(a));
        	}
        }
	}
	

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
            	
            	String[] adr1 = KundenArray.get(j).getAdresse().split("\\s");
            	String[] adr2 = JDKunden.get(i).getAdresse().split("\\s");
            	String adresseneu = new String();                
                if(adr1.length > adr2.length){
                	adresseneu = KundenArray.get(j).getAdresse();
                }
                else{
                	adresseneu = JDKunden.get(i).getAdresse();
                }
            	
            	// Falls Vorname, Nachname & komplette Adresse �bereinstimmt wird gemerged
                if(JDKunden.get(i).getVorname().equals(KundenArray.get(j).getVorname()) &&
                	JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname()) &&
                	JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse())){
                	
//                	// Status wird von JD �bernommen
//                	KundenArray.get(j).setStatus(JDKunden.get(i).getStatus());                	
//                    neu = false;
//                    
//                    // Konto wird existierendem Kunden zugeteilt
//                    int id = KundenArray.get(j).getKundenid();
//                    int idJD = JDKunden.get(i).getKundenid();
//                    JDKonti.get(i).setKundenid(id);
//                    // Schlaufe durch JDKonti
//                    for(int a = 0; a < JDKonti.size(); a++){
//                    	if(JDKonti.get(a).getKundenid() == idJD){
//                    		JDKonti.get(a).setKundenid(id);
//                            KontenArray.add(JDKonti.get(a));
//                    	}
//                    }
                	neu = false;
                	merge(KundenArray, JDKunden, JDKonti, i, j);
                    break;
                }
                
                // Bei Kunden mit Initialen als Vornamen
//                else if(JDKunden.get(i).getVorname().substring(1, 1) == "." || KundenArray.get(j).getVorname().substring(1, 1) == "."){
//                	String initalJD = JDKunden.get(i).getVorname().substring(0, 0);
//                	String initialVCT = KundenArray.get(j).getVorname().substring(0, 0);
//                	
//                	System.out.println("Initial 1. If-Schlaufe klappt");
//                	
//                	// Falls Initialen, Nachname & komplette Adresse �bereinstimmt wird gemerged
//                	if(initalJD.equals(initialVCT) &&
//                        JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname()) &&
//                        JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse())){
//                		
//                		System.out.println("Initial 2. If-Schlaufe klappt");
//                		
//                		// Voller Vorname wird bestimmt
//                		String name = new String();
//                		if(JDKunden.get(i).getVorname().length() > KundenArray.get(j).getVorname().length()){
//                			name = JDKunden.get(i).getVorname();
//                			
//                			System.out.println("Initial 3. If-Schlaufe klappt");
//                		}
//                		else{
//                			name = KundenArray.get(j).getVorname();
//                			
//                			System.out.println("Initial 3. If-Schlaufe klappt (else)");
//                		}
//                		neu = false;
//                    	merge(KundenArray, JDKunden, JDKonti, i, j);
//                    	KundenArray.get(j).setVorname(name);
//                        break;
//                	}
//                	
//                }
                
                // Falls Vorname, Nachname & Strassenname �bereinstimmt wird gemerged, aber eine Meldung geworfen
                else if(JDKunden.get(i).getVorname().equals(KundenArray.get(j).getVorname()) &&
                    	JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname()) &&
                    	adr1[0].replace(",","").equals(adr2[0].replace(",", "")) &&
                    	adr1[adr1.length-1].equals(adr2[adr2.length-1])){
                    	
//                    	// Status wird von JD �bernommen
//                    	KundenArray.get(j).setStatus(JDKunden.get(i).getStatus());                	
//                        neu = false;
//                        
//                        // Konto wird existierendem Kunden zugeteilt
//                        int id = KundenArray.get(j).getKundenid();
//                        int idJD = JDKunden.get(i).getKundenid();
//                        JDKonti.get(i).setKundenid(id);
//                        KundenArray.get(j).setFehler("Korrekte Hausnummer bei Kunde erfragen");
//                        KundenArray.get(j).setAdresse(adresseneu);
//                        // Schlaufe durch JDKonti
//                        for(int a = 0; a < JDKonti.size(); a++){
//                        	if(JDKonti.get(a).getKundenid() == idJD){
//                        		JDKonti.get(a).setKundenid(id);
//                                KontenArray.add(JDKonti.get(a));
//                        	}
//                        }
                	neu = false;
                	merge(KundenArray, JDKunden, JDKonti, i, j);
                	KundenArray.get(j).setFehler("Korrekte Hausnummer bei Kunde erfragen");
                	KundenArray.get(j).setAdresse(adresseneu);
                    break;
                    }
                
                // Falls von den Attributen Vorname, Nachname und Adresse zwei übereinstimmen, wird eine Meldung geworfen, dass evtl. der Kunde mehrmals erfasst wurde
                else if((JDKunden.get(i).getVorname().equals(KundenArray.get(j).getVorname()) && JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname())) || 
                        (JDKunden.get(i).getVorname().equals(KundenArray.get(j).getVorname()) && JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse())) ||
                        (JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse()) && JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname()))){
                    JDKunden.get(i).setFehler("M\u00F6glicherweise mehrfach vorhanden");
                    break;
                }
                
                // Falls Vor- und Nachname in umgekehrter Reihenfolge vorkommen und die Adresse stimmt, wird nicht gemerged, aber eine Meldung ausgegeben
                else if(JDKunden.get(i).getVorname().equals(KundenArray.get(j).getNachname()) &&
                		JDKunden.get(i).getNachname().equals(KundenArray.get(j).getVorname()) &&
                		JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse())){
                	JDKunden.get(i).setFehler("Vor- und Nachname m\u00F6glicherweise vertauscht");
                	break;
                }
            }
            
            // Falls Kunde nicht bereits vorhanden ist, werden Kunde & Konto nun in die Liste eingefügt
            if(neu){
            	int idJD = JDKunden.get(i).getKundenid();
                JDKunden.get(i).setKundenid(kundenidcnt);
                KundenArray.add(JDKunden.get(i));
                for(int a = 0; a < JDKonti.size(); a++){
                	if(JDKonti.get(a).getKundenid() == idJD){
                		JDKonti.get(a).setKundenid(kundenidcnt);
                        KontenArray.add(JDKonti.get(a));
                	}
                }
                kundenidcnt++;
            }
        }
            	
        // Status erfassen falls noch nicht vorhanden (nach Gesamtverm�gen)
        for(int i = 0; i < KundenArray.size(); i++){
        	float total = 0;
        	if(KundenArray.get(i).getStatus() == null){
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
