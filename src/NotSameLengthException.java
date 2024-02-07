public class NotSameLengthException extends RuntimeException{
    public NotSameLengthException() {
        super("The number of features is not the same as the numbebr of weights");
    }
}
