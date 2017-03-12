package algorithm;

public class DES {

    private Values v;
    private byte[] key, msg;
    private byte[][] subKeys;
    private byte[] leftSite, rightSite;

    DES() {
        this.v = new Values();
        generateDefaultMessageAndKey();
        generateSubkeys();
        leftSite = null;
        rightSite = null;
    }

    public byte[] getMsg(){
        return msg;
    }

    void setMsg(byte[] msg) {
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg.getBytes();
    }

    public byte[] getKey(){
        return key;
    }

    public void run(boolean ifEncrypt) {

        initPermutation();
        divideMsg();
        proceedIterations(ifEncrypt);

    }

    private void proceedIterations(boolean ifEncrypt) {
        int iteration = subKeys.length;
        for (int i = 0; i < iteration; i++) {
            byte[] oldRightSide = rightSite;
            rightSite = shuffleBits(rightSite, v.expantionPermutation);
            if (ifEncrypt)
                rightSite = doXORBytes(rightSite, subKeys[i]);
            else
                rightSite = doXORBytes(rightSite, subKeys[iteration - i - 1]);

            performSBoxPBoxAndXOR();
            leftSite = oldRightSide;
        }

        composeOutput();
    }

    private void composeOutput() {
        msg = concatenateBits(rightSite, v.startPermutation.length/2, leftSite, v.startPermutation.length/2);
        msg = shuffleBits(msg, v.endPermutation);
    }

    private void performSBoxPBoxAndXOR() {
        rightSite = doSBox(rightSite);
        rightSite = shuffleBits(rightSite, v.pBox);
        rightSite = doXORBytes(leftSite, rightSite);
    }

    private byte[] doSBox(byte[] in) {
        in = splitBytes(in);
        byte[] out = new byte[in.length / 2];
        int lhByte = 0;
        for (int b = 0; b < in.length; b++) {
            byte valByte = in[b];
            int r = 2 * (valByte >> 7 & 0x0001) + (valByte >> 2 & 0x0001);
            int c = valByte >> 3 & 0x000F;
            int hByte = v.sBox[64 * b + 16 * r + c];
            if (b % 2 == 0)
                lhByte = hByte;
            else
                out[b / 2] = (byte) (16 * lhByte + hByte);
        }
        return out;
    }

    private byte[] splitBytes(byte[] in) {
        int numOfBytes = ((8 * in.length - 1) / 6) + 1;
        byte[] out = new byte[numOfBytes];
        for (int i = 0; i < numOfBytes; i++) {
            for (int j = 0; j < 6; j++) {
                boolean val = checkBit(in, (6 * i) + j);
                setBit(out, (8 * i) + j, val);
            }
        }
        return out;
    }

    byte[] doXORBytes(byte[] a, byte[] b) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ b[i]);
        }
        return out;
    }

    private void divideMsg() {
        int size = v.startPermutation.length / 2;

        leftSite = shuffleBits(msg, 0, size);
        rightSite = shuffleBits(msg, size, size);
    }

    private void initPermutation() {
        msg = shuffleBits(msg, v.startPermutation);
    }

    private void generateDefaultMessageAndKey() {
        msg = "aMessage".getBytes();
        key = "12345678".getBytes();
    }

    private void generateSubkeys(){
        byte[] keyPC1 = shuffleBits(key,v.keyPermutation);

        byte[] c = shuffleBits(keyPC1, 0, 28);
        byte[] d = shuffleBits(keyPC1, 28, 28);

        subKeys = new byte[v.shifts.length][];
        for (int i = 0; i < v.shifts.length; i++) {
            c = leftShift(c, v.shifts[i]);
            d = leftShift(d, v.shifts[i]);
            byte[] cd = concatenateBits(c, 28, d, 28);
            subKeys[i] = shuffleBits(cd, v.taperPermutation);
        }
    }

    private byte[] concatenateBits(byte[] a, int aLength, byte[] b, int bLength) {
        byte[] output = prepareOutput(aLength, bLength);

        int i = 0;
        for (; i < aLength; i++) {
            boolean bit = checkBit(a, i);
            setBit(output, i, bit);
        }
        for (int j = 0; j < bLength; j++, i++) {
            boolean bit = checkBit(b, j);
            setBit(output, i, bit);
        }
        return output;
    }

    private byte[] prepareOutput(int aLen, int bLen) {
        int numOfBytes = ((aLen + bLen - 1) / 8) + 1;
        return new byte[numOfBytes];
    }

    private byte[] leftShift(byte[] input, int shiftNumb) {
        byte[] out = new byte[4];
        for (int i = 0; i < 28; i++) {
            boolean bit = checkBit(input, (i + shiftNumb) % 28);
            setBit(out, i, bit);
        }
        return out;
    }

    private byte[] shuffleBits(byte[] input, int index, int length) {
        byte[] output = prepareOutput(length);
        for (int i = 0; i < length; i++) {
            boolean bit = checkBit(input, index + i);
            setBit(output, i, bit);
        }
        return output;
    }

    private byte[] prepareOutput(int length) {
        int bytesNumb = ((length - 1) / 8) + 1;
        return new byte[bytesNumb];
    }

    private byte[] shuffleBits(byte[] input, int[] permTable) {
        byte[] output = prepareOutput(permTable.length);
        for (int i = 0; i < permTable.length; i++) {
            boolean bit = checkBit(input, permTable[i] - 1);
            setBit(output, i, bit);
        }
        return output;
    }

    private boolean checkBit(byte[] data, int index) {
        int whichByte = index / 8;
        int whichBit = index % 8;

        return (data[whichByte] >> (8 - (whichBit + 1)) & 1) == 1;
    }

    private void setBit(byte[] data, int index, boolean bit) {
        int whichByte = index / 8;
        int whichBit = index % 8;
        byte oldByte = data[whichByte];
        oldByte = (byte) (((0xFF7F >> whichBit) & oldByte) & 0x00FF);
        byte newByte = (byte) (((bit ? 1 : 0) << (8 - (whichBit + 1))) | oldByte);
        data[whichByte] = newByte;
    }
}
