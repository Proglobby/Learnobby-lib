package org.proglobby.learnobby.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class DataFrame {
    List<String> columns;
    List<LinkedHashMap<String, Double>> data = new ArrayList<>();
    Set<Double> target = new HashSet();

    public DataFrame(String path) throws FileNotFoundException {
        loadFromCsv(path);
    }

    public void addRow(Double... rowData) {
        if (rowData.length != columns.size()) {
            throw new IllegalArgumentException("Number of columns and data elements must match");
        }
        LinkedHashMap<String, Double> row = new LinkedHashMap<>();
        for (int i = 0; i < columns.size(); i++) {
            row.put(columns.get(i), rowData[i]);
        }
        data.add(row);
    }

    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            data.remove(rowIndex);
        } else {
            throw new IndexOutOfBoundsException("Invalid row index");
        }
    }

    public Object getValue(int rowIndex, String columnName) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            Map<String, Double> row = data.get(rowIndex);
            return row.get(columnName);
        }
        throw new IndexOutOfBoundsException("Invalid row index");
    }

//    public double[][] intoMatrix() {
//        double[][] matrix = new double[data.size()][columns.size()];
//        for (int i = 0; i < data.size(); i++) {
//            for (int j = 0; j < columns.size(); j++) {
//                matrix[i][j] = (double) getValue(i, columns.get(j));
//            }
//        }
//        return matrix;
//    }

    public DataSet intoDataSet(String targetColumn) {
        double Xdata[][] = new double[data.size()][columns.size() - 1];
        double Ydata[] = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < columns.size(); j++) {
                if (columns.get(j).equals(targetColumn)) {
                    Ydata[i] = data.get(i).get(columns.get(j));
                } else {
                    Xdata[i][j] = data.get(i).get(columns.get(j));
                }
            }
        }
        DataSet dataSet = new DataSet();
        dataSet.Xdata = Xdata;
        dataSet.Ydata = Ydata;
        dataSet.length = Ydata.length;
        dataSet.columns = columns.size();
        return dataSet;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map<String, Double> row : data) {
            sb.append(row).append("\n");
        }
        return sb.toString();
    }

    public void loadFromCsv(String path) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (columns == null) {
                    columns = new ArrayList<>();
                    for (String value : values) {
                        columns.add(value);
                        System.out.println("column added : " + value);
                    }
                } else {
                    LinkedHashMap<String, Double> row = new LinkedHashMap<>();
                    for (int i = 0; i < columns.size(); i++) {
                        row.put(columns.get(i), Double.parseDouble(values[i]));
                    }
                    data.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
