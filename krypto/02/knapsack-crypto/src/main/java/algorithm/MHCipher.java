package algorithm;

import lombok.Getter;
import lombok.Setter;

import static javax.swing.UIManager.getInt;

public class MHCipher {
    @Getter
    @Setter
    private SimpleKeyGenerator keyGen;
    private long[] privateKey, publicKey;

    public MHCipher(SimpleKeyGenerator keyGen) {
        this.keyGen = keyGen;
        generateKeys();
    }

    public long encrypt(String message) {
        int len = message.length();
        if (len <= 0)
            return 0;

        long output = translateChar(message.charAt(0));
        for (int i = 1; i < len; i++) {
            output += translateChar(message.charAt(i));
        }
        return output;
    }

    public String decrypt(long cipher) {

        return null;
    }

    private void generateKeys() {
        this.privateKey = keyGen.getPrivateKey();
        this.publicKey = keyGen.getPublicKey();
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
