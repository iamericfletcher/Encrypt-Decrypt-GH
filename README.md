# About

This is a program that encrypts and decrypts messages and texts using two simple algorithms. Please note, the algorithms
used in this project are not suitable for production use. I used them to help deepen my understanding of the general
ideas behind encryption, working with files, and interacting with the command line.

### Algorithms

```
1. Unicode
   - Encodes regular and non-letter characters according to the Unicode table.
2. Shift
   - Encodes only English letters.
```

### Arguments

        -mode: determines the program’s mode (enc for encryption, dec for decryption).
        -key: an integer key to modify the message.
        -data: is a text or ciphertext to encrypt or decrypt.
        -alg: two different algorithms. The first one would be shifting algorithm (it shifts each letter by the
              specified number according to its order in the alphabet in circle). The second one is based
              on the Unicode table (shift for shifting, unicode for unicode).
        -in: read data in from a file
        -out: write data to a file

## Usage

```
Example 1

java Main -mode enc -in road_to_treasure.txt -out protected.txt -key 5 -alg unicode
This command must get data from the file road_to_treasure.txt, encrypt the data with the key 5, create a file called protected.txt and write ciphertext to it.

Example 2

Input:

java Main -mode enc -key 5 -data "Welcome to hyperskill!" -alg unicode
Output:

\jqhtrj%yt%m~ujwxpnqq&
Example 3

Input:

java Main -key 5 -alg unicode -data "\jqhtrj%yt%m~ujwxpnqq&" -mode dec
Output:

Welcome to hyperskill!
Example 4:

Input:

java Main -key 5 -alg shift -data "Welcome to hyperskill!" -mode enc
Output:

Bjqhtrj yt mdujwxpnqq!
Example 5:

Input:

java Main -key 5 -alg shift -data "Bjqhtrj yt mdujwxpnqq!" -mode dec
Output:

Welcome to hyperskill!
```

## Reflections

For my next project, I would like to implement one or more fundamental principles of object-oriented programming (
encapsulation, inheritance, and polymorphism).

I'd like to learn more about design patterns.

## License

[MIT](https://choosealicense.com/licenses/mit/)