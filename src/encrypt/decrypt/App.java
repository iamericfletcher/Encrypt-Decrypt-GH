package encrypt.decrypt;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {

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
            switch (args[i]) {
                case "-alg" -> alg = args[i + 1];
                case "-mode" -> mode = args[i + 1];
                case "-key" -> key = Integer.parseInt(args[i + 1]);
                case "-data" -> {
                    isDataProvided = true;
                    data = args[i + 1];
                }
                case "-in" -> {
                    isInProvided = true;
                    inputFile = new File(workingDirectory + "\\" + args[i + 1]);
                }
                case "-out" -> outputFilePath = new File(workingDirectory + "\\" + args[i + 1]);
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
                        System.out.println(EncryptDecrypt.alphabetIndexPositions(data, key, mode, alg));
                    } else if (isDataProvided && isInProvided) {
                        System.out.println(EncryptDecrypt.alphabetIndexPositions(data, key, mode, alg));
                    } else {
                        EncryptDecrypt.writeCipherTextFile(inputFile, outputFilePath, key, alg, mode);
                    }
                    break;
                }
            case "dec":
                if (inputFile == null && outputFilePath == null) {
                    System.out.println(EncryptDecrypt.alphabetIndexPositions(data, key, mode, alg));
                } else if (isDataProvided && isInProvided) {
                    System.out.println(EncryptDecrypt.alphabetIndexPositions(data, key, mode, alg));
                } else {
                    EncryptDecrypt.readCipherTextFile(inputFile, outputFilePath, key, alg, mode);
                }
                break;
        }
    }
}


