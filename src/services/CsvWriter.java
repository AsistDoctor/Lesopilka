package services;

import com.opencsv.CSVWriter;
import models.TreeType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CsvWriter {

    public void write(Map<TreeType, Integer> result) throws IOException {

        CSVWriter writer = new CSVWriter(
                new FileWriter("result.csv")
        );

        for (Map.Entry<TreeType, Integer> entry : result.entrySet()) {

            writer.writeNext(new String[]{
                    entry.getKey().name(),
                    entry.getValue().toString()
            });
        }

        writer.close();
    }
}