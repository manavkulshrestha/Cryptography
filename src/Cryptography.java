/*
    Manav Kulshrestha
    Cryptography.java
    5/21/18
*/

import java.io.*;
import java.util.Scanner;

public class Cryptography {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        menu input;
        Grid grid = null;

        final String SAVED = "Saved!", FILE_PROMPT = "Enter filename (with extention): ",
                NO_GRID = "No grid loaded.", CREATED = "Created!", SAVE_ERROR = "Error saving.",
                LOADED = "Loaded!", TEXT_PROMPT_ONE_LINE = "Enter text (in one line): ",
                DECRYPTED_SHOW = "Decrypted string: ", ENCRYPTED_SHOW = "Encrypted string: ",
                TEXT_SHOW = "Text: ", LOAD_ERROR = "Not loaded.";

        do {
            pMenu();
            System.out.print('\n');
            try {
                input = menu.values()[in.nextInt()];
            } catch(Exception e) {
                input = menu.QUIT;
            }
            switch (input) {
                case RANDOM_GRID:
                    grid = new Grid(true);
                    System.out.print(CREATED+"\n"+grid+"\n"+FILE_PROMPT);
                    try {
                        grid.saveToFile(in.next());
                        System.out.print(SAVED);
                    } catch(IOException e) {
                        System.out.print(SAVE_ERROR);
                    }
                    break;
                case STANDARD_GRID:
                    grid = new Grid(false);
                    System.out.print(CREATED+"\n"+grid+"\n"+FILE_PROMPT);
                    try {
                        grid.saveToFile(in.next());
                        System.out.print(SAVED);
                    } catch(IOException e) {
                        System.out.print(SAVE_ERROR);
                    }
                    break;
                case LOAD_GRID:
                    System.out.print(FILE_PROMPT);
                    try {
                        grid = new Grid(in.next());
                        System.out.print(LOADED+"\n"+grid);
                    } catch (IOException e) {
                        System.out.print(NO_GRID);
                    }
                    break;
                case VIEW_GRID:
                    System.out.print((grid == null) ? NO_GRID : LOADED+"\n"+grid);
                    break;
                case ENCRYPT_TEXT:
                    if(grid == null) {
                        System.out.print(NO_GRID);
                        break;
                    }
                    System.out.print(TEXT_PROMPT_ONE_LINE);
                    in.nextLine();
                    String encryptedText = grid.encryptString(in.nextLine());
                    System.out.print(ENCRYPTED_SHOW+encryptedText+"\n"+FILE_PROMPT);
                    try {
                        writeToFile(in.next(), encryptedText);
                        System.out.print(SAVED);
                    } catch(IOException e) {
                        System.out.print(SAVE_ERROR);
                    }
                    break;
                case ENCRYPT_FILE:
                    if(grid == null) {
                        System.out.print(NO_GRID);
                        break;
                    }
                    String toEncrypt, encrypted;
                    System.out.print(FILE_PROMPT);
                    try {
                        encrypted = grid.encryptString(toEncrypt = readFile(in.next()));
                        System.out.print(LOADED+"\n"+TEXT_SHOW+toEncrypt+"\n"+ENCRYPTED_SHOW+encrypted+"\n"+FILE_PROMPT);
                        try {
                            writeToFile(in.next(), encrypted);
                            System.out.print(SAVED);
                        } catch(IOException e) {
                            System.out.print(SAVE_ERROR);
                        }
                    } catch(IOException e) {
                        System.out.print(LOAD_ERROR);
                    }
                    break;
                case DECRYPT_TEXT:
                    if(grid == null) {
                        System.out.print(NO_GRID);
                        break;
                    }
                    System.out.print(TEXT_PROMPT_ONE_LINE);
                    in.nextLine();
                    String decryptedText = grid.decryptString(in.nextLine());
                    System.out.print(DECRYPTED_SHOW+decryptedText+"\n"+FILE_PROMPT);
                    try {
                        writeToFile(in.next(), decryptedText);
                        System.out.print(SAVED);
                    } catch(IOException e) {
                        System.out.print(SAVE_ERROR);
                    }
                    break;
                case DECRYPT_FILE:
                    if(grid == null) {
                        System.out.print(NO_GRID);
                        break;
                    }
                    String toDecrypt, decrypted;
                    System.out.print(FILE_PROMPT);
                    try {
                        decrypted = grid.decryptString(toDecrypt = readFile(in.next()));
                        System.out.print(LOADED+"\n"+TEXT_SHOW+toDecrypt+"\n"+DECRYPTED_SHOW+decrypted+"\n"+FILE_PROMPT);
                        try {
                            writeToFile(in.next(), decrypted);
                            System.out.print(SAVED);
                        } catch(IOException e) {
                            System.out.print(SAVE_ERROR);
                        }
                    } catch(IOException e) {
                        System.out.print(LOAD_ERROR);
                    }
                    break;
                case QUIT:
                    break;
                default:
                    break;
            }
            System.out.println('\n');
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

enum menu {RANDOM_GRID, STANDARD_GRID, LOAD_GRID, VIEW_GRID, ENCRYPT_TEXT, ENCRYPT_FILE, DECRYPT_TEXT, DECRYPT_FILE, QUIT}