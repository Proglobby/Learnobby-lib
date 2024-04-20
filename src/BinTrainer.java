import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BinTrainer {

    private Perceptron perceptron;
    int params;


    BufferedReader reader;
    public void loadDataSet(String dataPath) throws IOException {
        reader = new BufferedReader(new FileReader(dataPath));
        String line;
        int j = 0;
        List<List<Double>> list = new ArrayList<>();
        line = reader.readLine();
        line = reader.readLine();
        String[] strings = line.split(",");
        params = strings.length;
    }

    public Model fit(int maxIteration, double speed, String dataSetPath, String positiveClass) throws IOException {
        loadDataSet(dataSetPath);
        perceptron = new Perceptron(params, 0.5);
        perceptron.initWeights();
        perceptron.setWeights(learn(maxIteration, speed, this.perceptron.getWeights(), dataSetPath, positiveClass));
        List<Double> output = new ArrayList<>();
        for (int i = 0; i < perceptron.getWeights().length; i++) {
            output.add(perceptron.getWeights()[i]);
        }
        Model model = new Model(output);
        return model;
    }

    public boolean predictSingle(Model model, List<Double> indiv, String positiveClass){
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


    public native double[] learn(int maxIteration, double speed, double[] weights, String path, String positiveClass);

    public native ArrayList<Double> testinho(int size);

    public native ArrayList<String> getStrings();
    static {
        // This actually loads the shared object that we'll be creating.
        // The actual location of the .so or .dll may differ based on your
        // platform.
        //String home = System.getProperty("user.home");
        URL url = BinTrainer.class.getResource("myTrainerlib.dll");
        File file = new File(url.getPath());
        System.load(file.getAbsolutePath());
    }


}
