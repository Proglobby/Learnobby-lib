pub mod sigmoid {

    //logistic function
    pub fn sigmoid_fn(x: f64) -> f64 {
        1_f64 / (1_f64 + (-x).exp())
    }

    //derivative of the logistic function
    pub fn sigmoid_derivative(x: f64) -> f64 {
        sigmoid_fn(x) * (1_f64 - sigmoid_fn(x))
    }

    

    



}

pub mod tanh{
    //tanh function
    pub fn tanh(x: f64) -> f64 {
        (2_f64 / (1_f64 + (-2_f64 * x).exp())) - 1_f64
    }

    //derivative of the tanh function

    pub fn tanh_derivative(x: f64) -> f64 {
        1_f64 - tanh(x).powi(2)
    }


}

pub mod relu{
    //relu funciton
    pub fn relu(x: f64) -> f64 {
        if x > 0_f64 {
            x
        } else {
            0_f64
        }
    }

    //derivation of relu 
    pub fn relu_derivative(x: f64) -> f64 {
        if x > 0_f64 {
            1_f64
        } else {
            0_f64
        }
    }
}
