package hexlet.code;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {


    private String readFile(String fileName) throws Exception {
        return Files.readString(Paths.get(getClass().getClassLoader()
                .getResource(fileName).getPath())).trim();
    }

    @Test
    void testFlatJsonFilesAreEqual() throws Exception {
        String file1Path = getClass().getClassLoader().getResource("file1.json").getPath();
        String file2Path = getClass().getClassLoader().getResource("file1.json").getPath();

        String diff = Differ.generate(file1Path, file2Path);

        String expected = "{\n" +
                          "    follow: false\n" +
                          "    host: hexlet.io\n" +
                          "    proxy: 123.234.53.22\n" +
                          "    timeout: 50\n" +
                          "}";
        assertEquals(expected, diff);
    }

    @Test
    void testFlatJsonFilesHaveDifference() throws Exception {
        String file1Path = getClass().getClassLoader().getResource("file1.json").getPath();
        String file2Path = getClass().getClassLoader().getResource("file2.json").getPath();

        String diff = Differ.generate(file1Path, file2Path);

        String expected = readFile("expected-flat.stylish.txt");
        assertEquals(expected, diff);
        assertEquals(expected, diff);
    }

    @Test
    void testFlatJsonFilesMissingKey() throws Exception {
        String file1Path = getClass().getClassLoader().getResource("file1.json").getPath();
        String file2Path = getClass().getClassLoader().getResource("file-missing-key.json").getPath();

        String diff = Differ.generate(file1Path, file2Path);

        String expected = readFile("expected-missing-key.txt");
        assertEquals(expected, diff);
    }
}