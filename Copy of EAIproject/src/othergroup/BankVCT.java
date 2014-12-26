/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othergroup;

/**
 *
 * @author Dominik & Fabio
 */
import java.sql.*;

public class BankVCT {
    static String vorname;
    static String nachname;
    static String adresse;
    static String laendercode;
    static String status;
    
    //values Konto
    static String iban;
    static float kontostand;

    /**
     * @param args the command line arguments
     */
    public BankVCT() {
       
        String url = "jdbc:mysql://localhost:3306/";    
        String user = "root";
        String password = "";
        
        try {
        
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url,user,password);
        
            Statement stt = con.createStatement();
        
            stt.execute("USE vct");
            
            //Durch die SQL Abfrage werden Firmen nicht migriert    
            ResultSet res = stt.executeQuery("SELECT * FROM account where Kundenart != 'Firma'"); 
    
     while (res.next())
            {
        
                String Kundenname = res.getString("Kundenname");
                String Strassenname = res.getString("Strassenname");
                String plz = res.getString("PLZ");
                String stadt = res.getString("Stadt");
                String land = res.getString("Land");
                String kontonummer = res.getString("Kontonummer");
                String saldo = res.getString("saldo");
//                String clearing = res.getString("Clearing");//Feedback Holgi abwarten
                
//                System.out.println(Kundenname+" "+Strassenname+ " "+plz+" "+stadt+" "+land+" "+kontonummer+" "+saldo);
                
                //Methode aufteilen Kundenname
                if(Kundenname.contains("Dr")){
                    String[] segs = Kundenname.split("\\s");
                    vorname = segs[1];
                    nachname = segs[0]+segs[2];
                }
                else{
                String[] segs = Kundenname.split("\\s");
                if(segs.length == 2) {
                    vorname = segs[0];
                    nachname = segs[1];
                }
                if(segs.length >2) {
                    vorname = segs[0];
                    nachname = segs[1]+" "+segs[2];
                }
                }
                
                //zusammenführen
                adresse = Strassenname+" ,"+plz+stadt;
                
                //laendercode erstellen
                if (land.equals("Switzerland") || land.equals("Schweiz")) {
                laendercode = "CH";
                }
                
                if(land.equals("Germany")){
                    laendercode = "DE";
                }
                if(land.equals("The Netherlands")) {
                    laendercode ="NL";
                }
                //status auf leer um in Fusion anzupassen
                status = "leer";
                
                //create IBAN
                iban = "CH" + "27" + "261000" + kontonummer;
                
                //parsed to float
                kontostand = Float.parseFloat(saldo);
                
                
                
                Fusion.KundenArray.add(new Kunde(Fusion.KIDCounter, vorname, nachname, adresse, laendercode, status));
                Fusion.KontoArray.add(new Konto(Fusion.KIDCounter, iban, kontostand, "Depotkonto"));
                Fusion.KIDCounter++;
                
                
                
            }
            System.out.println("");
            
            res.close();
            stt.close();
            con.close();
    }
    catch (Exception e) {
    e.printStackTrace();    
    }
    }
    
}
