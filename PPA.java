import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PPA {
    public static void main(String ... args)
    {
        Adder adder;
        Scanner in = new Scanner(System.in);
        int firstNumber = 0;
        int secondNumber = 0;
        String firstNumberBinaryString;
        String secondNumberBinaryString;
        System.out.print("Podaj pierwsza liczbe:");
        try {
            firstNumber = in.nextInt();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.print("Podaj druga liczbe: ");
        try {
            secondNumber = in.nextInt();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        firstNumberBinaryString = Integer.toBinaryString(firstNumber);
        secondNumberBinaryString = Integer.toBinaryString(secondNumber);
        //System.out.println(firstNumberBinaryString);
        //System.out.println(secondNumberBinaryString);

        adder = new Adder(firstNumberBinaryString,secondNumberBinaryString, false);
        adder.calculate();
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        adder.showGraph();

        //System.out.print(adder.getOutput());
    }
}
