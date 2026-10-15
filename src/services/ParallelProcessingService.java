package services;

import models.TreeType;
import models.Workpiece;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class ParallelProcessingService {

    private static final Logger logger =
            LoggerFactory.getLogger(ParallelProcessingService.class);

    private static final int MAX_THREADS = 2;

    public void processGroups(List<List<Workpiece>> groups)
            throws InterruptedException, ExecutionException {

        ExecutorService executor =
                Executors.newFixedThreadPool(MAX_THREADS);

        List<Future<?>> tasks = new ArrayList<>();

        try {
            for (int i = 0; i < groups.size(); i++) {
                int groupNumber = i + 1;
                List<Workpiece> group = groups.get(i);

                tasks.add(executor.submit(() -> {
                    SawMill sawMill = new SawMill();
                    CsvWriter csvWriter = new CsvWriter();

                    logger.info(
                            "Группа {}: начало обработки, заготовок: {}, поток: {}",
                            groupNumber,
                            group.size(),
                            Thread.currentThread().getName()
                    );

                    Map<TreeType, Integer> result =
                            sawMill.process(group);

                    String outputPath =
                            "result_group_" + groupNumber + ".csv";

                    csvWriter.write(result, outputPath);

                    logger.info(
                            "Группа {}: результат записан в {}",
                            groupNumber,
                            outputPath
                    );

                    return null;
                }));
            }

            for (Future<?> task : tasks) {
                task.get();
            }
        } finally {
            executor.shutdown();
        }
    }
}