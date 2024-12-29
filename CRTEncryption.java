import java.math.BigInteger;
import java.util.Random;

public class CRTEncryption {

    public static BigInteger modularInverse(BigInteger a, BigInteger b) {
        BigInteger b0 = b;
        BigInteger x0 = BigInteger.ZERO, x1 = BigInteger.ONE;

        while (a.compareTo(BigInteger.ONE) > 0) {
            BigInteger q = a.divide(b);
            BigInteger temp = b;
            b = a.mod(b);
            a = temp;

            temp = x0;
            x0 = x1.subtract(q.multiply(x0));
            x1 = temp;
        }

        return x1.compareTo(BigInteger.ZERO) < 0 ? x1.add(b0) : x1;
    }

    public static BigInteger chineseRemainder(BigInteger[] n, BigInteger[] a) {
        BigInteger N = BigInteger.ONE;
        for (BigInteger ni : n) {
            N = N.multiply(ni);
        }

        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < n.length; i++) {
            BigInteger Ni = N.divide(n[i]);
            BigInteger Mi = modularInverse(Ni, n[i]);
            result = result.add(a[i].multiply(Ni).multiply(Mi));
        }

        return result.mod(N);
    }

    public static BigInteger[] encrypt(BigInteger message, BigInteger[] moduli, BigInteger salt) {
        BigInteger maskedMessage = message.xor(salt);
        BigInteger[] encrypted = new BigInteger[moduli.length];

        for (int i = 0; i < moduli.length; i++) {
            encrypted[i] = maskedMessage.mod(moduli[i]);
        }

        return encrypted;
    }

    public static BigInteger decrypt(BigInteger[] encrypted, BigInteger[] moduli, BigInteger salt) {
        BigInteger maskedMessage = chineseRemainder(moduli, encrypted);
        return maskedMessage.xor(salt);
    }

    public static void main(String[] args) {

        BigInteger[] moduli = {
                new BigInteger("1000003"),
                new BigInteger("1000033"),
                new BigInteger("1000037")
        };


        BigInteger message = new BigInteger("123456789012345");


        Random rand = new Random();
        BigInteger salt = new BigInteger(16, rand);

        System.out.println("Original Message: " + message);
        System.out.println("Salt: " + salt);

        BigInteger[] encryptedMessage = encrypt(message, moduli, salt);

        System.out.println("Encrypted message:");
        for (BigInteger encrypted : encryptedMessage) {
            System.out.println(encrypted);
        }

        BigInteger decryptedMessage = decrypt(encryptedMessage, moduli, salt);


        System.out.println("Decrypted message: " + decryptedMessage);

        if (decryptedMessage.equals(message)) {
            System.out.println("Decryption successful!");
        } else {
            System.out.println("Decryption failed!");
        }
    }
}