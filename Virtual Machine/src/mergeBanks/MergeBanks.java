package mergeBanks;

import java.util.ArrayList;

public class MergeBanks {
	static ArrayList<Kunde> KundenArray = new ArrayList<Kunde>();
	static ArrayList<Konto> KontenArray = new ArrayList<Konto>();
	static int kundenidcnt = 1;
	

	public static void main(String[] args) {
		
		// Silv Test Stuff
		System.out.println("JD Kunden");
        
        BankJD DataJD = new BankJD();
        for(int i = 0; i<DataJD.getKundenliste().size();i++){
            System.out.println(DataJD.getKundenliste().get(i).toString());
        }
        
        System.out.println("JD Konti");

        for(int i = 0; i < DataJD.getKontoliste().size(); i++){
            System.out.println(DataJD.getKontoliste().get(i).toString());
        }
		
		
		VCTBankDBConnection.getVCTDatas();
		
        System.out.println("Ausgabe Kundenarray");
        for (int i = 0; i < KundenArray.size(); i++) {
            System.out.println(KundenArray.get(i).getKundenid() + "\t" + KundenArray.get(i).getVorname() + "\t" + KundenArray.get(i).getNachname() + "\t\t" + KundenArray.get(i).getAdresse() + "\t" + KundenArray.get(i).getLaendercode() + "\t" + KundenArray.get(i).getStatus());
        }
        System.out.println("\nAusgabe Kontenarray");
        for (int i = 0; i < KontenArray.size(); i++) {
            System.out.println(KontenArray.get(i).getIban() + "\t" + KontenArray.get(i).getKontostand() + "\t\t" + KontenArray.get(i).getKontoart());
        }
	}

}
