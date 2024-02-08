use ejni::{Class, JavaString, List, Object};
use jni::objects::{self, JClass, JList, JObject, JString, JValue};
use jni::sys::{
    _jobject, jboolean, jdouble, jdoubleArray, jint, jintArray, jobject, jobjectArray, jsize,
    jstring, JNI_COMMIT,
};
use std::time::{self, Instant};
mod functions;
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
) -> jdoubleArray {
    let start = time::Instant::now();
    let size = env.get_array_length(weights).unwrap();
    let mut weights_vec: Vec<f64> = vec![0.0; size as usize];
    let mut data: Vec<Vec<f64>> = Vec::new();
    let temp: String = env.get_string(path).unwrap().into();
    let path: &str = &temp;
    read_data(&mut data, path);
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

/*
#[no_mangle]
pub extern "system" fn Java_BinTrainer_testinho(env: JNIEnv, _: jobject, size: jint) -> jobject {
    /*let mut vectorinho = Vec::new();
    for i in 0..size as usize {
        vectorinho.push(rand::thread_rng().gen_range(-1.0..1.0) as f64);
    }
    let arr: &[f64] = vectorinho.as_slice();
    let result = env.new_double_array(size).unwrap();
    env.set_double_array_region(result, 0, &arr).unwrap();
    result
    */
    let list = List::arraylist(&env, Class::Double(&env).unwrap()).unwrap();
    for i in 0..size as usize {
        let rand = Object::new_Double(&env, rand::thread_rng().gen_range(-1.0..1.0)).unwrap();
        list.add(&env, &rand);
    }
    println!("{}", list.size(&env).unwrap());

    list.into()
}

#[no_mangle]
pub fn Java_BinTrainer_getStrings(env: JNIEnv<'_>, _: JClass) -> jobject {
    // Create a new java.util.ArrayList containing java.lang.String's
    let list = List::arraylist(&env, Class::String(&env).unwrap()).unwrap();

    // Add 10 Strings to the List
    for i in 0..10 {
        let string = JavaString::from_rust(&env, format!("Iteration {}", i)).unwrap();
        list.add(&env, &Object::new_string(&env, "wrt").unwrap())
            .unwrap();
    }

    list.into()
}


    pub unsafe extern "system" fn Java_BinTrainer_testinho(
    env: JNIEnv,

    _: JObject,

    size: i32,
) -> jobject {
    let cls_arraylist = env.find_class("java/util/ArrayList").unwrap();

    let arraylist = env
        .new_object(cls_arraylist, "(I)V", &[JValue::from(8)])
        .unwrap();

    let mut i = 0;

    while i < 7 {
        // Add items

        env.call_method(
            arraylist,
            "add",
            "(Ljava/lang/Object;)Z",
            &[JValue::from(0)],
        )
        .unwrap();

        i += 1;
    }

    *arraylist
}
*/
