package com.gmail.romkatsis;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class App
{
    //Throw exceptions for missing file or wrong algorithm
    // Exceptions are not handled because any of them is fatal
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        //Checking and retrieving file path and passwords
        if (args.length < 2) {
            System.out.println(
                    "The first value must be the path to the file, then at least one password must be provided");
            System.exit(-1);
        }
        String filePath = args[0];
        String[] passwords = Arrays.copyOfRange(args, 1, args.length);

        PasswordSearchEngine passwordSearchEngine =
                new PasswordSearchEngine(filePath);

        System.out.printf("%40s %45s %15s\n", "Password", "Hash", "Amount");
        for (String password : passwords) {
            passwordSearchEngine.displayPasswordInfo(password);
        }
    }
}
