use std::fs::File;
use std::io::{BufRead, BufReader};
pub mod functions {
    use std::{
        fs::File,
        io::{BufRead, BufReader},
    };

    pub fn read_data(vec: &mut Vec<Vec<f64>>, path: &str) {
        let file = File::open(path).unwrap();
        let reader = BufReader::new(file);
        for line in reader.lines() {
            let line = line.unwrap();
            let mut row: Vec<f64> = Vec::new();
            for word in line.split_whitespace() {
                row.push(word.parse().unwrap());
            }
            vec.push(row);
        }
    }

    pub fn update_weights(indiv: Vec<f64>, weights_vec: &mut Vec<f64>, delta: f64, speed: f64) {
        let size = weights_vec.len();
        for j in 0..weights_vec.len() - 1 {
            weights_vec[j] = weights_vec[j] + delta * indiv[j] * speed;
        }
        println!(
            "old bias  : {} and the operation is {} + {} * {} = {}",
            weights_vec[weights_vec.len() - 1],
            weights_vec[weights_vec.len() - 1],
            delta,
            speed,
            (weights_vec[weights_vec.len() - 1] + delta * speed)
        );
        weights_vec[size - 1] = weights_vec[weights_vec.len() - 1] + delta * speed;
        println!("bias change  : {}", weights_vec[weights_vec.len() - 1]);
    }
}
