package com.gmail.romkatsis;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.NoSuchAlgorithmException;

public class PasswordSearchEngine {

    private final String filePath;

    public PasswordSearchEngine(String filePath) {
        this.filePath = filePath;
    }

    //A binary search on the file is used
    // RandomAccessFile is used to quickly move the pointer around the file
    // This also helped to reduce memory usage
    public int searchPasswordAmountByHex(String hex) throws IOException {
        RandomAccessFile file = new RandomAccessFile(this.filePath, "r");
        long startPosition = 0;
        long endPosition = file.length() - 1;
        long midPosition;
        long lastRange = endPosition + 1;

        while (startPosition <= endPosition) {
            midPosition = (startPosition + endPosition) / 2;
            file.seek(midPosition);

            //Repositioning the pointer to the beginning of the line if it is not on the line
            while (file.readByte() != 10 && midPosition > 0) {
                midPosition--;
                file.seek(midPosition);
            }

            //Reading a line and checking the Hex value
            //Then determining the further course of the binary search
            String[] currentLine = file.readLine().split(":");
            String currentHex = currentLine[0];
            int amount = Integer.parseInt(currentLine[1]);

            if (hex.equals(currentHex)) {
                return amount;
            } else if (hex.compareTo(currentHex) < 1) {
                endPosition = midPosition - 1;
            } else {
                startPosition = midPosition + 1;
            }

            //The comparison of the last range with the current one is necessary
            //because we move the cursor to the beginning of the line
            //and if there is no hex value in the file, an eternal loop is possible
            if (endPosition - startPosition == lastRange) {
                return 0;
            }
            lastRange = endPosition - startPosition;
        }
        file.close();
        return 0;
    }

    //Method for displaying the values
    public void displayPasswordInfo(String password) throws NoSuchAlgorithmException, IOException {
        String hex = HashingTool.getStringHex(password, "SHA-1");
        int amount =  this.searchPasswordAmountByHex(hex);
        System.out.printf("%40s %45s %15d\n", password, hex, amount);
    }
}
