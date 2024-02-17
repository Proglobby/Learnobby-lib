use std::fs::File;
use std::io::{BufRead, BufReader};
pub mod functions {
    use std::{
        error,
        fs::File,
        io::{BufRead, BufReader},
    };

    pub fn read_data(vec: &mut Vec<Vec<f64>>, path: &str, positive_class: &str) {
        let file = File::open(path).unwrap();
        let reader = BufReader::new(file);
        let mut i = 0;
        for line in reader.lines() {
            if i == 0 {
                i = i + 1;
                continue;
            }
            let line = line.unwrap();
            let mut row: Vec<f64> = Vec::new();
            for word in line.split(",") {
                match word.parse::<f64>() {
                    Ok(word) => {
                        row.push(word);
                    }
                    Err(e) => {
                        if word == positive_class {
                            row.push(1.0);
                        } else {
                            row.push(0.0);
                        }
                    }
                }
            }
            vec.push(row);
        }
    }

    pub fn update_weights(indiv: Vec<f64>, weights_vec: &mut Vec<f64>, delta: f64, speed: f64) {
        let size = weights_vec.len();
        for j in 0..weights_vec.len() - 1 {
            weights_vec[j] = weights_vec[j] + delta * indiv[j] * speed;
        }
        weights_vec[size - 1] = weights_vec[weights_vec.len() - 1] + delta * speed;
    }
}
