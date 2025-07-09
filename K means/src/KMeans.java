import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class KMeans {
    private static int k;
    private static int params;
    private static List<Pair<String, double[]>> irises;
    private static List<List<Pair<String, double[]>>> clusters;
    private static List<double[]> centroids;
    private static List<Pair<String, double[]>> clearList;

    private static boolean stop;

    public static void clust(int numOfClusters){
        k = numOfClusters;
        init();
        while (!stop){
            stop = true;
            clusterize();
            updateCentroids();
            printClust();
        }

    }
    private static void init(){
        stop = false;
        irises = Parser.parseList(DataReader.readDataset("iris_kmeans.txt"));
        params = irises.get(0).getSecond().length;
        clusters = new ArrayList<>();
        clearList = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<>());
        }
        centroids = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            int index = (int) (Math.random() * irises.size());
            centroids.add(irises.get(index).getSecond());
        }

        for (int i = 0; i < irises.size(); i++) {
            double[] distances = new double[centroids.size()];
            for (int j = 0; j < centroids.size(); j++) {
                distances[j] = dist(centroids.get(j), irises.get(i).getSecond());
            }
            clusters.get(findMinIndex(distances)).add(irises.get(i));
        }
    }

    private static void clusterize(){
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < clusters.get(i).size(); j++) {
                clusterizeObservation(i, j);
            }
            for (int j = 0; j < clearList.size(); j++) {
                clusters.get(i).remove(clearList.get(j));
            }
            clearList.clear();
        }
    }

    private static void clusterizeObservation(int clustNum, int observationNum){
        double[] distances = new double[centroids.size()];
        for (int i = 0; i < centroids.size(); i++) {
            distances[i] = dist(clusters.get(clustNum).get(observationNum).getSecond(), centroids.get(i));
        }
        int targetClust = findMinIndex(distances);
        if(targetClust != clustNum){
            stop = false;
            clusters.get(targetClust).add(clusters.get(clustNum).get(observationNum));
            clearList.add(clusters.get(clustNum).get(observationNum));
        }
    }


    private static void updateCentroids(){
        for (int i = 0; i < k; i++) {
            double[] newCentroid = new double[params];
            for (int j = 0; j < clusters.get(i).size(); j++) {
                sumParams(newCentroid, clusters.get(i).get(j).getSecond(), clusters.get(i).size());
            }
            centroids.set(i, newCentroid);
        }
    }

    private static void sumParams(double[] cent, double[] clust, int divide){
        for (int i = 0; i < cent.length; i++) {
            cent[i] += clust[i]/(divide*1.);
        }
    }
    private static void printClust(){
        for (int i = 0; i < k; i++) {
            Map<String, Integer> labelCounts = new HashMap<>();
            for (Pair<String, double[]> observation : clusters.get(i)) {
                labelCounts.put(observation.getFirst(), labelCounts.getOrDefault(observation.getFirst(), 0) + 1);
            }
            System.out.print("Cluster " + (i + 1) + ": ");
            for (Map.Entry<String, Integer> entry : labelCounts.entrySet()) {
                double purity = (double) entry.getValue() / clusters.get(i).size() * 100;
                System.out.printf("%.2f%% %s ", purity, entry.getKey());
            }
            System.out.println();
        }
    }

    private static double dist(double[] a, double[] b){
        double res = 0;
        for (int i = 0; i < a.length; i++) {
            res+= Math.pow((a[i] - b[i]), 2);
        }
        return res;
    }

    private static int findMinIndex(double[] in){
        int index = 0;
        for (int i = 1; i < in.length; i++) {
            if(in[i]<in[index])
                index = i;
        }
        return index;
    }
}
