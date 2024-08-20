/* Compare the page faults found when using FIFO and LRU.
 * Kylie Heiland
 * CSCI331
 * 12/2/23
 */
import java.util.*;

public class PageReplacementDemo{
    // Method to create a reference string for page replacement
    public static ArrayList<Integer> createRS(int sizeOfVM, int length, int sizeOfLocus, int rateOfMotion, double prob){
        ArrayList<Integer> result = new ArrayList<Integer>();
        int start = 0;
        int n;
        while(result.size() < length){
            // Add 'rateOfMotion' number of random pages to the result
            for(int i = 0; i < rateOfMotion; i++){
                n = (int)(Math.random() * sizeOfLocus + start);
                result.add(n);
            }
            
            // Randomly change the start, based on the probability 'prob'
            if(Math.random() < prob)
                start = (int)Math.random() * sizeOfVM;
            else
                 start++;   
        }
        return result;
    }
    
    // First-In, First-Out (FIFO) Page Replacement Algorithm
    public static int FIFOReplacement(ArrayList<Integer> rs, int numOfFrames){
        int[] frames = new int[numOfFrames];
        int i, first = 0, count = 0;
        // Initialize the frames
        for(i = 0; i < numOfFrames; i++)
            frames[i] = i;
        // Process each page in the reference string
        for(i = 0; i < rs.size(); i++){
            // Replace the page if it's not already in a frame
            if(isInArray(frames, rs.get(i)) == -1){
                frames[first] = rs.get(i);
                count++;
                first = (first + 1) % (frames.length);
            }
        }
         return count;   
    }
    
    // Least Recently Used (LRU) Page Replacement Algorithm
    public static int LRUReplacement(ArrayList<Integer> rs, int numOfFrames){
        int[] frames = new int[numOfFrames];
        int i, j, first = 0, count = 0;
        // Initialize the frames
        for(i = 0; i < numOfFrames; i++)
            frames[i] = i;
        // Process each page in the reference string
        for(i = 0; i < rs.size(); i++){
            int index = isInArray(frames, rs.get(i));
            int least;
            if(index == -1){
                least = rs.get(i);
                count++;
                index = 0;
            } else
                least = frames[index];
            for(j = index; j < frames.length - 1; j++)
                frames[j] = frames[j+1];
            frames[j] = least;
        }
        return count;
    }
    
    // Helper method to check if a page is already in a frame
    private static int isInArray(int[] frames, int page){
        for(int i = 0; i < frames.length; i++)
            if(frames[i] == page) return i;
        return -1;
    }
    
    // Main method to execute the page replacement demonstration
    public static void main(String[] args){
        // Create a reference string for page replacement
        ArrayList<Integer> rs = createRS(4096, 10000, 10, 100, 0.1);
        // Demonstrate FIFO replacement and print the number of page faults
        System.out.println("The page fault using FIFO replacement algorithm: ");
        System.out.println(FIFOReplacement(rs, 10) + " times");
        // Demonstrate LRU replacement and print the number of page faults
        System.out.println("The page fault using LRU replacement algorithm: ");
        System.out.println(LRUReplacement(rs, 10) + " times");
    }
}
