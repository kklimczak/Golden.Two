package algorithm;

public class DESX extends DES {
    private final DES des;
    private byte[] keyFirst, keySecond, msg;
    private byte[][] msgPackage;

    public byte[] getKeyFirst() {
        return keyFirst;
    }

    public byte[] getKeySecond() {
        return keySecond;
    }

    public byte[] getMsg(){
        return msg;
    }

    public DESX() {
        des = new DES();
        msg = des.getMsg();
        generateDefaultKeys();
    }

    public void setMsg(String msg){
        des.setMsg(msg);
        this.msg = des.getMsg();
    }

    public void run(boolean ifEncrypt){
        prepareMsgPackages();
        if(ifEncrypt)
            encrypt();
        else
            decrypt();
        concatMsgPackages();
    }

    private void concatMsgPackages() {
        msg = msgPackage[0];
        if(msgPackage.length > 1){
            for (int i = 1; i < msgPackage.length; i++) {
                msg = des.concatBytes(msg, msg.length*8, msgPackage[i], msgPackage[i].length*8);
            }
        }
    }

    /**
     * Dzieli wiadomosc na 64 bitowe paczki
     */
    private void prepareMsgPackages() {
        int msgPartsNUmb = ((msg.length-1) / 8) + 1;
        msgPackage = new byte[msgPartsNUmb][];
        for (int i = 0; i < msgPartsNUmb; i++) {
            msgPackage[i] = des.splitBytes(msg, i*8*8,64);
        }
    }

    /**
     * Jedyna roznica z metoda encrypt() to zamienione miejscami klucze keyFirst i keySecond
     */
    private void decrypt() {
        for (int i = 0; i < msgPackage.length; i++) {
            msgPackage[i] = des.doXORBytes(msgPackage[i], keySecond);
            des.setMsg(msgPackage[i]);
            des.run(false);
            msgPackage[i] = des.getMsg();
            msgPackage[i] = des.doXORBytes(keyFirst, msgPackage[i]);
        }
    }

    /**
     * Wiadomosc (paczka) poddana jest dwom operacjom XOR z kluczami DESX'a.
     * Pierwsza ma miejsce jeszcze przed dzialaniem algorytmu DES.
     * Druga operacja to ostatnia operacja algorytmu na bloku danych.
     */
    private void encrypt() {
        for (int i = 0; i < msgPackage.length; i++) {
            msgPackage[i] = des.doXORBytes(msgPackage[i], keyFirst);
            des.setMsg(msgPackage[i]);
            des.run(true);
            msgPackage[i] = des.getMsg();
            msgPackage[i] = des.doXORBytes(keySecond, msgPackage[i]);
        }
    }

    /**
     * Generuje klucze domyslne.
     * Nalezy pamietac, ze w tym miejscu klucze MUSZA miec dokladnie 8 znakow dlugosci (64 bity)
     */
    private void generateDefaultKeys() {
        keyFirst = "qwertyui".getBytes();
        keySecond = "asdfghjk".getBytes();
    }


}
