use ejni::{Class, JavaString, List, Object};
use jni::objects::{self, JClass, JList, JObject, JString, JValue};
use jni::sys::{
    _jobject, jboolean, jdouble, jdoubleArray, jint, jintArray, jobject, jobjectArray, jsize,
    jstring, JNI_COMMIT,
};
use std::time::{self, Instant};
mod functions;
mod activation_funcs;
use functions::functions::read_data;
use jni::JNIEnv;
use rand::distributions::weighted;
use std::fs::File;
use std::io::{self, stdout, BufRead, BufReader, Read};
use std::path;

use crate::functions::functions::update_weights;
#[no_mangle]
pub extern "system" fn Java_BinTrainer_learn<'local>(
    mut env: JNIEnv<'local>,
    _: jobject,
    max_iterations: jint,
    speed: jdouble,
    weights: jdoubleArray,
    path: JString,
    positive_class: JString,
) -> jdoubleArray {
    let start = time::Instant::now();
    let size = env.get_array_length(weights).unwrap();
    let mut weights_vec: Vec<f64> = vec![0.0; size as usize];
    let mut data: Vec<Vec<f64>> = Vec::new();
    let temp: String = env.get_string(path).unwrap().into();
    let path: &str = &temp;
    let temp: String = env.get_string(positive_class).unwrap().into();
    let positive_class: &str = &temp;
    read_data(&mut data, path, positive_class);
    let mut err: bool = true;
    let mut counter: i32 = 0;
    let _ = env
        .get_double_array_region(weights, 0, weights_vec.as_mut_slice())
        .unwrap();

    while err && counter < max_iterations {
        err = false;
        for i in 0..data.len() {
            let mut z: f64 = 0.0;
            for j in 0..weights_vec.len() - 1 {
                z = z + weights_vec[j] * data[i][j];
            }
            z = z + weights_vec[weights_vec.len() - 1];
            let mut yp = 0;
            if z >= 0_f64 {
                yp = 1;
            } else {
                yp = 0;
            }
            let delta = data[i][data[i].len() - 1] - yp as f64;
            if delta != 0 as f64 {
                err = true;
                update_weights(data[i].clone(), &mut weights_vec, delta, speed);
            }
        }
        counter = counter + 1;
    }

    let output = env.new_double_array(size as i32).unwrap();
    let _ = env
        .set_double_array_region(output, 0, weights_vec.as_slice())
        .unwrap();
    let end = time::Instant::now();
    let duration = (end - start).as_millis();
    println!("duration: {}", duration);

    output
}


