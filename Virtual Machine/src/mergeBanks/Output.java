package mergeBanks;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Output {

	// Textfile erstellen und beschreiben
	public static void createTextFile(ArrayList<Kunde> Kunden, ArrayList<Konto> Konten){
		
		try{
		PrintWriter writer = new PrintWriter("MergeJD&VCT.txt", "UTF-8");
		writer.println("Daten von BankJD und BankVCT");
		writer.println("Kunden");
		for(int i = 0; i < Kunden.size(); i++){
			writer.println(Kunden.get(i).toString());
		}
		writer.println();
		writer.println("Konten");
		for(int i = 0; i < Konten.size(); i++){
			writer.println(Konten.get(i).toString());
		}
		writer.close();
		System.out.println("Ein Textfile wurde in der Working Directory erstellt:");
		System.out.println(System.getProperty("user.dir")+"\\MergeJD&VCT.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	// Ausgabe auf Console
	public static void createConsoleOutput(ArrayList<Kunde> Kunden, ArrayList<Konto> Konten){
		System.out.println("Daten von BankJD und BankVCT");
		System.out.println("Kunden");
        for(int i = 0; i< Kunden.size();i++){
            System.out.println(Kunden.get(i).toString());
        }
        System.out.println("Konti");
        for(int i = 0; i < Konten.size(); i++){
            System.out.println(Konten.get(i).toString());
        }
	}
}
