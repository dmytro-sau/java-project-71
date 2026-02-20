package hexlet.code;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {

    private String readFile(String fileName) throws Exception {
        return Files.readString(Paths.get(getClass().getClassLoader()
                .getResource(fileName).getPath())).trim();
    }

    @Test
    void testFlatJsonFilesAreEqual() throws Exception {
        String file1 = getClass().getClassLoader().getResource("file1.json").getPath();
        String file2 = getClass().getClassLoader().getResource("file1.json").getPath();
        String diff = Differ.generate(file1, file2);
        String expected = readFile("expected-flat.stylish.txt");
        assertEquals(expected, diff);
    }

    @Test
    void testFlatJsonFilesHaveDifference() throws Exception {
        String file1 = getClass().getClassLoader().getResource("file1.json").getPath();
        String file2 = getClass().getClassLoader().getResource("file2.json").getPath();
        String diff = Differ.generate(file1, file2);
        String expected = readFile("expected-flat.stylish.txt");
        assertEquals(expected, diff);
    }

    @Test
    void testFlatJsonFilesMissingKey() throws Exception {
        String file1 = getClass().getClassLoader().getResource("file1.json").getPath();
        String file2 = getClass().getClassLoader().getResource("file-missing-key.json").getPath();
        String diff = Differ.generate(file1, file2);
        String expected = readFile("expected-missing-key.txt");
        assertEquals(expected, diff);
    }


    @Test
    void testFlatYamlFilesAreEqual() throws Exception {
        String file1 = getClass().getClassLoader().getResource("file1.yml").getPath();
        String file2 = getClass().getClassLoader().getResource("file1.yml").getPath();
        String diff = Differ.generate(file1, file2);
        String expected = readFile("expected-flat.stylish.txt"); // используем тот же expected
        assertEquals(expected, diff);
    }

    @Test
    void testFlatYamlFilesHaveDifference() throws Exception {
        String file1 = getClass().getClassLoader().getResource("file1.yml").getPath();
        String file2 = getClass().getClassLoader().getResource("file2.yml").getPath();
        String diff = Differ.generate(file1, file2);
        String expected = readFile("expected-flat.stylish.txt");
        assertEquals(expected, diff);
    }
}