package algorithm;

public class DES {

    private Values v;
    private byte[] key, msg;
    private byte[][] subKeys;
    private byte[] leftSide, rightSide;

    DES() {
        this.v = new Values();
        generateDefaultMessageAndKey();
        generateSubkeys();
        leftSide = null;
        rightSide = null;
    }

    public byte[] getMsg(){
        return msg;
    }

    void setMsg(byte[] msg) {
        this.msg = msg;
    }

    public void setMsg(String msg) {
        msg = makeProperMsgLength(msg);
        this.msg = msg.getBytes();
    }

    /**
     * W przypadku gdy dlugosc wiadomosc jest nieprawidlowa (niepodzielna przez 8),
     * dodawana jest na koniec wiadomosci odpowiednia ilosc bialych znakow (spacji)
     */
    private String makeProperMsgLength(String msg) {
        int overflowBytesNumb = msg.length() % 8;
        if(overflowBytesNumb != 0){
            for (int i = 0; i < 8 - overflowBytesNumb; i++) {
                msg += " ";
            }
        }
        return msg;
    }

    public byte[] getKey(){
        return key;
    }

    public void run(boolean ifEncrypt) {

        initPermutation();
        divideMsg();
        proceedIterations(ifEncrypt);

    }

    /**
     * Glowna petla algorytmu. Przeksztalca prawy blok wiadomosci funkcja f,
     * a nastepnie laczy sie z lewym blokiem operacja XOR.
     * Petla wykonuje sie 16 razy po czym blok koncowy poddawany jest permtacji koncowej.
     *
     * Funkcja f zawiera w sobie:
     *  1. permutacje rozszerzajaca - prawy blok danych z 32bitow ma ich teraz 48
     *  2. operacja XOR z kluczem wygenerowanym dla danej iteracji
     *  3. kazde kolejne 6 bitow jest adresem do wartosci w SBoxach.
     *      Wartosci te sa odczytywane i zamieniane na zapis dwojkowy
     *  4.Wynik z SBoxow poddaje sie permutacji P (PBox)
     *
     * Ostatecznie laczone zostaja polowy wiadomosci, ktore nastepnie poddawane sa permutacji koncowej
     *
     * @param ifEncrypt true oznacza szyfrowanie wiadomosci, false jej odszyfrowanie
     */
    private void proceedIterations(boolean ifEncrypt) {
        int iteration = subKeys.length;
        for (int i = 0; i < iteration; i++) {
            byte[] oldRightSide = rightSide;
            rightSide = shuffleBits(rightSide, v.expantionPermutation);
            if (ifEncrypt)
                rightSide = doXORBytes(rightSide, subKeys[i]);
            else
                rightSide = doXORBytes(rightSide, subKeys[iteration - i - 1]);

            performSBoxPBoxAndXOR();
            leftSide = oldRightSide;
        }

        composeOutput();
    }

    /**
     * Ostatni etap algorytmu. Laczone zostaja polowki wiadomosci, ktore potem poddane sa permutacji koncowej
     */
    private void composeOutput() {
        msg = concatBytes(rightSide, v.startPermutation.length/2, leftSide, v.startPermutation.length/2);
        msg = shuffleBits(msg, v.endPermutation);
    }

    private void performSBoxPBoxAndXOR() {
        rightSide = doSBox(rightSide);
        rightSide = shuffleBits(rightSide, v.pBox);
        rightSide = doXORBytes(leftSide, rightSide);
    }

