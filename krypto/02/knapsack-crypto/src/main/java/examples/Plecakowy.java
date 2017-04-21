package examples;

import java.lang.*;
import java.util.*;
import java.math.BigInteger;

public class Plecakowy {
    class PlecakowyKeyException extends Exception {
        public PlecakowyKeyException(String msg) {
            super(msg);
        }
    }


    int keyLen = 128; //ta wartość daje długość d=256
    int ilBitowSklKlucza = keyLen / 8;//ilość bitów w każdej z 8 liczb klucza (zmieniane są od ilBitowSklKlucza-3 do ilBitowSklKlucza+4

    BigInteger kluczPrywatny[] = new BigInteger[8];
    BigInteger kluczPubliczny[] = new BigInteger[8];
    String kluczPrywatnyString = "";
    String kluczPublicznyString = "";
    BigInteger M;
    BigInteger W;
    BigInteger odwrW;

    public Plecakowy() {
        generateKey();
    }


    public void generateKey() {
        M = BigInteger.probablePrime(ilBitowSklKlucza + 100, new Random());
        W = BigInteger.probablePrime(ilBitowSklKlucza, new Random());
        while (true)
            if (W.gcd(M).equals(BigInteger.ONE)) break;
            else W = W.nextProbablePrime();
        odwrW = W.modInverse(M);
        BigInteger suma = new BigInteger("0");
        kluczPrywatnyString = "";
        kluczPublicznyString = "";
        for (int i = 0; i < 8; i++) {
            BigInteger liczba = new BigInteger(ilBitowSklKlucza - 8 + (2 * i), new Random());
            while (liczba.compareTo(suma) < 1) liczba = new BigInteger(ilBitowSklKlucza - 8 + (2 * i), new Random());
            suma.add(liczba);
            kluczPrywatny[i] = liczba;
            kluczPubliczny[i] = liczba.multiply(W).mod(M);
            kluczPrywatnyString += kluczPrywatny[i].toString(16) + ",";
            kluczPublicznyString += kluczPubliczny[i].toString(16) + ",";
        }
    }


    public BigInteger[] encrypt(byte[] message) {
        BigInteger[] cipher = new BigInteger[message.length];
        for (int n = 0; n < message.length; n++)//pętla bo poszczególnych bajtach wiadomości
        {
            cipher[n] = new BigInteger("0");
            for (int i = 0; i < 8; i++)//pętla po poszczególnych bitach każdego bajta wiadomości
                if (isBitSet(message[n], i)) cipher[n] = cipher[n].add(kluczPubliczny[i]);
        }
        return cipher;
    }

    public String encryptFromStringToString(String message) {
        StringBuilder str = new StringBuilder();
        BigInteger[] bi_table = encrypt(message.getBytes());
        for (BigInteger aBi_table : bi_table)
            str.append(aBi_table).append("\n");
        return str.toString();
    }

    public byte[] decrypt(BigInteger[] cipher) {
        byte wynik[] = new byte[cipher.length];
        for (int n = 0; n < cipher.length; n++)//pętla bo poszczególnych bajtach wiadomości
        {
            cipher[n] = cipher[n].multiply(odwrW).mod(M);
            wynik[n] = 0;
            byte pom = 0;
            byte pomocnicza[] = new byte[8];
            for (int i = 0; i < 8; i++) pomocnicza[i] = 0;
            for (int i = 7; i >= 0; i--)//pętla po poszczególnych bitach każdego bajta wiadomości
            {
                if (cipher[n].compareTo(kluczPrywatny[i]) < 0) continue;
                else {
                    cipher[n] = cipher[n].subtract(kluczPrywatny[i]);
                    pomocnicza[i] = 1;
                }
            }
            for (int j = 0; j < 8; j++) pom += Math.pow(2, j) * pomocnicza[j];
            wynik[n] = pom;
        }
        return wynik;
    }


    public String decryptFromStringToString(String cipher) {

        String[] wiersze = cipher.split("\n");
        BigInteger[] bi_table = new BigInteger[wiersze.length];
        for (int i = 0; i < wiersze.length; i++)
            bi_table[i] = new BigInteger(wiersze[i]);
        byte[] pom = decrypt(bi_table);//new byte[wiersze.length];
        return new String(pom);
    }

    private boolean isBitSet(byte bajt, int bit) {
        return (bajt & (1 << bit)) != 0;
    }

}//klasa Plecakowy