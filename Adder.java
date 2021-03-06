/**
 * Class represents whole adder with data and calculations
 */
public class Adder{
    private Data data;
    private Line[] line;
    private CarryDigit carryDigit;
    private boolean[] output;
    private boolean schemeType;
    int[][] scheme;
    int linesNumber;

    Adder(String firstNumber, String secondNumber, boolean schemeType){
        int max;
        if (firstNumber.length()>secondNumber.length()){
            max = firstNumber.length();
            secondNumber = fixLength(secondNumber, max - secondNumber.length());
        }
        else if (firstNumber.length()<secondNumber.length()){
            max = secondNumber.length();
            firstNumber = fixLength(firstNumber, max - firstNumber.length());
        } else {
            max = secondNumber.length();
        }
        data = new Data(firstNumber, secondNumber);
        this.schemeType = schemeType;
        if (!this.schemeType)
            scheme = data.generateKoggeStone(data.getWidth(), data.getHeight());
        else
            scheme = data.generateSklansky(data.getWidth(), data.getHeight());
        line = new Line[max];
        linesNumber = max;
        for (int i=0; i<linesNumber; i++){
            line[i] = new Line(data, i, scheme);
        }
        carryDigit = new CarryDigit(data);
        this.output = data.getOutput();

    }

    /**
     * Add '0' to a left side of word
     * @param word String to be fixed
     * @param paddingSize number of '0' to be added
     * @return String with added '0'
     */
    private String fixLength(String word, int paddingSize){
        String w = word;
        for (int i=0; i<paddingSize; i++){
            w = '0'+w;
        }
        return w;
    }

    /**
     * Start threads calculating output
     */
    public void calculate(){
        for (int i=0; i<linesNumber; i++){
            line[i].start();
        }
        carryDigit.start();
    }

    public String getOutput(){
        String str = "";
        for (boolean bit: output){
            if (bit == false){
                str = '0'+str;
            } else if (bit == true){
                str = '1'+str;
            }
        }
        return str;
    }

    /**
     * Display visualisation of a whole adder
     */
    public void showGraph(){
        data.showGenerators();
        data.showGraph();
        data.showOutput();
        System.out.println();
    }
}