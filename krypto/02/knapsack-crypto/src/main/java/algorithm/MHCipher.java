package algorithm;

import lombok.Getter;
import lombok.Setter;

public class MHCipher {
    @Getter
    @Setter
    private SimpleKeyGenerator keyGen;
    private long[] privateKey, publicKey;

    public MHCipher(SimpleKeyGenerator keyGen) {
        this.keyGen = keyGen;
        generateKeys();
    }

    private void generateKeys() {
        this.privateKey = keyGen.getPrivateKey();
        this.publicKey = keyGen.getPublicKey();
    }

    public long[] encrypt(String message) {
        int len = message.length();
        if (len <= 0)
            return null;

        long[] output = new long[len];
        output[0] = translateChar(message.charAt(0));
        for (int i = 1; i < len; i++) {
            output[i] = translateChar(message.charAt(i));
        }
        return output;
    }

    public String decrypt(long[] cipher) {
        StringBuilder str = new StringBuilder();
        long inv = inverse();

        for (long element : cipher) {
            str.append((char) xlateBack(((inv * element) % keyGen.getModulus())));
        }

        return str.toString();
    }

    private int xlateBack(long x) {
        int m = 128;
        int ch = 0;
        for (int i = 7; i >= 0 && x > 0; i--) {
            if (privateKey[i] <= x) {
                x -= privateKey[i];
                ch += m;
            }
            m /= 2;
        }
        return ch;
    }

    private long inverse() {
        long f;
        long multiplier = keyGen.getMultiplier();
        long modulus = keyGen.getModulus();

        for (int k = 1; k < modulus; k++) {
            f = 0;
            for (int j = 0; j < modulus; j++) {
                if (((k * ((multiplier * j) % modulus)) % modulus) == j) {
                    f += 1;
                } else {
                    break;
                }
            }
            if (f == modulus) {
                return k;
            }
        }
        return 0;
    }

    private long translateChar(char x) {
        long sum = 0;

        for (int i = 0; i < 8; i++) {
            if (2 * (x / 2) != x) sum += publicKey[i];
            x /= 2;
        }
        return sum;
    }
}
