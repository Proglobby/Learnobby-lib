package org.proglobby.learnobby.Classifiers;

import org.proglobby.learnobby.Activations;
import org.proglobby.learnobby.data.DataSet;

public class MLPClassifier {
    int[] hiddenLayers;
    double[] weights;
    double bias;


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
        this.hiddenLayers = builder.hiddenLayers;
        this.learningRate = builder.learningRate;
        this.momentum = builder.momentum;
        this.weightDecay = builder.weightDecay;
        this.maxIterations = builder.maxIterations;
        this.batchSize = builder.batchSize;
        this.activationFunction = builder.activationFunction;
    }




    public static class Builder{
        int[] hiddenLayers;
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

        public Builder setHiddenLayers(int[] hiddenLayers){
            this.hiddenLayers = hiddenLayers;
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

    public void fit(DataSet dataSet){
        // Train the model
        initWeights(dataSet.columns);
        for (int i = 0; i < maxIterations; i++) {
            //check the batch size
            if (batchSize > dataSet.length) {
                throw new IllegalArgumentException("Batch size cannot be greater than the number of samples");
            }else{
                for (int j = 0; j < dataSet.length; j++) {
                    forwardPropagation(dataSet.Xdata[j]);
                    backPropagation(new double[]{dataSet.Ydata[j]});
                    updateWeights();
                }
            }

        }

    }

    private void forwardPropagation(double[] input){
        // Forward propagation
        double z = 0;
        for (int i = 0; i < hiddenLayers[0]; i++) {
            for (int j = 0; j < input.length; j++) {
                z += input[j] * weights[i];
            }
        }
        z += bias;
        double a = 0;
        switch (activationFunction){
            case SIGMOID:
                a = Activations.sigmoid(z);
                break;
            case TANH:
                a = Activations.tanh(z);
                break;
            case RELU:
                a = Activations.relu(z);
                break;
        }


    }

    private void backPropagation(double[] target){
        // Back propagation
    }

    //calculate the error


    private void updateWeights(){
        // Update weights
    }

    private void initWeights(int columns){
        // Initialize weights
        if (hiddenLayers.length < 1) {
            throw new IllegalArgumentException("Number of hiddenLayers must be greater than 0");
        }else{
            // count the number of weights
            int numWeights = columns*hiddenLayers[0];
            for (int i = 0; i < hiddenLayers.length - 1; i++) {
                numWeights += hiddenLayers[i] * hiddenLayers[i + 1];
            }
            weights = new double[numWeights];
            System.out.println("Number of weights: " + numWeights);
        }
        for (int i = 0; i < weights.length; i++) {
            // Initialize weights for the input layer
            weights[i] = Math.random();
        }

    }
}
