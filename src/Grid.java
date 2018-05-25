/*
    Manav Kulshrestha
    Grid.java
    5/21/18
*/

import java.io.*;
import java.math.BigInteger;

public class Grid {
    private int[] rows;
    private char[][] grid;
    private String encryptedForwardSlash;
    private String encryptedPeriod;
    private boolean escapeEncrypted = false;
    private String repeat;

    public Grid(boolean random, String rep) {
        this.rows = new int[2];
        this.grid = new char[10][3];
        char[] temp;

        temp = toCharArray("ETAO N RIS");
        if(random)
            shuffle(temp);
        for(int i=0, j=0; i<temp.length; i++) {
            grid[i][0] = temp[i];
            if(temp[i] == ' ')
                rows[j++] = i;
        }

        temp = toCharArray("BCDFGHJKLMPQ/UVWXY.Z");
        if(random)
            shuffle(temp);

        int len = temp.length/2;
        for(int i=0; i<len; i++)
            grid[i][1] = temp[i];

        for(int i=0; i<len; i++)
            grid[i][2] = temp[len+i];

        encryptedForwardSlash = encryptCharacter('/');
        encryptedPeriod = encryptCharacter('.');

        repeat = rep;
    }

    public Grid(String fileName, String rep) throws IOException{
        this.rows = new int[2];
        this.grid = new char[10][3];
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

        encryptedForwardSlash = encryptCharacter('/');
        encryptedPeriod = encryptCharacter('.');

        repeat = rep;
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

    public String encryptCharacter(char ch) {
        String ret = "";
        ch = Character.toUpperCase(ch);

        for(int c=0; c<grid.length; c++) {
            for(int r=0; r<grid[c].length; r++) {
                if(grid[c][r] == ch) {
                    ret += c;
                    if(r > 0)
                        ret = rows[r-1]+ret;
                }
            }
        }

        return ret;
    }

    public String encryptString(String s) {
        String ret = "";
        s = s.replaceAll("[^A-Za-z0-9]", "");
        int len = s.length();

        for(int i=0; i<len; i++) {
            char ch = s.charAt(i);
            if('0' <= ch && ch <= '9')
                ret += encryptedForwardSlash+ch+encryptedPeriod;
            else
                ret += encryptCharacter(ch);
        }

        return complicate(ret, repeat);
    }

    public String decryptString(String s) {
        String ret = "";
        s = uncomplicate(s, repeat);

        for(int i=0; i<s.length(); i++) {
            char ch = s.charAt(i);
            int num = ch-'0';

            if(num == rows[0] || num == rows[1]) {
                char decrypted = grid[s.charAt(++i)-'0'][(num == rows[0]) ? 1 : 2];
                if(decrypted == '/') {
                    ret += ""+s.charAt(++i);
                    i += 2;
                } else
                    ret += decrypted;
            } else
                ret += grid[num][0];
        }

        return ret;
    }

    public void saveToFile(String fileName) throws IOException {
        PrintWriter out = new PrintWriter(new File(fileName));

        out.println((""+rows[0])+rows[1]);
        for(int r=0; r<3; r++) {
            for(int c=0; c<10; c++)
                out.print(grid[c][r]);
            out.println();
        }

        out.close();
    }

    public String complicate(String encrypted, String repeating) {
        int eLen = encrypted.length(), rLen = repeating.length(), rCount = eLen/rLen, subLim = eLen%rLen;
        String toAdd = "", ret = "";

        for(int i=0; i<rCount; i++)
            toAdd += repeating;

        toAdd += repeating.substring(0, subLim);

        for(int i=0; i<eLen; i++)
            ret += (((encrypted.charAt(i)-'0')+(toAdd.charAt(i)-'0'))%10);

        return ret;
    }

    public String uncomplicate(String complicated, String repeating) {
        int cLen = complicated.length(), rLen = repeating.length(), rCount = cLen/rLen, subLim = cLen%rLen;
        String toSubtract = "", ret = "";

        for(int i=0; i<rCount; i++)
            toSubtract += repeating;

        toSubtract += repeating.substring(0, subLim);

        for(int i=0; i<cLen; i++)
            ret += ((complicated.charAt(i)-'0'+10)-(toSubtract.charAt(i)-'0'))%10;

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

        return ret;
    }
}