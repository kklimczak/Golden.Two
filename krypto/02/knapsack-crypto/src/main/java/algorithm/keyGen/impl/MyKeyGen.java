package algorithm.keyGen.impl;

import algorithm.keyGen.KeyGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyKeyGen implements KeyGenerator {

    private int keySize = 8;

    @Override
    public long[] generatePrivateKey() {
        long[] output = new long[keySize];
        for (int i = 0; i < output.length; i++) {
            output[i] = i*i;
        }
        return output;
    }
}