    /**
     * Kazdy 6-bitowy fragment jest przeksztalcany przez jeden z osmiu S-BOXow.
     * W kazdym bajcie ignorowane sa dwa ostatnie bity (LSB)
     * Pierwszy i ostatni bit danych okresla wiersz, a pozostale bity kolumne S-BOXa.
     * Po odczytaniu dwoch kolejnych wartosci, liczby te sa zamieniane na ciag bitow i scalane.
     */
    private byte[] doSBox(byte[] input) {
        input = splitBytes(input);
        byte[] output = new byte[input.length / 2];

        for (int i = 0, firstSBoxValue = 0; i < input.length; i++) {
            byte sixBitsFragment = input[i];
            int rowNumb = 2 * (sixBitsFragment >> 7 & 0x0001) + (sixBitsFragment >> 2 & 0x0001);
            int columnNumb = sixBitsFragment >> 3 & 0x000F;
            int secondSBoxValue = v.sBox[64 * i + 16 * rowNumb + columnNumb];
            if (i % 2 == 0)
                firstSBoxValue = secondSBoxValue;
            else
                output[i / 2] = createByteFromSBoxValues(firstSBoxValue, secondSBoxValue);
        }
        return output;
    }

    /**
     * Po przekszalceniu liczb na system binarny, scala ze soba te wartosci
     */
    private byte createByteFromSBoxValues(int firstSBoxValue, int secondSBoxValue) {
        return (byte) (16 * firstSBoxValue + secondSBoxValue);
    }

    /**
     * Tworzy 8 bajtowa tablice z 6 bajtowej.
     * Dwa ostatnie bity w kazdym bajcie sa bezuzyteczne.
     * Ma to na celu odseparowanie od siebie grup szesciobitywych potrzebnych przy operacjach z SBoxami
     */
    private byte[] splitBytes(byte[] in) {
        int numOfBytes = 8;
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

    /**
     * Dzieli wiadomosc na dwie rowne czesci
     */
    private void divideMsg() {
        int howManyBits = (msg.length * 8) / 2;

        leftSide = splitBytes(msg, 0, howManyBits);
        rightSide = splitBytes(msg, howManyBits, howManyBits);
    }

    /**
     * 64-bitowy blok danych wejsciowych poddawany jest permutacji wstępnej
     */
    private void initPermutation() {
        msg = shuffleBits(msg, v.startPermutation);
    }

    /**
     * Generuje domyslny klucz oraz wiadomosc
     * Nalezy pamietac, ze wartosci te w tym miejscu MUSZA zawierac rowno osiem znakow (64 bity)
     */
    private void generateDefaultMessageAndKey() {
        msg = "aMessage".getBytes();
        key = "12345678".getBytes();
    }

    /**
     * Generowanie podkluczy.
     * Etapy algorytmu:
     *  1. Permutacja PC1 na kluczu pierwotnym
     *  2. Dane dzielone sa na dwa bloki - c i d
     *  3. Kazdy z blokow przesuwany jest w lewo.
     *     Ilosc przesuniecia okreslona jest w tabeli
     *  4. Bloki sa ze soba spowrotem laczone i poddane permutacji PC2 - tj. permutacji zwezajacej
     *  5. Wynikiem kazdej iteracji petli jest podklucz. Jego wartosc zostaje zapisana do tablicy subKeys
     */
    private void generateSubkeys(){
        byte[] keyPC1 = shuffleBits(key,v.keyPermutation);

        byte[] c = splitBytes(keyPC1, 0, 28);
        byte[] d = splitBytes(keyPC1, 28, 28);

        subKeys = new byte[v.shifts.length][];
        for (int i = 0; i < v.shifts.length; i++) {
            c = leftShift(c, v.shifts[i]);
            d = leftShift(d, v.shifts[i]);
            byte[] cd = concatBytes(c, 28, d, 28);
            subKeys[i] = shuffleBits(cd, v.taperPermutation);
        }
    }

    byte[] concatBytes(byte[] a, int aLength, byte[] b, int bLength) {
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
        int halfKeySize = 28;
        for (int i = 0; i < halfKeySize; i++) {
            boolean bit = checkBit(input, (i + shiftNumb) % halfKeySize);
            setBit(out, i, bit);
        }
        return out;
    }

    byte[] splitBytes(byte[] input, int index, int length) {
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
        if(bit)
            data[whichByte] |= 0x80 >> whichBit;
        else
            data[whichByte] &= ~(0x80 >> whichBit);

    }
}
