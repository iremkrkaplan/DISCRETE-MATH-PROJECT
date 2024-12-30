
import java.math.BigInteger;
import java.util.Random;

public class EuclideanAproachCRTEncrypiton {


        public static BigInteger modularInverse(BigInteger a, BigInteger b) {
            BigInteger b0 = b;
            BigInteger x0 = BigInteger.ZERO, x1 = BigInteger.ONE;

            while (a.compareTo(BigInteger.ZERO) > 0) {
                BigInteger q = b.divide(a);
                BigInteger temp = a;
                a = b.mod(a);
                b = temp;

                temp = x0;
                x0 = x1.subtract(q.multiply(x0));
                x1 = temp;
            }

            return x1.compareTo(BigInteger.ZERO) < 0 ? x1.add(b0) : x1;
        }

        public static void main(String[] args) {

            BigInteger a = new BigInteger("42");
            BigInteger b = new BigInteger("2017");

            System.out.println("Modüler tersini bul: " + a + " mod " + b);

            BigInteger modInverse = modularInverse(a, b);

            System.out.println("Modüler Ters: " + modInverse);

            if (a.multiply(modInverse).mod(b).equals(BigInteger.ONE)) {
                System.out.println("Sonuç doğru!");
            } else {
                System.out.println("Hatalı sonuç!");
            }
        }
    }
