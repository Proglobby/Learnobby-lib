package org.proglobby.learnobby;

public class Loss {
    public static double meanSquaredError(double[] y, double[] yHat) {
        double sum = 0;
        for (int i = 0; i < y.length; i++) {
            sum += Math.pow(y[i] - yHat[i], 2);
        }
        return sum / y.length;
    }

    public static double crossEntropy(double[] y, double[] yHat) {
        double sum = 0;
        for (int i = 0; i < y.length; i++) {
            sum += y[i] * Math.log(yHat[i]);
        }
        return -sum / y.length;
    }

    public static double binaryCrossEntropy(double[] y, double[] yHat) {
        double sum = 0;
        for (int i = 0; i < y.length; i++) {
            sum += y[i] * Math.log(yHat[i]) + (1 - y[i]) * Math.log(1 - yHat[i]);
        }
        return -sum / y.length;
    }

    public static double meanAbsoluteError(double[] y, double[] yHat) {
        double sum = 0;
        for (int i = 0; i < y.length; i++) {
            sum += Math.abs(y[i] - yHat[i]);
        }
        return sum / y.length;
    }

    public static double hingeLoss(double[] y, double[] yHat) {
        double sum = 0;
        for (int i = 0; i < y.length; i++) {
            sum += Math.max(0, 1 - y[i] * yHat[i]);
        }
        return sum / y.length;
    }

    //log loss with epsilon
    public static double logLoss(double[] y, double[] yHat, double epsilon) {
        double sum = 0;
        for (int i = 0; i < y.length; i++) {
            sum += y[i] * Math.log(yHat[i] + epsilon) + (1 - y[i]) * Math.log(1 - yHat[i] + epsilon);
        }
        return -sum / y.length;
    }

    //multinomial log loss with epsilon
    public static double multinomialLogLoss(double[] y, double[] yHat, double epsilon) {
        double sum = 0;
        for (int i = 0; i < y.length; i++) {
            sum += y[i] * Math.log(yHat[i] + epsilon);
        }
        return -sum / y.length;
    }
}
