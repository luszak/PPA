public class Data {
    final int[][] SklanskyNodesScheme = new int[][]{
            new int[]{-1,0,-1,2,-1,4,-1,6,-1,8,-1,10,-1,12,-1,14},
            new int[]{-1,-1,1,1,-1,-1,5,5,-1,-1,9,9,-1,-1,13,13},
            new int[]{-1,-1,-1,-1,3,3,3,3,-1,-1,-1,-1,11,11,11,11},
            new int[]{-1,-1,-1,-1,-1,-1,-1,-1,7,7,7,7,7,7,7,7},
            new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
    };

    final int[][] KoggeStoneNodesScheme = new int [][]{
            new int[]{-1,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14},
            new int[]{-1,-1,0,1,2,3,4,5,6,7,8,9,10,11,12,13},
            new int[]{-1,-1,-1,-1,0,1,2,3,4,5,6,7,8,9,10,11},
            new int[]{-1,-1,-1,-1,-1,-1,-1,-1,0,1,2,3,4,5,6,7},
            new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
    };

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

    public synchronized boolean getGeneration(int i, int j){
        while(!set[i][j]){
            try{
                wait();
            } catch (InterruptedException e){}
        }
        notifyAll();
        return generations[i][j];
    }

    public synchronized boolean getPropagation(int i, int j){
        while(!set[i][j]){
            try{
                wait();
            } catch (InterruptedException e){}
        }
        notifyAll();
        return propagations[i][j];
    }

    public synchronized void setGenerationAndPropagation(boolean generation, boolean propagation,int i, int j){
        generations[i][j] = generation;
        propagations[i][j] = propagation;
        set[i][j] = true;
        notifyAll();
    }

    public synchronized void setOutput(boolean value, int i){
        output[i] = value;
        outputSet[i] = true;
    }

    public synchronized boolean getHalfsum(int i){
        notifyAll();
        return halfsums[i];
    }

    public synchronized void setCarry(boolean value) {
        output[width] = value;
        outputSet[width] = true;
        notifyAll();
    }

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