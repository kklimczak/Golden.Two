package algorithm;

import lombok.*;

@Data
@AllArgsConstructor
public class SimpleKeyGenerator {

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private long[] privateKey;

    private long[] publicKey;
    private int keySize = 8;
    private long multiplier, modulus;

    public SimpleKeyGenerator() {
        this.multiplier = 4571;
        this.modulus = 7187;
        generateKeys();
    }

    public SimpleKeyGenerator(long multiplier, long modulus) {
        this.multiplier = multiplier;
        this.modulus = modulus;
        generateKeys();
    }

    public void generateKeys() {
        privateKey = generatePrivateKey();
        publicKey = generatePublicKey();
    }

    private long[] generatePrivateKey() {
        long[] newPrivateKey = new long[keySize];
        long keyElement = 1;
        for (int i = 0; i < newPrivateKey.length; i++) {
            keyElement *= 2;
            newPrivateKey[i] = keyElement;
        }
        return newPrivateKey;
    }

    private long[] generatePublicKey() {
        long[] newPublicKey = new long[keySize];
        for (int i = 0; i < privateKey.length; i++) {
            newPublicKey[i] = (privateKey[i] * multiplier) % modulus;
        }
        return newPublicKey;
    }
}
