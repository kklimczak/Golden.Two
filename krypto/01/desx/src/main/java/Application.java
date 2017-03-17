import algorithm.*;

import java.util.Arrays;
import java.util.Scanner;

public class Application {

    private static DESX desx;

    public static void main(String[] args) {
        desx = new DESX();

        programStart();
    }

    private static void programStart() {
        Scanner scanner = new Scanner(System.in);
        int option = 4;
        while(option != 0){
            System.out.println(
                            "[1] Encrypt message\n" +
                            "[2] Decrypt message\n" +
                            "[3] Display message as array\n" +
                            "[4] Display message as string\n" +
                            "[5] Display DES key\n" +
                            "[6] Display DESX first key\n" +
                            "[7] Display DESX second key\n" +
                            "[8] Set new message\n" +
                            "[0] EXIT\n"
            );
            option = scanner.nextInt();
            switch (option){
                case 1:
                    desx.run(true);
                    break;
                case 2:
                    desx.run(false);
                    break;
                case 3:
                    System.out.print("Message as array: ");
                    displayAsArray(desx.getMsg());
                    break;
                case 4:
                    System.out.print("Message as string: ");
                    displayAsString(desx.getMsg());
                    break;
                case 5:
                    System.out.print("Key: ");
                    displayAsString(desx.getKey());
                    break;
                case 6:
                    System.out.print("First key: ");
                    displayAsString(desx.getKeyFirst());
                    break;
                case 7:
                    System.out.print("Second key: ");
                    displayAsString(desx.getKeySecond());
                case 8:
                    System.out.print("New message: ");
                    scanner.nextLine();
                    String msg = scanner.nextLine();
                    if(msg.length() != 8)
                        System.out.println("Wrong message size (8 bytes)");
                    else
                        desx.setMsg(msg);
                    break;
                case 0:
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("WRONG OPTION");
            }
        }
    }

    private static void displayAsArray(byte[] input){
        System.out.println(Arrays.toString(input));
    }

    private static void displayAsString(byte[] input){
        System.out.println(new String(input));
    }
}
