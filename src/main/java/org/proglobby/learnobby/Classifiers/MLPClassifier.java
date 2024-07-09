package org.proglobby.learnobby.Classifiers;

public class MLPClassifier {
    int[] layers;

    enum ActivationFunction{
        SIGMOID, TANH, RELU
    }
    double learningRate = 0.01f;
    double momentum = 0.9f;
    double weightDecay = 0.0001f;
    int maxIterations = 1000;
    int batchSize = 1;

    ActivationFunction activationFunction = ActivationFunction.SIGMOID;

    public MLPClassifier(Builder builder){
        this.layers = builder.layers;
        this.learningRate = builder.learningRate;
        this.momentum = builder.momentum;
        this.weightDecay = builder.weightDecay;
        this.maxIterations = builder.maxIterations;
        this.batchSize = builder.batchSize;
        this.activationFunction = builder.activationFunction;
    }




    public static class Builder{
        int[] layers;
        double learningRate = 0.01;
        double momentum = 0.9f;
        double weightDecay = 0.0001f;
        int maxIterations = 1000;
        int batchSize = 1;
        ActivationFunction activationFunction = ActivationFunction.SIGMOID;
        public Builder setLearningRate(double learningRate){
            this.learningRate = learningRate;
            return this;
        }
        public Builder setMomentum(double momentum){
            this.momentum = momentum;
            return this;
        }
        public Builder setWeightDecay(double weightDecay){
            this.weightDecay = weightDecay;
            return this;
        }
        public Builder setMaxIterations(int maxIterations){
            this.maxIterations = maxIterations;
            return this;
        }
        public Builder setBatchSize(int batchSize){
            this.batchSize = batchSize;
            return this;
        }
        public Builder setActivationFunction(ActivationFunction activationFunction){
            this.activationFunction = activationFunction;
            return this;
        }
        public MLPClassifier build(){
            return new MLPClassifier(this);
        }
    }

    public void fit(double[][] X, double[] y){
        // Train the model

    }
}
