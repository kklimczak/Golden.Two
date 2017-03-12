package algorithm;

public class DESX extends DES {
    private DES des;
    private byte[] keyFirst, keySecond, msg;

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
        generateKeys();
    }

    public void run(boolean ifEncrypt){
        if(ifEncrypt)
            encrypt();
        else
            decrypt();
    }

    private void decrypt() {
        msg = des.doXORBytes(msg, keySecond);
        des.setMsg(msg);
        des.run(false);
        msg = des.getMsg();
        msg = des.doXORBytes(keyFirst, msg);
    }

    private void encrypt() {
        msg = des.doXORBytes(msg, keyFirst);
        des.setMsg(msg);
        des.run(true);
        msg = des.getMsg();
        msg = des.doXORBytes(keySecond, msg);
    }

    private void generateKeys() {
        keyFirst = "qwertyui".getBytes();
        keySecond = "asdfghjk".getBytes();
    }


}
