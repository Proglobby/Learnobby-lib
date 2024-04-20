pub mod loss_functions{
    //log loss function
    pub fn log_loss(y: f64, yp: f64) -> f64 {
        -y * yp.ln() - (1_f64 - y) * (1_f64 - yp).ln()
    }

    //mean squared error loss function
    pub fn mean_squared_error(y: f64, yp: f64) -> f64 {
        (y - yp).powi(2)
    }

    
}