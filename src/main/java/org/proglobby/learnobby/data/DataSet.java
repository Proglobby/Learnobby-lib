package org.proglobby.learnobby.data;
import org.proglobby.learnobby.data.DataFrame;
public class DataSet {
    public double Xdata[][];
    public double Ydata[];
    public int length = 0;
    public int columns = 0;
    public DataSet(DataFrame dataFrame, String targetColumn){
        this.length = dataFrame.data.size();
        this.columns = dataFrame.columns.size()-1;
        this.Xdata = new double[length][columns - 1];
        this.Ydata = new double[length];
        for (int i = 0; i < length; i++) {
            int j = 0;
            for (String column : dataFrame.columns) {
                if (!column.equals(targetColumn)) {
                    Xdata[i][j] = dataFrame.data.get(i).get(column);
                    j++;
                } else {
                    Ydata[i] = dataFrame.data.get(i).get(column);
                }
            }
        }
    }
    public DataSet() {
    }
}
