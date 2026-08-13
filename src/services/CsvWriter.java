package services;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {

    public void write() throws IOException {

        CSVWriter writer = new CSVWriter(
                new FileWriter("result.csv")
        );

        writer.writeNext(new String[]{
                "OAK",
                "48"
        });

        writer.close();
    }
}