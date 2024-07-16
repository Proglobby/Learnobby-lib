package org.proglobby.learnobby.structure.Classifiers;

import org.proglobby.learnobby.Activations;
import org.proglobby.learnobby.data.DataSet;
import org.proglobby.learnobby.structure.Layer;
import org.proglobby.learnobby.structure.Neuron;

import java.util.Arrays;

public class MLPClassifier {
    Layer[] hiddenLayers;

    Layer outputLayer;
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
        Layer[] hiddenLayers;
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
            this.hiddenLayers = new Layer[hiddenLayers.length];
            for (int i = 0; i < hiddenLayers.length; i++) {
                this.hiddenLayers[i] = new Layer();
                this.hiddenLayers[i].neurons = new Neuron[hiddenLayers[i]];
                for (int j = 0; j < this.hiddenLayers[i].neurons.length; j++) {
                    this.hiddenLayers[i].neurons[j] = new Neuron();
                }
            }
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
    /**
     * <h1>fit</h1>
     * <b>This method trains the model using the given dataset</b>
     * <h2>Example</h1>
     * <pre>
     *    {@code classifier.fit(dataSet);}
     *</pre>
     * @param dataSet The dataset to train the model
     * @return void
     *
     */
    public void fit(DataSet dataSet){
        outputLayer = new Layer();
        if (dataSet.target.size() == 2){
            outputLayer.neurons = new Neuron[1];
            Neuron neuron = new Neuron();
            neuron.weights = new double[hiddenLayers[hiddenLayers.length-1].neurons.length+1]
            outputLayer.neurons[0] = neuron;
            System.out.println("here"+dataSet.target);
        }else{
            outputLayer.neurons = new Neuron[dataSet.target.size()];

        }

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


    private double forwardPropagation(double[] input){
        // Forward propagation
        for (int i = 0; i < hiddenLayers.length; i++) {
            double[] output = new double[hiddenLayers[i].neurons.length];
            for (int j = 0; j < hiddenLayers[i].neurons.length; j++) {
                output[j] = Activations.sigmoid(dotProduct(input, hiddenLayers[i].neurons[j].weights) + hiddenLayers[i].neurons[j].bias);
            }
            input = new double[output.length];
            input = output;
        }
        //calculate for the ouput layout
        double[] output = new double[outputLayer.neurons.length];
        for (int i = 0; i < outputLayer.neurons.length; i++){
            output[i] = Activations.sigmoid(dotProduct(input, outputLayer.neurons[i].weights) + bias);
        }

        if (outputLayer.neurons.length <= 1){
            return output[0];
        }else{
            output = Activations.softmax(output);
            Arrays.sort(output);
            return output[output.length-1];
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
        for (int i = 0; i < hiddenLayers.length; i++) {
            for (int j = 0; j < hiddenLayers[i].neurons.length; j++) {
                hiddenLayers[i].neurons[j].weights = new double[columns];
                for (int k = 0; k < columns; k++) {
                    hiddenLayers[i].neurons[j].weights[k] = Math.random();
                }
                hiddenLayers[i].neurons[j].bias = Math.random();
            }
        }
        for (Neuron neuron: outputLayer.neurons){
            for (int i = 0; i < hiddenLayers[hiddenLayers.length -1].neurons.length; i++){
                neuron.weights[i] = Math.random();
            }
            neuron.bias = Math.random();
        }

    }

    public double dotProduct(double[] a, double[] b){
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }
}
