package algorithm.keyGen.impl;

import algorithm.keyGen.KeyGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleKeyGenerator implements KeyGenerator {

    private int keySize = 8;
    private long[] privateKey, publicKey;
    private long multiplier, modulus;

    public SimpleKeyGenerator() {
        this.multiplier = 1110;
        this.modulus = 4141;
        generateKeys();
    }

    public SimpleKeyGenerator(long multiplier, long modulus) {
        this.multiplier = multiplier;
        this.modulus = modulus;
        generateKeys();
    }

    @Override
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
