/*
    Manav Kulshrestha
    Cryptography.java
    5/21/18
*/

import java.io.*;
import java.util.Scanner;

public class Cryptography {
    public static void main(String args[]) throws IOException {
        Scanner in = new Scanner(System.in);
        menu input;

        do {
            pMenu();
            System.out.print('\n');
            switch (input = menu.values()[in.nextInt()]) {
                case RANDOM_GRID:

                    break;
                case STANDARD_GRID:
                    break;
                case LOAD_GRID:
                    break;
                case ENCRYPT_TEXT:
                    break;
                case ENCRYPT_FILE:
                    break;
                case DECRYPT_TEXT:
                    break;
                case DECRYPT_FILE:
                    break;
                case QUIT:
                    break;
            }
        } while(input != menu.QUIT);
    }

    public static void pMenu() {
        for(menu option: menu.values())
            System.out.printf("%d. %s%n", option.ordinal(), option.toString().replace('_', ' '));
    }

    public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String ret = br.readLine(), line;

        while((line = br.readLine()) != null)
            ret += "\n"+line;

        return ret;
    }

    public static void writeToFile(String fileName, String data) throws IOException {
        PrintWriter out = new PrintWriter(new File(fileName));
        out.println(data);
        out.close();
    }

}

enum menu {RANDOM_GRID, STANDARD_GRID, LOAD_GRID, ENCRYPT_TEXT, ENCRYPT_FILE, DECRYPT_TEXT, DECRYPT_FILE, QUIT}