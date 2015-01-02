package mergeBanks;

import java.util.ArrayList;

public class MergeBanks {
	static ArrayList<Kunde> KundenArray = new ArrayList<Kunde>();
	static ArrayList<Konto> KontenArray = new ArrayList<Konto>();
	static int kundenidcnt = 1;
	
	public static void merge(ArrayList<Kunde> KundenArray, ArrayList<Kunde> JDKunden, ArrayList<Konto> JDKonten, int i, int j){
		// Status wird von JD übernommen
    	KundenArray.get(j).setStatus(JDKunden.get(i).getStatus());                	
                
        // Konto wird existierendem Kunden zugeteilt
        int id = KundenArray.get(j).getKundenid();
        int idJD = JDKunden.get(i).getKundenid();
        // Schlaufe durch JDKonti
        for(int a = 0; a < JDKonten.size(); a++){
        	if(JDKonten.get(a).getKundenid() == idJD){
        		Konto ko = JDKonten.get(a);
        		Konto konto = new Konto(ko.getKundenid(), ko.getIban(), ko.getKontostand(), ko.getKontoart());
        		konto.getFehler().addAll(ko.getFehler());
                KontenArray.add(konto);
                KontenArray.get(KontenArray.size()-1).setKundenid(id);
        	}
        }
        KundenArray.get(j).getFehler().addAll(JDKunden.get(i).getFehler());
	}
	

	public static void main(String[] args) {
		
		// Laden der VCT Daten
		BankVCT.getVCTDatas();
		// Laden der JD Daten
        BankJD DataJD = new BankJD();
        ArrayList<Kunde> JDKunden = DataJD.getKundenliste();
        ArrayList<Konto> JDKonten = DataJD.getKontoliste();
        
        
        // Before Merge
//        System.out.println("Kunden VCT");
//        for(int i = 0; i< KundenArray.size();i++){
//            System.out.println(KundenArray.get(i).toString());
//        }
//        System.out.println("Konti VCT");
//        for(int i = 0; i < KontenArray.size(); i++){
//            System.out.println(KontenArray.get(i).toString());
//        }
//        System.out.println("Kunden JD");
//        for(int i = 0; i< JDKunden.size();i++){
//            System.out.println(JDKunden.get(i).toString());
//        }
//        System.out.println("Konten JD");
//        for(int i = 0; i < JDKonten.size(); i++){
//            System.out.println(JDKonten.get(i).toString());
//        }
        
        
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
            	
            	// Falls Vorname, Nachname & komplette Adresse übereinstimmt wird gemerged
                if(JDKunden.get(i).getVorname().equals(KundenArray.get(j).getVorname()) &&
                	JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname()) &&
                	JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse())){
                	
                	neu = false;
                	merge(KundenArray, JDKunden, JDKonten, i, j);
                    break;
                }
                
                // Falls Vorname, Nachname & Strassenname übereinstimmt wird gemerged, aber eine Meldung geworfen
                else if(JDKunden.get(i).getVorname().equals(KundenArray.get(j).getVorname()) &&
                    	JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname()) &&
                    	adr1[0].replace(",","").equals(adr2[0].replace(",", "")) &&
                    	adr1[adr1.length-1].equals(adr2[adr2.length-1])){
                	
                	neu = false;
                	merge(KundenArray, JDKunden, JDKonten, i, j);
                	KundenArray.get(j).setFehler("Korrekte Hausnummer bei Kunde erfragen");
                	KundenArray.get(j).setAdresse(adresseneu);
                    break;
                }
                
                // Falls Vor- und Nachname in umgekehrter Reihenfolge vorkommen und die Adresse stimmt, wird gemerged und eine Meldung ausgegeben
                else if(JDKunden.get(i).getVorname().equals(KundenArray.get(j).getNachname()) &&
                		JDKunden.get(i).getNachname().equals(KundenArray.get(j).getVorname()) &&
                		JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse())){
                	neu = false;
                	merge(KundenArray, JDKunden, JDKonten, i, j);
                	KundenArray.get(j).setFehler("Vor- und Nachname m\u00F6glicherweise vertauscht");
                    break;
                }
                
                // Falls von den Attributen Vorname, Nachname und Adresse zwei Ã¼bereinstimmen, wird eine Meldung geworfen, dass evtl. der Kunde mehrmals erfasst wurde
