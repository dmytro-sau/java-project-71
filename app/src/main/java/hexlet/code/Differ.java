package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Differ {

    public static String generate(String filePathOne, String filePathTwo) throws Exception {
        Map<String, Object> map1 = parseFile(filePathOne);
        Map<String, Object> map2 = parseFile(filePathTwo);

        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(map1.keySet());
        allKeys.addAll(map2.keySet());

        Map<DiffType, Function<String, String>> formatters = Map.of(
            DiffType.ADDED, key -> "  + " + key + ": " + map2.get(key),
            DiffType.REMOVED, key -> "  - " + key + ": " + map1.get(key),
            DiffType.CHANGED, key -> "  - " + key + ": " + map1.get(key) + "\n  + " + key + ": " + map2.get(key),
            DiffType.UNCHANGED, key -> "    " + key + ": " + map1.get(key)
        );

        String diff = allKeys.stream()
                .map(key -> formatters.get(determineType(key, map1, map2)).apply(key))
                .collect(Collectors.joining("\n"));

        return "{\n" + diff + "\n}";
    }

    private static DiffType determineType(String key, Map<String, Object> map1, Map<String, Object> map2) {
        boolean in1 = map1.containsKey(key);
        boolean in2 = map2.containsKey(key);

        if (!in1) return DiffType.ADDED;
        if (!in2) return DiffType.REMOVED;
        if (!Objects.equals(map1.get(key), map2.get(key))) return DiffType.CHANGED;
        return DiffType.UNCHANGED;
    }

    private enum DiffType { ADDED, REMOVED, CHANGED, UNCHANGED }

    public static Map<String, Object> parseFile(String filePath) throws Exception {
        String content = Files.readString(Path.of(filePath));

        ObjectMapper objectMapper;
        if (filePath.endsWith(".yml") || filePath.endsWith(".yaml")) {
            objectMapper = new ObjectMapper(new YAMLFactory()); 
        } else {
            objectMapper = new ObjectMapper();
        }

        return new TreeMap<>(objectMapper.readValue(content, Map.class));
    }
}
