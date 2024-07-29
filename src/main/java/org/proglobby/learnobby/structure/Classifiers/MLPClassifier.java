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
    public void fit(DataSet dataSet) throws Exception {
        outputLayer = new Layer();
        if (dataSet.target.size() == 2){
            outputLayer.neurons = new Neuron[1];
            Neuron neuron = new Neuron();
            neuron.weights = new double[hiddenLayers[hiddenLayers.length-1].neurons.length];
            outputLayer.neurons[0] = neuron;
            for (Neuron neuron1 : outputLayer.neurons){

            }
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
                    System.out.println("after"+forwardPropagation(dataSet.Xdata[j])+"with : wieghts : " + weightsToString());
                    backPropagation(new double[]{dataSet.Ydata[j]});
                    updateWeights();
                }
            }

        }

    }

    private String weightsToString() {
        String output = new String();
        int count = 0;
        for (int i = 0; i < hiddenLayers.length; i++) {
            for (int j = 0; j < hiddenLayers[i].neurons.length; j++) {
                output+=" w"+count+" :"+hiddenLayers[i].neurons[j].bias;
                count++;
                for (int k = 0; k < hiddenLayers[i].neurons[j].weights.length; k++) {
                    output+=" w"+count+" :"+hiddenLayers[i].neurons[j].weights[k];
                    count++;
                }
            }

        }
        for (int i = 0; i < outputLayer.neurons.length; i++) {
            output+=" wo"+count+" :"+outputLayer.neurons[i].bias;
            count++;
            for (int j = 0; j < outputLayer.neurons[i].weights.length; j++) {
                output+=" wo"+count+" :"+outputLayer.neurons[i].weights[j];
                count++;
            }
        }
        return output;
    }


    private double forwardPropagation(double[] input) throws Exception {
        // Forward propagation
        for (int i = 0; i < hiddenLayers.length; i++) {
            double[] output = new double[hiddenLayers[i].neurons.length];
            for (int j = 0; j < hiddenLayers[i].neurons.length; j++) {
                output[j] = activate(dotProduct(input, hiddenLayers[i].neurons[j].weights) + hiddenLayers[i].neurons[j].bias);
            }
            input = new double[output.length];
            input = output;
        }
        //calculate for the ouput layout
        double[] output = new double[outputLayer.neurons.length];
        for (int i = 0; i < outputLayer.neurons.length; i++){
            output[i] = activate(dotProduct(input, outputLayer.neurons[i].weights) + outputLayer.neurons[i].bias);
        }

        if (outputLayer.neurons.length <= 1){
            System.out.println("bef "+output[0]);
            return output[0] >= 0.5 ? 1:0;
        }else{
            output = Activations.softmax(output);
            int maxIndex = 0;
            for (int i = 0; i < output.length; i++) {
                if (output[maxIndex] < output[i]){
                    maxIndex = i;
                }
            }
            return maxIndex;
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
        System.out.println(columns+"clmns");
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
        double sum = 0;
        for (int i = 0; i <a.length ; i++) {
            sum+= a[i]*b[i];
        }
        return sum;
    }

    public double activate(double x) throws Exception {
        switch (activationFunction){
            case SIGMOID -> {
                return Activations.sigmoid(x);
            }
            case RELU -> {
                return Activations.relu(x);
            }
            case TANH -> {
                return Activations.tanh(x);
            }
        }
        Exception RuntimeException = new RuntimeException("No Activation function declared");
        throw RuntimeException;
    }



}
