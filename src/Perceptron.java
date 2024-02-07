public class Perceptron {

    int params;
    private double[] weights;
    double bias;



    public Perceptron(int indivs, double bias) {
        this.bias = bias;
        this.params = indivs;
    }

    public Perceptron() {
    }

    public void initWeights(){
        this.weights = new double[params];
        for (int i = 0; i< params; i++){
            weights[i] = (Math.random() * 2) - 1;
        }
        bias = (Math.random() * 2) - 1;
    }

    public void initWeights(int range){

    }
    public int predict(double[] indiv) throws NotSameLengthException {
        // Implementation of the perceptron prediction logic
        double sum = bias + dotProduct(indiv, weights);
        return (sum >= 0) ? 1 : 0;
    }

    private double dotProduct(double[] a, double[] b) throws NotSameLengthException {
        if (a.length != b.length){
            throw new NotSameLengthException();
        }
        double output = 0;
        for (int i = 0; i < a.length; i++) {
            output += a[i]*b[i];
        }
        return output;
    }

    public double getParams() {
        return params;
    }

    public void setParams(int params) {
        this.params = params;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }
}
