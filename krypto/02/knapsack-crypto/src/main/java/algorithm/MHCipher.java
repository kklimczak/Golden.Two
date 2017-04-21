package algorithm;

import algorithm.keyGen.KeyGenerator;

public class MHCipher {
    private KeyGenerator keyGen;

    public MHCipher(KeyGenerator keyGen) {
        this.keyGen = keyGen;
    }
}
