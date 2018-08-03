package pl.pwlctk;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class CalculateFactorialMultiThreading {

    private static long computeTime;

    static String calculateFactorial(String number) {
        long startTime = System.currentTimeMillis();
        BigInteger processors = BigInteger.valueOf(Runtime.getRuntime().availableProcessors());
        BigInteger n = new BigInteger(number);
        BigInteger batchSize = (n.add(processors.subtract(BigInteger.ONE))).divide(processors);
        ExecutorService service = Executors.newFixedThreadPool(processors.intValue());
        try {
            List<Future<BigInteger>> results = new ArrayList<>();

            for (BigInteger i = BigInteger.ONE; i.compareTo(n) <= 0; i = i.add(batchSize)) {
                final BigInteger start = i;
                final BigInteger end = n.add(BigInteger.ONE).min(i.add(batchSize));
                results.add(service.submit(() -> {
                    BigInteger num = start;
                    for (BigInteger j = start.add(BigInteger.ONE); j.compareTo(end) < 0; j = j.add(BigInteger.ONE)) {
                        if (service.isShutdown()) {
                            break;
                        }
                        num = num.multiply(j);
                    }
                    return num;
                }));
            }

            BigInteger result = BigInteger.ONE;
            for (Future<BigInteger> future : results) {
                try {
                    result = result.multiply(future.get());

                } catch (InterruptedException e) {
                    service.shutdown();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            computeTime = System.currentTimeMillis() - startTime;
            return result.toString();

        } finally {
            service.shutdown();
        }
    }

    static long getComputeTime() {
        return computeTime;
    }
}




