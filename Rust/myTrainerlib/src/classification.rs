pub mod logistic_regression{
    mod activation_funcs;
    mod loss_functions;
    use activation_funcs::sigmoid;
    use loss_functions::{log_loss, der_log_loss};
    pub fn fit(features: &[f64], weights: &mut [f64], learning_rate: f46){
        let mut z: f46 = 0;
        for i in 0..weights.len() - 1 {
            z = z + weights[i] * features[i];
        }
        z = z + weights[weights.len() - 1];
        let mut g = sigmoid(z);
        if g >= 0.5 {
            g = 1;
        } else {
            g = 0;
        }
        let delta = log_loss(features[features.len() - 1], g);
        if delta != 0 as f64 {
            for i in 0..weights.len() - 1{
                weights[i] = weights[i] - learning_rate*der_log_loss(features[features.len() - 1], g);
            }
        }
    }

    
}