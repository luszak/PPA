/**
 * Class represents whole data given to adder
 */
public class Data {
    private String firstNumber, secondNumber;
    private int width, height;
    private boolean[][] generations;
    private boolean[][] propagations;
    private boolean[] halfsums;
    private boolean[][] set;
    private boolean[] outputSet;
    private boolean[] output;

    Data(String firstNumber, String secondNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.width = firstNumber.length();
        if (this.width < secondNumber.length())
            this.width = secondNumber.length();
        height = (int) Math.ceil((Math.log10(width) / Math.log10(2)));
        generations = new boolean[height + 1][width];
        propagations = new boolean[height + 1][width];
        halfsums = new boolean[width];
        set = new boolean[height + 1][width];
        outputSet = new boolean[width + 1];
        output = new boolean[width + 1];
        for (int i = 0; i < height + 1; i++) {
            for (int j = 0; j < width; j++) {
                generations[i][j] = false;
                propagations[i][j] = false;
                set[i][j] = false;
            }
            halfsums[i] = false;
        }
        for (int i = width-1; i>=0; i--) {
            boolean x, y;
            if (firstNumber.charAt(i) == '0')
                x = false;
            else
                x = true;
            if (secondNumber.charAt(i) == '0')
                y = false;
            else
                y = true;
            generations[0][width-1-i] = x & y;
            propagations[0][width-1-i] = x | y;
            halfsums[width-1-i] = x ^ y;
            set[0][width-1-i] = true;
        }

        for (int i = 0; i < width + 1; i++) {
            output[i] = false;
            outputSet[i] = false;
        }
    }

    public int getHeight(){
        return height;
    }
    public int getWidth() { return width;}
    public boolean[] getOutput() {return output;}

    /**
     * Returns generation
     * @param i row
     * @param j column
     * @return generation if set
     */
    public synchronized boolean getGeneration(int i, int j){
        while(!set[i][j]){
            try{
                wait();
            } catch (InterruptedException e){}
        }
        notifyAll();
        return generations[i][j];
    }

    /**
     * Returns propagation
     * @param i row
     * @param j column
     * @return propagation if set
     */
    public synchronized boolean getPropagation(int i, int j){
        while(!set[i][j]){
            try{
                wait();
            } catch (InterruptedException e){}
        }
        notifyAll();
        return propagations[i][j];
    }

    /**
     * Set a value of generation and propagation
     * @param generation value of generation
     * @param propagation value of propagation
     * @param i row
     * @param j column
     */
    public synchronized void setGenerationAndPropagation(boolean generation, boolean propagation,int i, int j){
        generations[i][j] = generation;
        propagations[i][j] = propagation;
        set[i][j] = true;
        notifyAll();
    }

    /**
     * Set a value of output digit
     * @param value value of digit
     * @param i index of digit
     */
    public synchronized void setOutput(boolean value, int i){
        output[i] = value;
        outputSet[i] = true;
    }

    public synchronized boolean getHalfsum(int i){
        notifyAll();
        return halfsums[i];
    }

    /**
     * Set carry digit
     * @param value value of carry digit
     */
    public synchronized void setCarry(boolean value) {
        output[width] = value;
        outputSet[width] = true;
        notifyAll();
    }

    /**
     * Display visualisation of generators
     */
    public void showGenerators(){
        char c;
        System.out.print("     ");
        for (int i=0; i<width; i++){
            System.out.print("  ");
            System.out.print(firstNumber.charAt(i));
            System.out.print(" ");
            System.out.print(secondNumber.charAt(i));
            System.out.print(" ");
        }
        System.out.println();
        System.out.print("     ");
        for (int i=0; i<width; i++){
            System.out.print("  | | ");
        }
        System.out.println();
        System.out.print("     ");
        for (int i=0; i<width; i++){
            System.out.print("######");
        }
        System.out.println("#");
        System.out.print("     ");
        for (int i=width-1; i>=0;i--){
            c = generations[0][i]? '1' : '0';
            System.out.print("# g=");
            System.out.print(c);
            System.out.print(" ");
        }
        System.out.println("#");
        System.out.print("     ");
        for (int i=width-1; i>=0;i--){
            c = propagations[0][i]? '1' : '0';
            System.out.print("# p=");
            System.out.print(c);
            System.out.print(" ");
        }
        System.out.println("#");
        System.out.print("     ");
        for (int i=width-1; i>=0;i--){
            c = halfsums[i]? '1' : '0';
            System.out.print("# h=");
            System.out.print(c);
            System.out.print(" ");
        }
        System.out.println("#");
    }

    /**
     * Display visualisation of graph
     */
    public void showGraph(){
        char c;
        for (int i=1; i<=height; i++){
            System.out.print("     ");
            for (int j=width-1; j>=0; j--){
                System.out.print("######");
            }
            System.out.println("#");
            System.out.print("     ");
            for (int j=width-1; j>=0; j--){
                c = generations[i][j]? '1' : '0';
                System.out.print("# g=");
                System.out.print(c);
                System.out.print(" ");
            }
            System.out.println("#");
            System.out.print("     ");
            for (int j=width-1; j>=0;j--){
                c = propagations[i][j]? '1' : '0';
                System.out.print("# p=");
                System.out.print(c);
                System.out.print(" ");
            }
            System.out.println("#");
        }
    }

    /**
     * Display visualisation of output
     */
    public void showOutput(){
        char c;
        System.out.print("     ");
        for (int i=0; i<width; i++){
            System.out.print("######");
        }
        System.out.println("#");
        System.out.print("    / ");
        for (int i=width-1; i>=0; i--){
            System.out.print("  |   ");
        }
        System.out.println();
        for (int i=width; i>=0; i--){
            c = output[i] ? '1' : '0';
            System.out.print("  ");
            System.out.print(c);
            System.out.print("   ");
        }
    }

    /**
     * Generate Kogge&Stone scheme
     * @param width width of graph
     * @param height height of graph
     * @return Kogge&Stone scheme used by Adder
     */
    public int[][] generateKoggeStone(int width, int height){
        int[][] scheme = new int[height+1][width];
        int counter, value;
        for (int i=0; i<height;i++){
            counter = (int)Math.pow(2,i);
            for (int j=0; j<counter; j++){
                scheme[i][j] = -1;
            }
            value = 0;
            for (int j=counter; j<width; j++){
                scheme[i][j] = value;
                value++;
            }
        }
        for (int i=0; i<width; i++){
            scheme[height][i]=-1;
        }
        return scheme;
    }

    /**
     * Generate Sklansky scheme
     * @param width width of graph
     * @param height height of graph
     * @return Sklansky scheme used by Adder
     */
    public int[][] generateSklansky(int width, int height){
        int[][] scheme = new int[height+1][width];
        int counter, value=0, index;
        for (int i=0; i<height; i++){
            counter = (int)Math.pow(2,i);
            index = 0;
            for (;index<width;){
                for (int j=0; j<counter;j++){
                    if (index >= width)
                        break;
                    scheme[i][index] = -1;
                    value = index;
                    index++;
                }
                for (int l=0; l<counter;l++){
                    if (index >= width)
                        break;
                    scheme[i][index] = value;
                    index++;
                }
            }
        }
        for (int i=0; i<width; i++){
            scheme[height][i]=-1;
        }
        return scheme;
    }
}