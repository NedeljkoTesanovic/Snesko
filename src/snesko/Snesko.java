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
            System.out.println("Рјечник је празан! Унесите бар једну ријеч у њега да бисте играли.");
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
                retVal.add(citac.nextLine());
            }
        } catch (Exception ex){
            System.out.println("Снијег се отопио! :(\nПровјери лог за грешку");
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
        System.out.println("Рјечник:");
        for(int i = 0; i < rjecnik.size(); i++){
            System.out.println(i + ". " + rjecnik.get(i));
        }
        System.out.println("\n");
    }
    
    private void dodajRijec(){
        String r;
        System.out.println("Унесите нову ријеч: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            r = reader.readLine();
            if(rjecnik.contains(r)){
            System.out.println("Ријеч већ постоји у рјечнику!");
            } else {
                try {
                FileWriter writer = new FileWriter("dictionary.txt", true);
                writer.write(r+"\n");
                writer.close();
                System.out.println("Ријеч успјешно уписана у ријечник!");
                rjecnik.add(r);
              } catch (IOException ex) {
                System.out.println("Снијег се отопио! :(\nПровјери лог за грешку");
                Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
        } catch (IOException ex) {
            System.out.println("Снијег се отопио! :(\nПровјери лог за грешку");
            Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ukloniRijec(){
        String r;
        System.out.println("Унесите ријеч: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            r = reader.readLine();
            int i = rjecnik.lastIndexOf(r);
            if(i != -1){
                rjecnik.remove(i);
                try {
                    FileWriter writer = new FileWriter("dictionary.txt", false);
                    for(String var : rjecnik){
                        writer.write(var + "\n");
                    }
                    writer.close();
                    System.out.println("Ријеч успјешно уклоњена из ријечника!");
                } catch (IOException ex) {
                    System.out.println("Снијег се отопио! :(\nПровјери лог за грешку");
                    Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Та ријеч не постоји у рјечнику!");
            }
        } catch (IOException ex) {
            System.out.println("Снијег се отопио! :(\nПровјери лог за грешку");
            Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void ostampajPravila(){
        System.out.println("Прољеће је наступило, и наш сњешко се топи!\nЈедини начин да га спасите је да погодите ријеч из рјечника коју је рачунар насумично изабрао. Можете унијети једно слово или цијелу ријеч.");
        System.out.println("Уколико унесете слово и ако је оно садржано у ријечи, откриће се на којим мјестима унутар ријечи се оно налази.\nАко тог слова нема у ријечи, дио сњешка ће се истопити. Поновни унос већ откривених слова се не рачуна као грешка.");
        System.out.println("Уколико се ради о читавој ријечи, сваки погрешан покушај ће бити пропраћен озбиљним посљедицама по сњешка.");
        System.out.println("Откривање свих слова, без погађања ријечи, такође доноси побједу.\n\n");
    }

    
    public void igraj(){
        // Pokrece glavni meni
        int opcija = 9;
        while(opcija != 0){
            System.out.println("\t-= СЊЕШКО =-");
            System.out.println(" (Снешко - Ијекавица едишн)\n\n");
            System.out.println("\t ГЛАВНИ МЕНИ\n");
            System.out.println("1. Покрени игру");
            System.out.println("2. Оштампај рјечник");
            System.out.println("3. Додај нову ријеч у рјечник");
            System.out.println("4. Уклони ријеч из рјечника");
            System.out.println("5. Оштампај правила игре");
            System.out.println("0. Угаси програм\n");
            System.out.println("Унесите цифру жељене опције, те притисните ЕNTER:\n");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                opcija = Integer.parseInt(reader.readLine());
            } catch (Exception ex) {
                System.out.println("Снијег се отопио! :(\nПровјери лог за грешку");
                Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            switch(opcija){
                case 1:
                    resetujIgru();
                    if(ciljnaRijec.equals("")){
                        System.out.println("Ниједна ријеч није изабрана, није могуће играти! :(");
                        break;
                    }
                    System.out.println("\n\n\tИГРА ЗАПОЧЕТА\n\n");
                    boolean nijeKraj = true;
                    List<String> pokusaji = new ArrayList<String>();
                    List<String> pokusaneRijeci = new ArrayList<String>();
                    while(nijeKraj){
                        System.out.println(dijeloviSnjeska);
                        System.out.println("\n\nРијеч:");
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
                            System.out.println("Снијег се отопио! :(\nПровјери лог за грешку");
                            Logger.getLogger(Snesko.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        if(unos.equals(ciljnaRijec)){
                            System.out.println("Браво! Погодили сте ријеч! Зимске чаролије су сачуване!");
                            if(hp<6){
                                System.out.println("(бар оно што је остало од њих...)");
                            }
                            nijeKraj = false;
                            break;
                        } else {
                            if(unos.length() == 1 && pokusaji.contains(unos)){
                                System.out.println("То слово сте већ уносили!");
                            } else if(unos.length() == 1 && ciljnaRijec.contains(unos)){
                                System.out.println("Погодак!");
                                for(int i = 0; i < ciljnaRijec.length(); i++){
                                    if(unos.charAt(0) == ciljnaRijec.charAt(i)){
                                        rijec[i] = ciljnaRijec.charAt(i);
                                    }
                                }
                            }
                            else {
                                System.out.println("Промашај!");

                                istopiSnjeska(--hp);
                                if(hp == 0){
                                    nijeKraj = false;
                                    System.out.println("Сњешко се скроз истопио! :(");
                                    System.out.println("Тражена ријеч је била: " + ciljnaRijec);
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
                        System.out.println("Снијег се отопио! :(\nПровјери лог за грешку");
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
                        System.out.println("Снијег се отопио! :(\nПровјери лог за грешку");
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
