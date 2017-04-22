import algorithm.MHCipher;
import algorithm.SimpleKeyGenerator;
import examples.Knapsack;
import examples.Plecakowy;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
//        runKnapsack();
//        runPlecakowy();
        SimpleKeyGenerator keyGenerator = new SimpleKeyGenerator();
        MHCipher cipher = new MHCipher(keyGenerator);
    }


    private static void runPlecakowy() {
        Plecakowy p = new Plecakowy();

        BigInteger[] encrypt = p.encrypt("hello hello hello hello hello hello hello hello hello hello hello ".getBytes());
        System.out.print("SZYFR: ");
        System.out.println(Arrays.toString(encrypt));
        byte[] decrypt = p.decrypt(encrypt);
        System.out.print("PO ODSZYFROWANIU: ");
        System.out.println(new String(decrypt));

    }

    private static void runKnapsack() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Knapsack Algorithm Test\n");

        Knapsack ks = new Knapsack();

        System.out.println("Enter number of elements ");
        int n = scan.nextInt();

        int[] wt = new int[n + 1];
        int[] val = new int[n + 1];

        System.out.println("\nEnter weight for "+ n +" elements");
        for (int i = 1; i <= n; i++)
            wt[i] = scan.nextInt();
        System.out.println("\nEnter value for "+ n +" elements");
        for (int i = 1; i <= n; i++)
            val[i] = scan.nextInt();

        System.out.println("\nEnter knapsack weight ");
        int W = scan.nextInt();

        ks.solve(wt, val, W, n);
    }
}
