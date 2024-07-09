package org.proglobby.learnobby.Classifiers.data;

public class DataSet {
    double data[][];
    public void loadFromDataFrame(DataFrame df){
        data = new double[df.data.size()][df.columns.size()];
        for(int i = 0; i < df.data.size(); i++){
            for(int j = 0; j < df.columns.size(); j++){
                data[i][j] = (double) df.getValue(i, df.columns.get(j));
            }
        }
    }
}
