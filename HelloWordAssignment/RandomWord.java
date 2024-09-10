import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 0; 
        String selectedWord = ""; 
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();

    
            if (StdRandom.bernoulli(1.0/(i+1) * 1.0)) {
                selectedWord = word;
            }
            i++; 
        }
        System.out.println(selectedWord); 
    }
}