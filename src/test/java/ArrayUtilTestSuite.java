import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class ArrayUtilTestSuite {
    String fileName1 = "array1.txt";
    String fileName2 = "array2.txt";
    String delimiter = " ";

    @DataProvider
    public Object[][] correctTestData() {
        return new Object[][]{
                {Arrays.asList("1 55 2 5", "55 56 4 1"), new Integer[] {1,2,4,5,55,56}},
                {Arrays.asList("", "55 56 4 1"), new Integer[] {1,4,55,56}},
                {Arrays.asList("", ""), new Integer[0]}
        };
    }

    @Test(dataProvider = "correctTestData")
    public void getSortedArrayOfIntegersFromTwoArraysInSeparateFilesPositiveTest(List<String> filesData, Integer[] expectedArray) throws IOException, URISyntaxException {
        writeTestDataToFile(fileName1, filesData.get(0));
        writeTestDataToFile(fileName2, filesData.get(1));

        Integer[] actualArray = ArrayUtil.getSortedArrayOfIntegersFromTwoArraysInSeparateFiles(fileName1, fileName2, delimiter);

        Assert.assertEquals(actualArray, expectedArray, String.format("Expected array: %s\nActual array: %s", Arrays.toString(expectedArray), Arrays.toString(actualArray)));
    }

    @DataProvider
    public Object[][] wrongTestData() {
        return new Object[][]{
                {Arrays.asList("1 55 2 a", "55 56 4 1"), NumberFormatException.class},
                {Arrays.asList("1 55 2 5", "55 56 b 1"), NumberFormatException.class},
                {Arrays.asList("1, 55, 2, 5", "55 56 3 1"), NumberFormatException.class},
                {Arrays.asList("1.0 55 2 5", "55 56 3 1"), NumberFormatException.class},
                {Arrays.asList("1 55 2 1/2", "55 56 3 1"), NumberFormatException.class},
        };
    }

    @Test(dataProvider = "wrongTestData")
    public void getSortedArrayOfIntegersFromTwoArraysInSeparateFilesNegativeTest(List<String> filesData, Class<Throwable> exceptionClass) throws IOException, URISyntaxException {
        writeTestDataToFile(fileName1, filesData.get(0));
        writeTestDataToFile(fileName2, filesData.get(1));

        Assert.assertThrows(exceptionClass, () ->
                ArrayUtil.getSortedArrayOfIntegersFromTwoArraysInSeparateFiles(fileName1, fileName2, delimiter)
        );
    }

    private void writeTestDataToFile(String fileName1, String fileData) throws IOException, URISyntaxException {
        Path path1 = Paths.get(getClass().getResource(fileName1).toURI());
        Files.writeString(path1, fileData, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
