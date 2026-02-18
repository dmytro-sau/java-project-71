package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Differ {

    public static String generate(String filePathOne, String filePathTwo, String format) throws Exception {
        Map<String, Object> data1 = parseFile(filePathOne);
        Map<String, Object> data2 = parseFile(filePathTwo);

    
        return "Files parsed successfully:\n"
                + "File 1 keys: " + data1.keySet() + "\n"
                + "File 2 keys: " + data2.keySet();
    }

    private static Map<String, Object> parseFile(String filePath) throws Exception {
        String content = Files.readString(Path.of(filePath));
        return parseJson(content);
    }

    private static Map<String, Object> parseJson(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }
}
