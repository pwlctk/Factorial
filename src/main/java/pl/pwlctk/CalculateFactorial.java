package pl.pwlctk;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class CalculateFactorial {

    static String calculateFactorialSingleThreading(String number) {
        BigInteger n = new BigInteger(number);
        BigInteger result = BigInteger.ONE;
        while (!n.equals(BigInteger.ZERO)) {
            result = result.multiply(n);
            n = n.subtract(BigInteger.ONE);
        }
        return result.toString();
    }

    static String calculateFactorialMultiThreading(String number) {
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
                    for (BigInteger j = start.add(BigInteger.ONE); j.compareTo(end) < 0; j = j.add(BigInteger.ONE))
                        num = num.multiply(j);
                    return num;
                }));
            }

            BigInteger result = BigInteger.ONE;
            for (Future<BigInteger> future : results) {
                result = result.multiply(future.get());
            }
            return result.toString();

        } catch (Exception e) {
            throw new AssertionError(e);
        } finally {
            service.shutdown();
        }
    }


}




