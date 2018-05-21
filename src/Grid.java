/*
    Manav Kulshrestha
    Grid.java
    5/21/18
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Grid {
    private int[] rows;
    private char[][] grid;

    public Grid(boolean random) {
        this.rows = new int[2];
        this.grid = new char[10][3];
        char[] temp;

        temp = toCharArray("ETAO N RIS");
        if(random)
            shuffle(temp);
        for(int i=0; i<temp.length; i++)
            grid[i][0] = temp[i];

        temp = toCharArray("BCDFGHJKLMPQ/UVWXY.Z");
        if(random)
            shuffle(temp);

        int len = temp.length/2;
        for(int i=0; i<len; i++)
            grid[i][1] = temp[i];

        for(int i=0; i<len; i++)
            grid[i][2] = temp[len+i];
    }

    public Grid(String fileName) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String rowNums = br.readLine();
        rows[0] = rowNums.charAt(0)-'0';
        rows[1] = rowNums.charAt(1)-'0';

        for(int i=0; i<3; i++) {
            char[] temp = toCharArray(br.readLine());
            for(int j=0; j<10; j++) {
                grid[j][i] = temp[j];
            }
        }
    }


    private char[] toCharArray(String s) {
        char[] ret = new char[s.length()];

        for(int i=0; i<ret.length; i++)
            ret[i] = s.charAt(i);

        return ret;
    }

    private void shuffle(char[] a) {
        for(int i=0; i<a.length; i++) {
            int swapIndex = (int)(Math.random()*a.length);
            char temp = a[i];
            a[i] = a[swapIndex];
            a[swapIndex] = temp;
        }
    }

    private void shuffle(int[] a) {
        for(int i=0; i<a.length; i++) {
            int swapIndex = (int)(Math.random()*a.length);
            int temp = a[i];
            a[i] = a[swapIndex];
            a[swapIndex] = temp;
        }
    }

    public String encryptCharacter(char c) {
        String ret = "";

        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[i].length; j++) {
                if(grid[i][j] == c) {
                    ret += columns[j];
                    if(i > 0)
                        ret = ret+rows[i-1];
                    break;
                }
            }
        }

        return (ret.equals("")) ? ""+c : ret;
    }

    public String encryptString(String s) {
        String ret = "";

        for(int i=0; i<s.length(); i++)
            ret += encryptCharacter(s.charAt(i));

        return ret;
    }

    public String decryptString(String s) {
        char rowOne = (char)('0'+rows[0]), rowTwo = (char)('0'+rows[1]);

        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);

            if(c == rowOne || c == rowTwo)
        }
    }

    public String toString() {
        return Arrays.toString(columns)+"\n"+Arrays.toString(rows)+"\n"+Arrays.toString(grid[0])+"\n"+Arrays.toString(grid[1])+"\n"+Arrays.toString(grid[2]);
    }
}