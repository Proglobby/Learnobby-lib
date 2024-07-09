package org.proglobby.learnobby.Classifiers;


import org.proglobby.learnobby.Classifiers.data.DataFrame;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        int[] layers = {2, 3, 4};
        MLPClassifier classifier = new MLPClassifier.Builder()
                .setLearningRate(0.01)
                .setMomentum(0.9)
                .setWeightDecay(0.0001)
                .setMaxIterations(1000)
                .setBatchSize(1)
                .build();

        System.out.println(classifier.learningRate);
        long start = System.currentTimeMillis();
        String irisPath = "C:\\Users\\MSI\\IdeaProjects\\Learnobby\\src\\main\\java\\org\\proglobby\\learnobby\\Classifiers\\data\\Iris - Iris.csv.csv";
        String testPath = "C:\\Users\\MSI\\IdeaProjects\\Learnobby\\src\\main\\java\\org\\proglobby\\learnobby\\Classifiers\\data\\test.csv";
        DataFrame dataFrame = new DataFrame(testPath);
        System.out.println("Time taken to load data: " + (System.currentTimeMillis() - start) + "ms");
        System.out.println(dataFrame.toString());
    }

}
