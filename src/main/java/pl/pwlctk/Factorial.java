package pl.pwlctk;

import java.math.BigInteger;

class Factorial {

    static String calculateFactorial(String number) {
        BigInteger n = new BigInteger(number);
        BigInteger result = BigInteger.ONE;
        while (!n.equals(BigInteger.ZERO)) {
            result = result.multiply(n);
            n = n.subtract(BigInteger.ONE);
        }
        return result.toString();
    }
}
