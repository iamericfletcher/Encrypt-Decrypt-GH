package com.company;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        /*
         * This program has six arguments:
         * -mode: determines the program’s mode (enc for encryption, dec for decryption).
         * -key: an integer key to modify the message.
         * -data: is a text or ciphertext to encrypt or decrypt.
         * -alg: two different algorithms. The first one would be shifting algorithm (it shifts each letter by the
         *  specified number according to its order in the alphabet in circle). The second one would be based on
         *  Unicode table, like in the previous stage.
         * -in: read data in from a txt file
         * -out: write data to a txt file
         */

        String mode = "enc";
        int key = 0;
        String data = "";
        String alg = "shift";
        boolean isDataProvided = false;
        boolean isInProvided = false;
        File inputFile = null;
        File outputFilePath = null;

        String workingDirectory = System.getProperty("user.dir");


        for (int i = 0; i < args.length; i++) {
            if ("-alg".equals(args[i])) {
                alg = args[i +1];
            } else if ("-mode".equals(args[i])) {
                mode = args[i + 1];
            } else if ("-key".equals(args[i])) {
                key = Integer.parseInt(args[i + 1]);
            } else if ("-data".equals(args[i])) {
                isDataProvided = true;
                data = args[i + 1];
            } else if ("-in".equals(args[i])) {
                isInProvided = true;
                inputFile = new File(workingDirectory + "\\" + args[i + 1]);
            } else if ("-out".equals(args[i])) {
                outputFilePath = new File(workingDirectory + "\\" + args[i + 1]);
            }
        }

        switch (mode) {
            case "enc":

                if (alg.equals("unicode")) {

                    if (inputFile == null && outputFilePath == null) {
                        System.out.println(EncryptDecrypt.encrypt(data, key));
                    } else if (isDataProvided && isInProvided) {
                        System.out.println(EncryptDecrypt.encrypt(data, key));
                    } else {
                        EncryptDecrypt.writeCipherTextFile(inputFile, outputFilePath, key, alg, mode);
                    }
                    break;

                } else if (alg.equals("shift")) {
                    if (inputFile == null && outputFilePath == null) {
                        System.out.println(EncryptDecrypt.alphabetIndexPositions(data, key, mode));
                    } else if (isDataProvided && isInProvided) {
                        System.out.println(EncryptDecrypt.alphabetIndexPositions(data, key, mode));
                    } else {
                        EncryptDecrypt.writeCipherTextFile(inputFile, outputFilePath, key, alg, mode);
                    }
                    break;
                }


            case "dec":


                if (inputFile == null && outputFilePath == null) {
                    System.out.println(EncryptDecrypt.alphabetIndexPositions(data, key, mode));
                } else if (isDataProvided && isInProvided) {
                    System.out.println(EncryptDecrypt.alphabetIndexPositions(data, key, mode));
                } else {
                    EncryptDecrypt.readCipherTextFile(inputFile, outputFilePath, key, alg, mode);
                }
                break;

            default:
                break;
        }

    }
}


