import models.Log;
import services.SawMill;
import services.CsvReader;
import services.CsvWriter;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        CsvReader csvReader = new CsvReader();
        CsvWriter csvWriter = new CsvWriter();
        SawMill sawMill = new SawMill();

        List<Log> logs = csvReader.read("input.csv");
        sawMill.process(logs);


    }
}