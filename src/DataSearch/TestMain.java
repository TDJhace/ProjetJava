package DataSearch;

import java.util.Date;
import java.util.Set;

public class TestMain {

    public int CompareDate(Date d1, Date d2){

        if(d1.after(d2)){
            return 1;
        }
        else if(d1.before(d2)){
            return -1;
        }
        else{
            return 0;
        }
    }
    
    void merge(Date[] arr, int l, int m, int r){
        
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
 
        /* Create temp arrays */
        Date L[] = new Date[n1];
        Date R[] = new Date[n2];
 
        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
 
        /* Merge the temp arrays */
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2) {
            if (CompareDate(L[i],R[j]) ==-1) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
 
        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
 
    // Main function that sorts arr[l..r] using
    // merge()
    void sort(Date[] arr, int l, int r)
    {
        if (l < r) {
            // Find the middle point
            int m = (l + r) / 2;
 
            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);
 
            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }
 
    /* A utility function to print array of size n */
    static void printArray(Date[] arr)
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
 
    // Driver code
    public static void main(String args[]) throws Exception
    {
        DataSearcher ds = new DataSearcher();
        Set<Date> sk = ds.getHdate().keySet();
        Date[] arr = sk.toArray(new Date[sk.size()]);
        

        System.out.println("Given Array");
        printArray(arr);
 
            TestMain ob = new TestMain();
            ob.sort(arr, 0, arr.length - 1);
 
            System.out.println("\nSorted array");
            printArray(arr);
        }
    

}