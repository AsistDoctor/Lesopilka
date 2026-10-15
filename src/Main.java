import models.Workpiece;
import services.CsvReader;
import services.ParallelProcessingService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        CsvReader csvReader = new CsvReader();

        List<Workpiece> workpieces = csvReader.read("input.csv");

        List<List<Workpiece>> groups = new ArrayList<>();
        int groupSize = 2;

        for (int i = 0; i < workpieces.size(); i += groupSize) {
            int end = Math.min(i + groupSize, workpieces.size());

            groups.add(
                    new ArrayList<>(workpieces.subList(i, end))
            );
        }

        ParallelProcessingService parallelService =
                new ParallelProcessingService();

        parallelService.processGroups(groups);

        System.out.println(
                csvReader.getUnknownCount()
                        + " заготовок неизвестного происхождения и были пропущены"
        );
    }
}