package org.proglobby.learnobby.Classifiers.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFrame {
    List<String> columns;
    List<Map<String, Object>> data = new ArrayList<>();

    public DataFrame(String path) throws FileNotFoundException {
        loadFromCsv(path);
    }

    public void addRow(Object... rowData) {
        if (rowData.length != columns.size()) {
            throw new IllegalArgumentException("Number of columns and data elements must match");
        }
        Map<String, Object> row = new HashMap<>();
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
            Map<String, Object> row = data.get(rowIndex);
            return row.get(columnName);
        }
        throw new IndexOutOfBoundsException("Invalid row index");
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> row : data) {
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
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 0; i < columns.size(); i++) {
                        row.put(columns.get(i), values[i]);
                    }
                    data.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
