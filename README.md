# AES-256 File Encryptor (Partial Implentation)

An AES-256 implentation used to encrypt and decyrpt files.

This project was created during the 2022 BC Hacks 4 event, <br>by Aidan Morris, and 

<br>

## Usage:

1. Encrypt a file:
    ```sh
    java -jar AES-256-FE.jar -e "target file" "output file" "password file.key"
    ```

2. Decrypt a file:
    ```sh
    java -jar AES-256-FE.jar -d "target file" "output file" "password file.key"
    ```

<br>

## Acknowledgments:

The algorithm used was based off the orignal proposal of Rijndael:

https://csrc.nist.gov/csrc/media/projects/cryptographic-standards-and-guidelines/documents/aes-development/rijndael-ammended.pdf

<br>

Reference was taken from

https://blog.nindalf.com/posts/implementing-aes/

<br>

And S_Box and Galois Multiplcation Look-up table data was sourced from:

https://en.wikipedia.org/wiki/Rijndael_S-box

https://en.wikipedia.org/wiki/Rijndael_MixColumns#Galois_Multiplication_lookup_tables 
