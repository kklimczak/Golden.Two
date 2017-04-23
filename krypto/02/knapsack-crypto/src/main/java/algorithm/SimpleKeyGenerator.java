package algorithm;

import lombok.*;

@Data
@AllArgsConstructor
public class SimpleKeyGenerator {

    private int keySize = 8;
    private long multiplier, modulus;

    public SimpleKeyGenerator() {
        this(4571L, 7187L);
    }

    public SimpleKeyGenerator(long multiplier, long modulus) {
        this.multiplier = multiplier;
        this.modulus = modulus;
    }

    long[] generateDefaultPrivateKey() {
        long[] newPrivateKey = new long[keySize];
        long keyElement = 1;
        for (int i = 0; i < newPrivateKey.length; i++) {
            keyElement *= 2;
            newPrivateKey[i] = keyElement;
        }
        return newPrivateKey;
    }

    long[] generatePublicKey(long[] privateKey) {
        long[] newPublicKey = new long[keySize];
        for (int i = 0; i < privateKey.length; i++) {
            newPublicKey[i] = (privateKey[i] * multiplier) % modulus;
        }
        return newPublicKey;
    }
}
