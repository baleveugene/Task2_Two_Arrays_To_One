import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        String file1 = "array1.txt";
        String file2 = "array2.txt";
        String delimiter = " ";

        System.out.println(
                Arrays.toString(
                        ArrayUtil.getSortedArrayOfIntegersFromTwoArraysInSeparateFiles(file1, file2, delimiter)
                )
        );
    }
}
