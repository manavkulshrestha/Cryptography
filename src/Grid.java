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
    private int[] columns;
    private int[] rows;
    private char[][] grid;

    public Grid(boolean random) {
        this.columns = new int[10];
        this.rows = new int[2];
        this.grid = new char[3][10];

        for(int i=0; i<columns.length; i++)
            columns[i] = i;

        grid[0] = toCharArray("ETAO N RIS");
        if(random) {
            shuffle(grid[0]);
            shuffle(columns);
        }

        for(int i=0, j=0; i<grid[0].length; i++)
            if(grid[0][i] == ' ')
                columns[j++] = columns[i];

        char[] remainingRows = toCharArray("BCDFGHJKLMPQ/UVWXY.Z");
        if(random)
            shuffle(remainingRows);

        for(int i=0; i<grid[1].length; i++)
            grid[1][i] = remainingRows[i];

        for(int i=0; i<grid[1].length; i++)
            grid[2][i] = remainingRows[grid[1].length+i];
    }

    public Grid(String fileName) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        char[] temp = toCharArray(br.readLine());
        for(int i=0; i<temp.length; i++)
            columns[i] = temp[i]-'0';

        temp = toCharArray(br.readLine());
        rows[0] = temp[0]-'0';
        rows[1] = temp[1]-'0';

        for(int i=0; i<3; i++)
            grid[i] = toCharArray(br.readLine());
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