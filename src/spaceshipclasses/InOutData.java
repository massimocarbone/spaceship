/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author jyee
 */
public class InOutData {
    static public int numStars;
    static public int highScore;
    static public void writeCurrentState()
    {
        try {
            byte[] data;
            File file = new File("state.txt");
            FileOutputStream fileStream = new FileOutputStream(file);

            String str = "numstars " + numStars + "\n";
            str += "highscore " + highScore + "\n";
            data = str.getBytes();
            
            fileStream.write(data,0,data.length);
            fileStream.close();
        }
        catch (IOException ioe) {
        }

    }

    static public void readCurrentState() {
        try {
            String inputfile = "state.txt";
            BufferedReader in = new BufferedReader(new FileReader(inputfile));
            String line = in.readLine();
            while (line != null) {
                String newLine = line.toLowerCase();
                if (newLine.startsWith("numstars"))
                {
                    String numStarsString = newLine.substring(9);
                    numStars = Integer.parseInt(numStarsString.trim());
                }
               
                if (newLine.startsWith("highscore"))
                {
                    String highScoreString = newLine.substring(10);
                    highScore = Integer.parseInt(highScoreString.trim());
                }

                line = in.readLine();
            }
            in.close();
        } catch (IOException ioe) {
        }
    }

}
