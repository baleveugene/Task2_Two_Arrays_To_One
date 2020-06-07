import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ArrayUtil {

    public static Integer[] getSortedArrayOfIntegersFromTwoArraysInSeparateFiles(String file1, String file2, String delimeter) {
        //read first file
        String file1AsString = getFileFromResourcesAsString(file1);
        Set<Integer> file1AsSet = convertFileAsStringToFileAsSetOfIntegers(file1AsString, delimeter);
        System.out.println("File 1 as set of integers: " + file1AsSet);

        //read second file
        String file2AsString = getFileFromResourcesAsString(file2);
        Set<Integer> file2AsSet = convertFileAsStringToFileAsSetOfIntegers(file2AsString, delimeter);
        System.out.println("File 2 as set of integers: " + file2AsSet);

        Set<Integer> finalSortedSet = new TreeSet<>();
        finalSortedSet.addAll(file1AsSet);
        finalSortedSet.addAll(file2AsSet);
        Integer[] finalSortedArray = new Integer[finalSortedSet.size()];

        return finalSortedSet.toArray(finalSortedArray);
    }

    // get file as string from resources folder
    private static String getFileFromResourcesAsString(String fileName) {
        InputStream inputStream = ArrayUtil.class.getResourceAsStream("/" + fileName);
        String fileAsString = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        System.out.println(String.format("File <%s> as string: <%s>", fileName, fileAsString));
        return fileAsString;
    }

    private static Set<Integer> convertFileAsStringToFileAsSetOfIntegers(String s, String delimiter) {
        if(s.isEmpty()) return new TreeSet<>(); // empty file is allowed

        return Arrays.stream(s.trim()
                .replace("\n", delimiter)
                .split("[" + delimiter + "]+") // regex match multiply spaces to avoid empty rows
        ).map(Integer::parseInt)
                .collect(Collectors.toSet());
    }
}
