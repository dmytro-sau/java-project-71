package hexlet.code;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
        name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0",
        description = "Compares two configuration files and shows their difference."
)
public class App implements Callable<Integer> {

    @Option(
            names = {"-f", "--format"},
            description = "Output format [default: stylish]",
            defaultValue = "stylish"
    )
    private String format;

    @Parameters(index = "0", description = "Path to the first file")
    private String filepath1;

    @Parameters(index = "1", description = "Path to the second file")
    private String filepath2;

    @Override
    public Integer call() throws Exception {
        String diff = Differ.generate(filepath1, filepath2);
        System.out.println(diff);
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
