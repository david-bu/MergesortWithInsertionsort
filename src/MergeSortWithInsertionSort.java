import java.util.Random;
import java.util.function.Consumer;

public class MergeSortWithInsertionSort {

    private static final int MAX_NUMBER = 5_000_000;
    public static final int TEST_SIZE = 2_000;
    private static final int TEST_ARRAY_SIZE = 100_000;

    /**
     * testing mergesortWithInsertionsort TEST_SIZE - times and measure the duration
     * printing the average duration
     * @param args - cmd line args - ignored
     */
    public static void main(String[] args) {
        float timeSum = 0;
        for (int i = 0; i < TEST_SIZE; i++) {
            int[] testArray = generateRandomArray(TEST_ARRAY_SIZE);
            long time = measureTime(MergeSortWithInsertionSort::mergeSortWithInsertionSort, testArray);
            float timeInS = time/1000_000_000f;
            System.out.println("Needed " + timeInS + " s");
            timeSum += timeInS;
        }
        System.out.println("avg time: " + timeSum/TEST_SIZE + " s");
    }

    /**
     * generating a random int array with numbers between 0 and MAX_NUMBER
     * @param size the size of the array which should be generated
     * @return an int array with size random elements
     */
    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        return random.ints(size, 0, MAX_NUMBER).toArray();
    }

    /**
     * measures the time of a function
     * @param consumer the function which time should be measured
     * @param parameter the arguments for the function counsumer
     * @return the duration if consumer in ns
     * @param <T> the arguments for the function
     */
    private static <T> long measureTime(Consumer<T> consumer, T parameter) {
        long startTime = System.nanoTime();
        consumer.accept(parameter);
        long stopTime = System.nanoTime();
        return stopTime - startTime;
    }

    /**
     * sorting an int array with insertion sort
     * @param a the array to sort (in place)
     */
    private static void insertionsort(int[] a) {
        int n = a.length;
        for (int i = 1; i < n ;i++) {
            int x = a[i];
            int j;
            for (j = i ;j > 0 && a[j-1] > x ;j--)
                a [j] = a[j-1];
            a [j] = x ;
        }
    }

    /**
     * sorting an int array with insertion sort or merge sort depending on the size of a
     * @param a array to sort
     * @param copy helper array for the algorithm to store sub arrays
     * @param start the position to begin sorting
     * @param end the position to stop sorting
     */
    private static void _mergeSort(int[] a, int[] copy, int start, int end) {
        if (start >= end)
            return;

        if (end - start <= 25) {
            insertionsort(a);
            return;
        }

        int mid = (start + end) / 2;
        _mergeSort(a, copy, start, mid);
        _mergeSort(a, copy, mid + 1, end);
        merge(a, copy, start, mid, end);
    }

    /**
     * merging two subarrays defined with start, mid and end into a
     * @param a the array to store the merged sorted arrays
     * @param copy the array storing the two sorted subarrays
     * @param start start index for first subarray in copy
     * @param mid end index for first subarray in copy; start index for second subarray
     * @param end end index for second subarray in copy
     */
    private static void merge(int[] a, int[] copy, int start, int mid, int end) {
        int i = 0;
        int j = start;
        int k = start;
        while (j <= mid) {
            copy[i++] = a[j++];
        }

        i = 0;
        while (k < j && j <= end) {
            if (copy[i] <= a[j]) {
                a[k++] = copy[i++];
            } else {
                a[k++] = a[j++];
            }
        }

        while (k < j) {
            a[k++] = copy[i++];
        }
    }

    /**
     * calling mergeSort with insertion sort, creating a helper array and setting the range for the complete array
     * @param a the array to sort
     */
    public static void mergeSortWithInsertionSort(int[] a) {
        _mergeSort(a, new int[a.length], 0, a.length - 1);
    }

}