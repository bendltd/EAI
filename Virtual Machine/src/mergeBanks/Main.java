package mergeBanks;

import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args){
        
        System.out.println("Kunden");
        
        BankJD DataJD = new BankJD();
        for(int i = 0; i<DataJD.getKundenliste().size();i++){
            System.out.println(DataJD.getKundenliste().get(i).toString());
        }
        
        System.out.println("Konto");

        for(int i = 0; i < DataJD.getKontoliste().size(); i++){
            System.out.println(DataJD.getKontoliste().get(i).toString());
        }
    }
}
