import models.Workpiece;
import models.TreeType;

import services.SawMill;
import services.CsvReader;
import services.CsvWriter;

import java.util.Map;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        CsvReader csvReader = new CsvReader();
        CsvWriter csvWriter = new CsvWriter();
        SawMill sawMill = new SawMill();

        List<Workpiece> workpieces = csvReader.read("input.csv");

        Map<TreeType, Integer> result = sawMill.process(workpieces);

        csvWriter.write(result);

        System.out.println(
                csvReader.getUnknownCount()
                        + " заготовок неизвестного происхождения и были пропущены"
        );

        System.out.println(result);
    }
}