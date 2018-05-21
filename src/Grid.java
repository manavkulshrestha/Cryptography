/*
    Manav Kulshrestha
    Grid.java
    5/21/18
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public String encryptCharacter(char ch) {
        String ret = "";
        ch = Character.toUpperCase(ch);

        for(int c=0; c<grid.length; c++) {
            for(int r=0; r<grid[c].length; r++) {
                if(grid[c][r] == ch) {
                    ret += c;
                    if(r > 0)
                        ret = ret+rows[r-1];
                    break;
                }
            }
        }

        return (ret.equals("")) ? ""+ch : ret;
    }

    public String encryptString(String s) {
        String ret = "";

        for(int i=0; i<s.length(); i++)
            ret += encryptCharacter(s.charAt(i));

        return ret;
    }

    public String decryptString(String s) {
        char rowOne = (char)('0'+rows[0]), rowTwo = (char)('0'+rows[1]);
        String ret = "";

        for(int i=0; i<s.length(); i++) {
            int num = s.charAt(i)-'0';

            if(num == rows[0] || num == rows[1])
                ret += grid[s.charAt(++i)-'0'][(num == rowOne) ? 0 : 1];
            else
                ret += grid[num][0];
        }

        return ret;
    }

    public String toString() {
        String ret = " ";
        for(int i=0; i<10; i++)
            ret += "\t"+i;

        ret += "\n ";

        for(int r=0; r<3; r++) {
            for(int c=0; c<10; c++)
                ret += "\t"+grid[c][r];
            if(r < 2)
                ret += "\n"+rows[r];
        }

    }
}