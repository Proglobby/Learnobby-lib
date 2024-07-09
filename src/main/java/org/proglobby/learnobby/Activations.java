package org.proglobby.learnobby;

public class Activations {
    //sigmoid
    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    //relu
    public static double relu(double x) {
        return Math.max(0, x);
    }

    //tanh
    public static double tanh(double x) {
        return Math.tanh(x);
    }

    //softmax
    public static double[] softmax(double[] x) {
        double[] result = new double[x.length];
        double sum = 0;
        for (double value : x) {
            sum += Math.exp(value);
        }
        for (int i = 0; i < x.length; i++) {
            result[i] = Math.exp(x[i]) / sum;
        }
        return result;
    }

    //derivative of sigmoid

}
