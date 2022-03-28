package snesko;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Snesko {
    List<String> rjecnik; //Sve procitane rijeci iz rijecnika
    String dijeloviSnjeska;
    String ciljnaRijec;
    char rijec[]; //cenzurisana rijec (sa -)
    int hp;
    Snesko(){
        ciljnaRijec = "";
        rjecnik = new ArrayList<String>();
        rjecnik = procitajFajl("dictionary.txt");
        napraviSnjeska();
    }

    private void napraviSnjeska(){
        hp = 7;
        dijeloviSnjeska = """
                             _|==|_  
                              ('')___/
                          >--(`^^')
                            (`^'^'`)
                            `======'""";
    }
    
    private void istopiSnjeska(int hp){
        switch(hp){
            case 7:
                dijeloviSnjeska ="""
                                    _|==|_  
                                     ('')___/
                                 >--(`^^')
                                   (`^'^'`)
                                   `======'""";
                break;
                case 6:
                dijeloviSnjeska =
                          """  
                          
                              ('')___/
                          >--(`^^')
                            (`^'^'`)
                            `======'""";
                    break;
                case 5:
                    dijeloviSnjeska =
                          """  
                          
                              (  )___/
                          >--(`^^')
                            (`^'^'`)
                            `======'""";
                    break;
                case 4:
                    dijeloviSnjeska =
                          """ 
                          
                              (  ) 
                          >--(`^^')
                            (`^'^'`)
                            `======'""";
                    break;
                case 3:
                    dijeloviSnjeska =
                          """  
                          
                          
                          >--(`^^')
                            (`^'^'`)
                            `======'""";
                    break;
                case 2:
                    dijeloviSnjeska =
                          """  
                          
                          
                             (`^^')
                            (`^'^'`)
                            `======'""";
                    break;
                case 1:
                    dijeloviSnjeska =
                          """  
                          
                          
                                   
                            (`^'^'`)
                            `======'""";
                    break;
                default:
                    dijeloviSnjeska =
                          """
                          
                          
                          
                          
                          
                          """; 
        }
    }
    
    private void resetujIgru(){
        if(rjecnik.isEmpty()){
            System.out.println("Речник је празан! Унесите бар једну реч у њега да бисте играли.");
            return;
        }
        napraviSnjeska();
        izaberiRijec();
    }
    private int slucajanBroj(int max){
        Random rnd = new Random();
        int ret = rnd.nextInt(max + 1); //+1 zato sto nextInt vraca 0 - vrednost_parametra - 1
        return ret;
    }

    private List<String> procitajFajl(String putanja){
        List<String> retVal = new ArrayList<String>(); 
        try{
            File fajl = new File(putanja);
            Scanner citac = new Scanner(fajl);
            while(citac.hasNextLine()){
                retVal.add(citac.nextLine().toUpperCase());
            }
        } catch (Exception ex){
            System.out.println("Снег се отопио! :(\nПровери лог за грешку");
            Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retVal;
    }
    
    private void izaberiRijec(){
        int i = slucajanBroj(rjecnik.size()-1);
        ciljnaRijec = rjecnik.get(i).toUpperCase();
        rijec = new char[ciljnaRijec.length()];

        for(i = 0; i< ciljnaRijec.length(); i++){
            rijec[i] = '-';
        }
    }
    
    private void ostampajRjecnik(){
        System.out.println("Речник:");
        for(int i = 0; i < rjecnik.size(); i++){
            System.out.println(i + ". " + rjecnik.get(i));
        }
        System.out.println("\n");
    }
    
    private void dodajRijec(){
        String r;
        System.out.println("Унесите нову реч: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            r = reader.readLine().toUpperCase();
            if(r.isBlank()){
                System.out.println("Препознат је празан унос! Ништа није додато у речник!");
                return;
            }
            if(rjecnik.contains(r)){
            System.out.println("Реч већ постоји у речнику!");
            } else {
                try {
                FileWriter writer = new FileWriter("dictionary.txt", true);
                writer.write(r.toUpperCase()+"\n");
                writer.close();
                System.out.println("Реч успешно уписана у речник!");
                rjecnik.add(r.toUpperCase());
              } catch (IOException ex) {
                System.out.println("Снег се отопио! :(\nПровери лог за грешку");
                Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
        } catch (IOException ex) {
            System.out.println("Снег се отопио! :(\nПровери лог за грешку");
            Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ukloniRijec(){
        String r;
        System.out.println("Унесите реч: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            r = reader.readLine().toUpperCase();
            int i = rjecnik.lastIndexOf(r);
            if(i != -1){
                rjecnik.remove(i);
                try {
                    FileWriter writer = new FileWriter("dictionary.txt", false);
                    for(String var : rjecnik){
                        writer.write(var + "\n");
                    }
                    writer.close();
                    System.out.println("Реч успешно уклоњена из речника!");
                } catch (IOException ex) {
                    System.out.println("Снег се отопио! :(\nПровери лог за грешку");
                    Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Та реч не постоји у речнику!");
            }
        } catch (IOException ex) {
            System.out.println("Снег се отопио! :(\nПровери лог за грешку");
            Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void ostampajPravila(){
        System.out.println("Пролеће је наступило, и наш снешко се топи!\nЈедини начин да га спасите је да погодите реч из речника коју је рачунар насумично изабрао. Можете уносити по једно слово или цијелу ријеч по покушају.");
        System.out.println("Уколико унесете слово и ако је оно садржано у речи, откриће се на којим местима унутар ријечи се оно налази.\nАко тог слова нема у речи, дио снешка ће се истопити. Поновни унос већ откривених слова се не рачуна као грешка.");
        System.out.println("Уколико се ради о читавој речи, сваки погрешан покушај ће бити пропраћен озбиљним посљедицама по снешка.");
        System.out.println("Откривање свих слова, без погађања речи, такође доноси победу.\n\n");
    }

    
    public void igraj(){
        // Pokrece glavni meni
        int opcija = 9;
        while(opcija != 0){
            System.out.println("\t-= СНЕШКО =-");
            System.out.println("\t ГЛАВНИ МЕНИ\n");
            System.out.println("1. Покрени игру");
            System.out.println("2. Оштампај речник");
            System.out.println("3. Додај нову реч у речник");
            System.out.println("4. Уклони реч из речника");
            System.out.println("5. Оштампај правила игре");
            System.out.println("0. Угаси програм\n");
            System.out.println("Унесите цифру жељене опције, те притисните ЕNTER:\n");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                opcija = Integer.parseInt(reader.readLine());
            } catch (Exception ex) {
                System.out.println("Снег се отопио! :(\nПровери лог за грешку");
                Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
                opcija = 9;
            }
            
            switch(opcija){
                case 1:
                    resetujIgru();
                    if(ciljnaRijec.equals("")){
                        System.out.println("Ниједна реч није изабрана, није могуће играти! :(");
                        break;
                    }
                    System.out.println("\n\n\tИГРА ЗАПОЧЕТА\n\n");
                    boolean nijeKraj = true;
                    List<String> pokusaji = new ArrayList<String>();
                    List<String> pokusaneRijeci = new ArrayList<String>();
                    while(nijeKraj){
                        System.out.println(dijeloviSnjeska);
                        System.out.println("\n\nРеч:");
                        System.out.println("Претходни покушаји: ");
                        System.out.println(pokusaji);
                        System.out.println(pokusaneRijeci);
                        System.out.println(rijec);
                        System.out.println("Унос: ");
                        String unos = "";
                        try {
                            unos = reader.readLine();
                            unos = unos.toUpperCase();
                        } catch (IOException ex) {
                            System.out.println("Снег се отопио! :(\nПровери лог за грешку");
                            Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        if(unos.equals(ciljnaRijec)){
                            System.out.println(ciljnaRijec);
                            System.out.println("Браво! Погодили сте реч! Зимске чаролије су сачуване!");
                            if(hp<6){
                                System.out.println("(бар оно што је остало од њих...)");
                            }
                            nijeKraj = false;
                            break;
                        } else {
                            if(unos.equals("")){
                                System.out.println("Празан унос! Унесите слово или целу реч, па притисните ENTER:");
                            }else if(unos.length() == 1 && pokusaji.contains(unos)){
                                System.out.println("То слово сте већ уносили!");
                            } else if(unos.length() == 1 && ciljnaRijec.contains(unos)){
                                System.out.println("Погодак!");
                                int j = 0;
                                for(int i = 0; i < ciljnaRijec.length(); i++){
                                    if(unos.charAt(0) == ciljnaRijec.charAt(i)){
                                        rijec[i] = ciljnaRijec.charAt(i);
                                    }
                                    if(rijec[i] == '-'){
                                        j++;
                                    }
                                }
                                if(j == 0){
                                    System.out.println(ciljnaRijec);
                                    System.out.println("Браво! Погодили сте реч! Зимске чаролије су сачуване!");
                                    if(hp<6){
                                        System.out.println("(бар оно што је остало од њих...)");
                                    }
                                    nijeKraj = false;
                                    break;
                                }
                            }
                            else {
                                System.out.println("Промашај!");

                                istopiSnjeska(--hp);
                                if(hp == 0){
                                    nijeKraj = false;
                                    System.out.println("Снешко се скроз истопио! :(");
                                    System.out.println("Тражена реч је била: " + ciljnaRijec);
                                }
                            }
                            if(unos.length() == 1){
                                pokusaji.add(unos);
                            } else {
                                pokusaneRijeci.add(unos);
                            }
                        }
                    }
                    System.out.println("\n\n\tИГРА ЗАВРШЕНА\n");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException ex) {
                        System.out.println("Снег се отопио! :(\nПровери лог за грешку");
                        Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 2:
                    ostampajRjecnik();
                    break;
                case 3:
                    dodajRijec();
                    break;
                case 4:
                    ukloniRijec();
                    break;
                case 5:
                    ostampajPravila();
                    break;
                case 0:
                    System.out.println("Хвала на игрању! :)\nПрограм се гаси...");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException ex) {
                        System.out.println("Снег се отопио! :(\nПровјери лог за грешку");
                        Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    System.out.println("Непостојећа опција!");
                    opcija = 9;
            }
        }    
    }   
}   
