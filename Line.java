/**
 * Adder Thread representation of every line
 */
public class Line extends Thread {
    private Data data;
    // Number of line to calculate
    private int number;
    private int lineHeight;
    private int[][] scheme;
    private int[] output;

    public Line(Data data , int number, int[][] scheme) {
        this.data = data;
        this.number = number;
        lineHeight = data.getHeight();
        this.scheme = scheme;
    }

    /**
     * Calculate whole line
     */
    public void run(){
        boolean g1, g2, p1,p2;
        for (int i=0; i<lineHeight+1; i++){
            if (scheme[i][number] == -1){
                if (i == lineHeight) {
                    boolean h = data.getHalfsum(number);
                    // First line - only halfsum
                    if (number == 0) {
                        data.setOutput(h, number);
                    } else {
                        g1 = data.getGeneration(i, number-1);
                        h = h ^ g1;
                        data.setOutput(h, number);
                    }
                } else {
                    g1 = data.getGeneration(i, number);
                    p1 = data.getPropagation(i, number);
                    data.setGenerationAndPropagation(g1, p1, i + 1, number);
                }
            }else {
                g1 = data.getGeneration(i, number);
                g2 = data.getGeneration(i, scheme[i][number]);
                p1 = data.getPropagation(i, number);
                p2 = data.getPropagation(i, scheme[i][number]);
                g1 = g1|(p1&g2);
                p1 = p2&p1;
                if (i == lineHeight){
                    boolean h = data.getHalfsum(number);
                    // First line - only halfsum
                    if (number == 0) {
                        data.setOutput(h, number);
                    } else {
                        g1 = data.getGeneration(i, number-1);
                        h = h ^ g1;
                        data.setOutput(h, number);
                    }
                } else {
                    data.setGenerationAndPropagation(g1, p1, i + 1, number);
                }
            }
        }
    }
}

/**
 * Adder Thread representation of line calculating carry digit
 */
class CarryDigit extends Thread{
    private Data data;

    CarryDigit(Data data){
        this.data = data;
    }

    /**
     * Calculate carry digit
     */
    public void run(){
        boolean g = data.getGeneration(data.getHeight(), data.getWidth()-1);
        data.setCarry(g);
    }
}
