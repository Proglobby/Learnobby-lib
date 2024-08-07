package org.proglobby.learnobby;


import org.proglobby.learnobby.Classifiers.MLPClassifier;
import org.proglobby.learnobby.data.DataFrame;
import org.proglobby.learnobby.data.DataSet;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        int[] layers = {3};
        MLPClassifier classifier = new MLPClassifier.Builder()
                .setLearningRate(0.01)
                .setMomentum(0.9)
                .setWeightDecay(0.0001)
                .setMaxIterations(1000)
                .setBatchSize(1)
                .setHiddenLayers(layers)
                .build();

        long start = System.currentTimeMillis();
        String irisPath = "C:\\Users\\MSI\\IdeaProjects\\Learnobby\\src\\main\\java\\org\\proglobby\\learnobby\\Classifiers\\data\\Iris - Iris.csv.csv";
        String testPath2 = Main.class.getClassLoader().getResource("test.csv").getPath();
        System.out.println(testPath2);
        String testPath = "C:\\Users\\MSI\\IdeaProjects\\Learnobby\\src\\main\\java\\org\\proglobby\\learnobby\\Classifiers\\data\\test.csv";
        DataFrame dataFrame = new DataFrame(testPath2);
        DataSet dataSet = new DataSet();
        dataSet = dataFrame.intoDataSet("species");
        for (int i = 0; i < dataSet.length; i++) {
            System.out.println("Y: " + dataSet.Ydata[i]);
        }
        System.out.println("Time taken to load data: " + (System.currentTimeMillis() - start) + "ms");
        System.out.println(dataFrame.toString());
        classifier.fit(dataSet);
    }

}
