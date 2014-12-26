/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package othergroup;

import java.util.ArrayList;


/**
 *
 * @author eai
 */
public class Fusion {
    static ArrayList<Kunde> KundenArray = new ArrayList();
    static ArrayList<Konto> KontoArray = new ArrayList();
    static ArrayList<Integer> ErrorArray = new ArrayList();
    static int KIDCounter = 1;
    
    public static void main(String[] args) {
        BankJD jd = new BankJD();
        BankVCT vct = new BankVCT();
        System.out.println();
        System.out.println("Ausgabe Kundenarray");
        for (int i = 0; i < KundenArray.size(); i++) {
            System.out.println(KundenArray.get(i).getKid() + "\t" + KundenArray.get(i).getVorname() + "\t" + KundenArray.get(i).getNachname() + "\t" + KundenArray.get(i).getAdresse() + "\t" + KundenArray.get(i).getLaendercode() + "\t" + KundenArray.get(i).getStatus());
        }
        System.out.println("Ausgabe Kundenarray");
        for (int i = 0; i < KontoArray.size(); i++) {
            System.out.println(KontoArray.get(i).getIban());
        }
        
        
    }
    
    
}
