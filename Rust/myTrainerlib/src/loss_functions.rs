pub mod loss_functions{
    //log loss function
    pub fn log_loss(y: f64, yp: f64) -> f64 {
        -y * yp.ln() - (1_f64 - y) * (1_f64 - yp).ln()
    }
    //derivative of log loss function
    pub fn der_log_loss(y: f64, yp: f64) -> f64 {
        (yp - y) / (yp * (1_f64 - yp))
    }

    //mean squared error loss function
    pub fn mean_squared_error(y: f64, yp: f64) -> f64 {
        (y - yp).powi(2)
    }

    
}