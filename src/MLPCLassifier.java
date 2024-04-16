import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MLPCLassifier {

    double[] weights;
    double bias;

    int layers;

    double [] data;

    public MLPCLassifier(int layers) {
        this.layers = layers;
    }

    public void initWeights(){
        for (int i = 0; i< weights.length; i++){
            weights[i] = (Math.random() * 2) - 1;
        }
        bias = (Math.random() * 2) - 1;
    }

   

    public int predict(double[] indiv) {
        double sum = bias + dotProduct(indiv, weights);
        return (sum >= 0) ? 1 : 0;
    }


    private double dotProduct(double[] a, double[] b) {
        double output = 0;
        for (int i = 0; i < a.length; i++) {
            output += a[i]*b[i];
        }
        return output;
    }






    public native double[] learn(int maxIteration, double speed, double[] weights, String path);


}
