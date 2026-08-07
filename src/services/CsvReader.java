package services;
import com.opencsv.CSVReader;
import models.Diameter;
import models.Log;
import models.TreeType;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public List<Log> read(String path) throws Exception {

        List<Log> logs = new ArrayList<>();

        CSVReader reader = new CSVReader(new FileReader(path));

        String[] row;

        while ((row = reader.readNext()) != null) {

            Log log = new Log(
                    TreeType.valueOf(row[0]),
                    Diameter.valueOf(row[1]),
                    Integer.parseInt(row[2])
            );

            logs.add(log);
        }

        return logs;
    }
}
