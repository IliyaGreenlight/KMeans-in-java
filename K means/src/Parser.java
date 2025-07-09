import java.util.ArrayList;
import java.util.List;

public abstract class Parser {
    public static Pair<String, double[]> parse(String rawData){
        String[] irisStringData = rawData.split(",");
        double[] irisMeasures = new double[irisStringData.length-1];
        for (int i = 0; i < irisStringData.length-1; i++) {
            irisMeasures[i] = Double.parseDouble(irisStringData[i]);
        }
        return new Pair<>(irisStringData[irisStringData.length-1], irisMeasures);
    }
    public static List<Pair<String, double[]>> parseList(List<String> rawData){
        List<Pair<String, double[]>> res = new ArrayList<>();
        for (int i = 0; i < rawData.size(); i++) {
            res.add(parse(rawData.get(i)));
        }
        return res;
    }
}
