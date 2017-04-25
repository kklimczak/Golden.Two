package algorithm;

import exception.MessageException;
import lombok.Data;

@Data
public class MHCipher {
    private SimpleKeyGenerator keyGen;
    private long[] privateKey, publicKey;

    public MHCipher(SimpleKeyGenerator keyGen) {
        this.keyGen = keyGen;
        generateKeys();
    }

    private void generateKeys() {
        privateKey = keyGen.generateDefaultPrivateKey();
        publicKey = keyGen.generatePublicKey(privateKey);
    }

    public void generatePublicKey() {
        publicKey = keyGen.generatePublicKey(privateKey);
    }

    public long[] encrypt(String message) throws MessageException {
        int length = message.length();
        if (length <= 0)
            throw new MessageException("Message is empty");

        long[] output = new long[length];
        for (int i = 0; i < length; i++) {
            output[i] = calculateEncryptedChar(message.charAt(i));
        }
        return output;
    }

    public String decrypt(long[] cipher) {
        StringBuilder stringBuilder = new StringBuilder();
        long multiInverse = calculateMultiplierModuloInverse();

        for (long element : cipher) {
            long encryptedChar = (multiInverse * element) % keyGen.getModulus();
            stringBuilder.append(decryptChar(encryptedChar));
        }

        return stringBuilder.toString();
    }

    private char decryptChar(long encryptedChar) {
        int MSB = 0x80;
        int outputChar = 0;
        for (int i = 7; i >= 0 && encryptedChar > 0; i--) {
            if (encryptedChar >= privateKey[i]) {
                encryptedChar -= privateKey[i];
                outputChar += MSB;
            }
            MSB /= 2;
        }
        return (char) outputChar;
    }

    private long calculateMultiplierModuloInverse() {
        long[] response = extendedEuclid(keyGen.getMultiplier(), keyGen.getModulus());
        return response[0];
    }

    private long[] extendedEuclid(long c, long d) {
        if (d == 0)
            return new long[]{1, 0};

        long[] response = extendedEuclid(d, c % d);
        long a = response[1];
        long b = response[0] - (c / d) * response[1];
        return new long[]{a, b};
    }

    private long calculateEncryptedChar(char x) {
        long output = 0;
        for (int i = 0; i < 8; i++) {
            if (isOdd(x)) {
                output += publicKey[i];
            }
            x /= 2;
        }
        return output;
    }

    private boolean isOdd(char x) {
        return x % 2 != 0;
    }
}
