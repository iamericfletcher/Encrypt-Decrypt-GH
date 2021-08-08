package com.company;

import java.io.*;

public class EncryptDecrypt {


    public static final String[] ALPHABET = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z"
    };

    public static String alphabetIndexPositions(String inputMessage, int inputKey, String mode, String alg) {

        String[] inputMessageArray = inputMessage.split("");

        int alphabetCount = 0;
        StringBuilder alphabetIndex = new StringBuilder();
        StringBuilder isUpperCase = new StringBuilder();

        for (String inputMessageLetter : inputMessageArray) {
            for (String alphabetLetter : ALPHABET) {
                if (inputMessageLetter.equals(alphabetLetter)) {
                    isUpperCase.append(false).append(" ");
                    break;
                } else if (inputMessageLetter.equals(alphabetLetter.toUpperCase())) {
                    isUpperCase.append(true).append(" ");
                    break;
                } else if (inputMessageLetter.matches("[a-zA-Z]")) {
                    alphabetCount++;
                }
            }
            if (alphabetCount == 0 && inputMessageLetter.matches("a")) {
                alphabetIndex.append(0).append(" ");
            } else if (alphabetCount == 0 && !inputMessageLetter.matches("[a-zA-Z]") && !inputMessageLetter.equals(" ")) {
                alphabetIndex.append(inputMessageLetter).append(" ");
                isUpperCase.append("false").append(" ");
            } else {
                if (inputMessageLetter.equals(" ")) {
                    alphabetIndex.append(inputMessageLetter);
                    isUpperCase.append("false").append(" ");
                } else {
                    alphabetIndex.append(alphabetCount).append(" ");
                }
            }
            alphabetCount = 0;
        }
        if (mode.equals("enc")) {
            return indexWithKey(alphabetIndex, inputKey, isUpperCase);
        } else if (mode.equals("dec")) {

            if (alg.equals("shift")) {
                return decryptShiftIndexes(alphabetIndex, inputKey, isUpperCase);
            } else if (alg.equals("unicode")) {
                return decrypt(inputMessage, inputKey);
            } else {
                return "Algorithm must be shift or unicode!";
            }
        } else {
            return "Mode must be enc or dec!";
        }
    }

    public static String indexWithKey(StringBuilder alphabetIndex, int inputKey, StringBuilder isUpperCase) {

        String[] indexArray = alphabetIndex.toString().split(" ");
        StringBuilder indexArrayKey = new StringBuilder();

        int lengthOfAlphabet = 25;



        for (String index :
                indexArray) {

            int indexWithKeyCount;

            if (index.matches("\\d+")) {
                indexWithKeyCount = Integer.parseInt(index) + inputKey;
                if (indexWithKeyCount > lengthOfAlphabet) {
                    indexWithKeyCount = Math.abs(lengthOfAlphabet - indexWithKeyCount);
                    indexArrayKey.append(indexWithKeyCount - 1).append(" ");
                } else {
                    indexArrayKey.append(indexWithKeyCount).append(" ");
                }
            } else {
                indexArrayKey.append(index).append(" ");
            }

        }
        return replaceIndexWithLetters(indexArrayKey, isUpperCase);
    }


    public static String replaceIndexWithLetters(StringBuilder indexArrayKey, StringBuilder isUpperCase) {
        String[] isUpperCaseArray = isUpperCase.toString().split(" ");
        String[] alphabetIndexArray = indexArrayKey.toString().split(" ");
        StringBuilder outputCipher = new StringBuilder();
        int indexCount = 0;

        int count = 0;

        for (String letterIndexPosition : alphabetIndexArray) {
            for (String s : ALPHABET) {

                if (!letterIndexPosition.matches("\\d+")) {

                    if (alphabetIndexArray[indexCount].equals("")) {
                        outputCipher.append(letterIndexPosition).append(" ");
                    } else {
                        outputCipher.append(letterIndexPosition);
                    }

                    break;

                } else if (count == Integer.parseInt(letterIndexPosition)) {

                    if (isUpperCaseArray[indexCount].equals("true")) {
                        outputCipher.append(s.toUpperCase());
                    } else {
                        outputCipher.append(s);
                    }
                    break;
                }
                count++;
            }
            indexCount++;
            count = 0;
        }
        return  outputCipher.toString();
    }

    public static String decryptShiftIndexes(StringBuilder alphabetIndex, int inputKey, StringBuilder isUpperCase) {
        String[] alphabetIndexArray = alphabetIndex.toString().split(" ");
        StringBuilder output = new StringBuilder();

        for (String index :
                alphabetIndexArray) {

            if (index.matches("\\d+")) {

                if (Integer.parseInt(index) - inputKey < 0) {
                    output.append(Integer.valueOf(26 - Math.abs(Integer.parseInt(index) - inputKey))).append(" ");
                } else {
                    output.append(Integer.parseInt(index) - inputKey).append(" ");
                }

            } else {
                output.append(index).append(" ");
            }

        }
        return decryptShift(output, isUpperCase);
    }

    public static String decryptShift(StringBuilder output, StringBuilder isUpperCase) {
        String[] isUpperCaseArray = isUpperCase.toString().split(" ");
        String[] outputFinal = output.toString().split(" ");
        StringBuilder outputFinal2 = new StringBuilder();
        int indexCount = 0;

        for (String index :
                outputFinal) {

            if (index.matches("\\d+")) {

                if (isUpperCaseArray[indexCount].equals("true")) {

                    outputFinal2.append(ALPHABET[Integer.parseInt(index)].toUpperCase());

                } else {

                    outputFinal2.append(ALPHABET[Integer.parseInt(index)]);

                }
            } else {

                if (index.equals("")) {
                    outputFinal2.append(index).append(" ");
                } else {
                    outputFinal2.append(index);
                }


            }
            indexCount++;
        }


        return String.valueOf(outputFinal2);
    }

    public static String encrypt(String inputMessage, int inputKey) {
        char[] encryptCharArray = inputMessage.toCharArray();
        char[] charArray2 = new char[encryptCharArray.length];

        for (int i = 0; i < charArray2.length; i++) {
            charArray2[i] = encryptCharArray[i] += inputKey;
        }

        return new String(charArray2);
    }

    public static String decrypt(String inputMessage, int inputKey) {


        char[] decryptCharArray = inputMessage.toCharArray();
        char[] charArray2 = new char[decryptCharArray.length];

        for (int i = 0; i < charArray2.length; i++) {
            charArray2[i] = decryptCharArray[i] -= inputKey;
        }
        return new String(charArray2);
    }

    public static void writeCipherTextFile(File inputFile, File outputFilePath, int key, String alg, String mode) {

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String s;

            if (alg.equals("unicode")) {

                while ((s = reader.readLine()) != null) {
                    writer.write(encrypt(s, key));
                    writer.newLine();
                }

            } else if (alg.equals("shift")) {

                while ((s = reader.readLine()) != null) {
                    writer.write(alphabetIndexPositions(s, key, mode, alg));
                    writer.newLine();
                }

            }

        } catch (IOException e) {
            System.out.printf("Error: %s", e.getMessage());
        }


    }

    public static void readCipherTextFile(File inputFile, File outputFilePath, int key, String alg, String mode) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String s;

            if (alg.equals("unicode")) {

                while ((s = reader.readLine()) != null) {
                    writer.write(decrypt(s, key));
                    writer.newLine();
                }

            } else if (alg.equals("shift")) {

                while ((s = reader.readLine()) != null) {
                    writer.write(alphabetIndexPositions(s, key, mode, alg));
                    writer.newLine();
                }

            }

        } catch (IOException e) {
            System.out.printf("Error: %s", e.getMessage());
        }
    }

}
