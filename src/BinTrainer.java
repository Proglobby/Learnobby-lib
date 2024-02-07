import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BinTrainer {
    private double[][] indivList;

    private Perceptron perceptron;



    BufferedReader reader;
    public void loadDataSet(String dataPath) throws IOException {
        reader = new BufferedReader(new FileReader(dataPath));
        String line;
        int j = 0;
        List<List<Double>> list = new ArrayList<>();
        while((line = reader.readLine()) != null){
            List<Double> temp = new ArrayList<>();
            String[] strings = line.split(" ");
            for (int i = 0; i < strings.length; i++) {
                temp.add(Double.parseDouble(strings[i]));
            }
            list.add(temp);
        }
        indivList = new double[list.size()][list.get(0).size()];
        for (int i = 0; i < list.size(); i++){
            for (int k = 0; k < list.get(i).size(); k++) {
                indivList[i][k] = list.get(i).get(k);
            }
        }
    }

    public Model fit(int maxIteration, double speed, String dataSetPath) throws IOException {
        loadDataSet(dataSetPath);
        perceptron = new Perceptron(indivList[0].length, 0.5);
        perceptron.initWeights();
        perceptron.setWeights(learn(maxIteration, speed, this.perceptron.getWeights(), dataSetPath));
        List<Double> output = new ArrayList<>();
        for (int i = 0; i < perceptron.getWeights().length; i++) {
            output.add(perceptron.getWeights()[i]);
        }
        Model model = new Model(output);
        return model;
    }

    public boolean predictSingle(Model model, List<Double> indiv){
        if (model.getParameters().size() != indiv.size() + 1){
            throw new NotSameLengthException();
        }else{
            double z = 0;
            for (int i = 0; i < indiv.size(); i++) {
                z = z + indiv.get(i)*model.getParameters().get(i);
            }
            z =z + model.getParameters().get(model.getParameters().size() - 1);
            if (z >= 0){
                return true;
            }
        }
        return false;
    }


    public native double[] learn(int maxIteration, double speed, double[] weights, String path);

    public native ArrayList<Double> testinho(int size);

    public native ArrayList<String> getStrings();
    static {
        // This actually loads the shared object that we'll be creating.
        // The actual location of the .so or .dll may differ based on your
        // platform.
        System.load("C:\\Users\\MSI\\IdeaProjects\\RustJava\\Rust\\myTrainerlib\\target\\debug\\myTrainerlib.dll");
    }

    public double[][] getIndivList() {
        return indivList;
    }

    public void setIndivList(double[][] indivList) {
        this.indivList = indivList;
    }
}
