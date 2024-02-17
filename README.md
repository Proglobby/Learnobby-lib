# Learnobby Library

This is a Java library for training models using machine learning tech, powerd by Rust, it takes the advantage of safety and perfoemance.

# Set-Up
## Step 1
Download the JAR file from this link:
[Download JAR File](https://github.com/MossaabDev/Learnobby-lib/raw/main/out/production/RustJava/MyTrainerLib.jar)
## Step 2
Add the JAR file to your project
### Intlij IDEA
https://www.jetbrains.com/help/idea/working-with-module-dependencies.html
### Eclipse
https://www.eclipse.org/forums/index.php/t/1112457/#:~:text=To%20do%20this%2C%20right%2Dclick,file%20to%20your%20project's%20classpath.

# How To Use
After adding the JAR file to your project structure, declare a `BinTrainer`, and use the method `fit()` that return an object of type `Model`.
```Java
BinTrainer trainer = new BinTrainer();
try {
      Model model = binTrainer.fit(10000, 0.1, "Iris.csv", "Iris-sitosa");// 10000: maxIterations, 0.1: learning_rate, data-set absolute path, the positive class
} catch (IOException e) {
      throw new RuntimeException(e);
}
```
You can use the method `predictSingle()` then to predict the class of a single example, the method will return `true` if the example belongs to the positive class.
```Java
boolean result = binTrainer.predictSingle(model, List.of(6.1, 3.0, 4.6, 1.4), "Iris-sitosa");
```
