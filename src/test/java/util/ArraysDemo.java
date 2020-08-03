package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Arrays.sort
 * Arrays.binarySearch
 * Comparator
 **/
public class ArraysDemo {
    public static void main(String[] args){
        String [] sa = {"one", "two", "three", "four"};
        Arrays.sort(sa);
        for(String s : sa){
            System.out.print(s + " ");
        }
        System.out.println("\none = " + Arrays.binarySearch(sa, "one"));

        System.out.println("now reverse sort");
        ReSortComparator rs = new ReSortComparator();
        //re-sort the array using the Comparator. sort(sa, rs)
        Arrays.sort(sa, rs);
        for(String s : sa){
            System.out.print(s + " ");
        }
        //doesn't pass the binarySearch() method the Comparator we used to sort the array, so get an incorrect answer
        System.out.println("\none = " + Arrays.binarySearch(sa, "one"));
        //passing the Comparator to binarySearch(), so get correct answer
        System.out.println("one = " + Arrays.binarySearch(sa,"one", rs));
    }
    //define the Comparator, it's ok for this to be an inner class
    static class ReSortComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b){
            return b.compareTo(a);
        }
    }

    private static void printArray(){
        int[] intArray = { 1, 2, 3, 4, 5 };
        String intArrayString = Arrays.toString(intArray);

        // print directly will print reference value
        System.out.println(intArray);  // [I@7150bd4d

        System.out.println(intArrayString); // [1, 2, 3, 4, 5]
    }

    private static void cvtArrayListToArray(){
        String[] stringArray = { "a", "b", "c", "d", "e" };
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(stringArray));
        String[] stringArr = new String[arrayList.size()];
        arrayList.toArray(stringArr);
        for (String s : stringArr)
            System.out.println(s);
    }
    private static void cvtArrayToArrayList(){
        String[] stringArray = { "a", "b", "c", "d", "e" };
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(stringArray));
        System.out.println(arrayList);// [a, b, c, d, e]
    }
}
