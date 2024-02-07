import java.util.List;

public class Model {
    // This class is a simple wrapper for the parameters of the model
    private List<Double> parameters;

    public Model(List<Double> parameters) {
        this.parameters = parameters;
    }

    public List<Double> getParameters() {
        return parameters;
    }

    public void setParameters(List<Double> parameters) {
        this.parameters = parameters;
    }
}
