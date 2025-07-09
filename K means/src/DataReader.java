import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class DataReader {
    public static List<String> readDataset(String filepath) {
        List<String> tempList = null;
        try {
            tempList = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempList;
    }


}