//                else if((JDKunden.get(i).getVorname().equals(KundenArray.get(j).getVorname()) && JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname())) || 
//                        (JDKunden.get(i).getVorname().equals(KundenArray.get(j).getVorname()) && JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse())) ||
//                        (JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse()) && JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname()))){
//                    JDKunden.get(i).setFehler("M\u00F6glicherweise mehrfach vorhanden");
//                    break;
//                }
                
                // Bei Kunden mit Initialen als Vornamen
                else if(JDKunden.get(i).getVorname().charAt(1) == '.' || KundenArray.get(j).getVorname().charAt(1) == '.'){
                	String initalJD = JDKunden.get(i).getVorname().substring(0, 1);
                	String initialVCT = KundenArray.get(j).getVorname().substring(0, 1);
                	
                	// Falls Initialen, Nachname & komplette Adresse übereinstimmt wird gemerged
                	if(initalJD.equals(initialVCT) &&
                        JDKunden.get(i).getNachname().equals(KundenArray.get(j).getNachname()) &&
                        JDKunden.get(i).getAdresse().equals(KundenArray.get(j).getAdresse())){
                		
                		// Voller Vorname wird bestimmt
                		String name = new String();
                		if(JDKunden.get(i).getVorname().length() > KundenArray.get(j).getVorname().length()){
                			name = JDKunden.get(i).getVorname();
                		}
                		else{
                			name = KundenArray.get(j).getVorname();
                		}
                		neu = false;
                    	merge(KundenArray, JDKunden, JDKonten, i, j);
                    	KundenArray.get(j).setVorname(name);
                        break;
                	}
                	
                }
                
            }
            
            // Falls Kunde nicht bereits vorhanden ist, werden Kunde & Konto nun in die Liste eingefÃ¼gt
            if(neu){
            	int idJD = JDKunden.get(i).getKundenid();
                for(int a = 0; a < JDKonten.size(); a++){
                	if(JDKonten.get(a).getKundenid() == idJD){
                		Konto ko = JDKonten.get(a);
                		Konto konto = new Konto(kundenidcnt, ko.getIban(), ko.getKontostand(), ko.getKontoart());
                		konto.getFehler().addAll(ko.getFehler());
                        KontenArray.add(konto);
//                        KontenArray.get(KontenArray.size()-1).setKundenid(kundenidcnt);
                        
                	}
                }
//                JDKunden.get(i).setKundenid(kundenidcnt);
                Kunde ku = JDKunden.get(i);
                Kunde kunde = new Kunde(kundenidcnt, ku.getVorname(), ku.getNachname(), ku.getAdresse(), ku.getLaendercode(), ku.getStatus());
                kunde.getFehler().addAll(ku.getFehler());
                KundenArray.add(kunde);
//                KundenArray.get(KundenArray.size()-1).setKundenid(kundenidcnt);
                kundenidcnt++;
            }
        }
            	
        // Status erfassen falls noch nicht vorhanden (nach Gesamtvermögen)
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
       
       // Ausgabe auf Console und in Textfile
       Output.createConsoleOutput(KundenArray, KontenArray);
       System.out.println();
       Output.createTextFile(KundenArray, KontenArray);
      
       //Test nach Merge
     System.out.println("Kunden JD");
     for(int i = 0; i< JDKunden.size();i++){
         System.out.println(JDKunden.get(i).toString());
     }
     System.out.println("Konten JD");
     for(int i = 0; i < JDKonten.size(); i++){
         System.out.println(JDKonten.get(i).toString());
     }
       
       
	}

}
