import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Main class
 */
public class PPA {
    static String  MENU = "--- MENU ---\n"+
            "1. Podaj liczby dziesietnie\n"+
            "2. Podaj liczby binarnie\n"+
            "3. Pokaz graf\n"+
            "4. O programie\n"+
            "5. Autor\n"+
            "0. Wyjdz z programu\n";
    static String ABOUT_APP = "Program sluzy do symulacji dzialania sumatora prefiksowego z grafem typu Sklansky'ego lub Kogge&Stone'a.\n";
    static String AUTHOR = "Lukasz Bieszczad\nPolitechnika Wroclawska\nkontakt: luk.biesz@gmail.com\n";

    public static void main(String ... args)
    {
        Adder adder = new Adder("0", "0", false);;
        Scanner in = new Scanner(System.in);
        int firstNumber = 0;
        int secondNumber = 0;
        int typeChoice = 0;
        String firstNumberBinaryString;
        String secondNumberBinaryString;
        int choice = -1;
        boolean type = false;
        System.out.println("Symulacja sumatora PPA");
        do {
            System.out.print(MENU);
            System.out.print(">>");
            try{
                choice = in.nextInt();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            switch (choice){
                case 1:
                    System.out.print("Podaj pierwsza liczbe dziesietnie: ");
                    try {
                        firstNumber = in.nextInt();
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    System.out.print("Podaj druga liczbe dziesietnie: ");
                    try {
                        secondNumber = in.nextInt();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    System.out.print("Podaj typ grafu (0 - Kogge&Stonne, 1 - Sklansky): ");
                    try {
                        typeChoice = in.nextInt();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    type = typeChoice != 0;
                    adder = new Adder(Integer.toBinaryString(firstNumber), Integer.toBinaryString(secondNumber), type);
                    break;
                case 2:
                    System.out.print("Podaj pierwsza liczbe binarnie: ");
                    firstNumberBinaryString = in.next();
                    System.out.print("Podaj druga liczbe binarnie: ");
                    secondNumberBinaryString = in.next();
                    if (!checkData(firstNumberBinaryString) || !checkData(secondNumberBinaryString)){
                        System.out.println("Niepoprawne dane");
                        break;
                    }
                    System.out.print("Podaj typ grafu (0 - Kogge&Stonne, 1 - Sklansky): ");
                    try {
                        typeChoice = in.nextInt();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    type = typeChoice != 0;
                    adder = new Adder(firstNumberBinaryString, secondNumberBinaryString, type);
                    break;
                case 3:
                    adder.calculate();
                    try {
                        TimeUnit.MILLISECONDS.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    adder.showGraph();
                    break;
                case 4:
                    System.out.print(ABOUT_APP);
                    break;
                case 5:
                    System.out.print(AUTHOR);
                    break;
                default:
                    System.out.println("Niepoprawny wybor");
                    break;
            }
        } while (choice != 0);
    }

    /**
     * Method checks if given String consists of only '0' and '1'
     * @param data String of binnary number
     * @return true if there are only '0' and '1' in data otherwise false
     */
    private static boolean checkData(String data){
        boolean ans = true;
        for (int i=0; i<data.length(); i++)
            ans &= (data.charAt(i)=='0' || data.charAt(i) == '1');
        return  ans;
    }
}
