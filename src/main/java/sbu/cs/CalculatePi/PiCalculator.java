package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PiCalculator {

    public static class calculatePI implements Runnable {
        static MathContext mc = new MathContext(1100);
        int n;
        static final BigDecimal C = new BigDecimal(2).multiply(new BigDecimal(2).sqrt(mc), mc).divide(new BigDecimal(9801), mc);

        public calculatePI(int n) {
            this.n = n;
        }

        public BigDecimal factorial(int n) {
            BigDecimal temp = BigDecimal.ONE;
            for (int i = 1; i <= n; i++) {
                temp = temp.multiply(BigDecimal.valueOf(i), mc);
            }
            return temp;
        }

        @Override
        public void run() {
            BigDecimal nominator = factorial(4 * n).multiply(new BigDecimal(26390 * n + 1103), mc);
            BigDecimal denominator = factorial(n).pow(4, mc).multiply(new BigDecimal(396).pow(4 * n, mc), mc);
            BigDecimal result = C.multiply(nominator, mc).divide(denominator, mc);
            addToSum(result);
        }
    }

    private static BigDecimal sum = BigDecimal.ZERO;

    public static synchronized void addToSum(BigDecimal num) {
        sum = sum.add(num);
    }

    public String calculate(int floatingPoint) {
        ExecutorService threadpool = Executors.newFixedThreadPool(4);

        sum = BigDecimal.ZERO;

        int numberOfTerms = 200;
        for (int i = 0; i <= numberOfTerms; i++) {
            calculatePI task = new calculatePI(i);
            threadpool.execute(task);
        }

        threadpool.shutdown();

        try {
            threadpool.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored) { }

        return BigDecimal.ONE.divide(sum, new MathContext(1100)).setScale(floatingPoint, RoundingMode.DOWN).toString();
    }

    public static void main(String[] args) {
        PiCalculator p = new PiCalculator();
        System.out.println(p.calculate(7));
    }
}