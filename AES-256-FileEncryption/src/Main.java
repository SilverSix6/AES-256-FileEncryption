import Algorithm.Decrypt;
import Algorithm.Encrypt;
import FileIO.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        /*
            Args formatting:
                args[0]: Encode or Decode
                    -e -> encode
                    -d -> decode
                args[1]: Target file
                    String
                args[2]: Output location
                    String
                args[3]: Password/Passkey
                    String
         */


        //If key wasn't found or inputted ask to create one
        try{
            String temp = args[3];
        } catch (ArrayIndexOutOfBoundsException e){
            String[] newArgs = new String[4];
            for(int i = 0; i < args.length; i++){
                newArgs[i] = args[i];
            }
            newArgs[3] = null;
            args = newArgs;
        }

        if(args[3].equals(null)){
            System.out.println("The key file was not found or was not inputted.\n Would you like to create one: (y/n)");
            Scanner userInput = new Scanner(System.in);

            if(userInput.nextLine().trim().equalsIgnoreCase("y")){
                System.out.println("Enter a sentence that you would like to use as a pass key:");

                //Create key sha256 key based on user input. Saves it to the program dir
                FileIO.save(DigestUtils.sha256(userInput.nextLine()), System.getProperty("user.dir") + "\\user.key");
                System.out.println("A key file was created in the working directory");

                //Update the args
                args[3] = System.getProperty("user.dir") + "\\user.key";
            } else return;
            userInput.close();
        }

        if(args[0].equals("-e")){
            //Load file into memory
            byte[] fileBytes = FileIO.load(new File(args[1]));

            //bytes that will be outputted are the next highest multiple of 16 plus and additional 4 bytes for input file size
            byte[] encryptedBytes = new byte[((fileBytes.length) / 16) * 16 + 16 + 4];

            //get key bytes
            byte[] keyValue = FileIO.load(new File(args[3]));

            //Split fileByte is into 16 byte sections
            for(int i = 0; i < ((fileBytes.length) / 16) * 16 + 16; i += 16){
                byte[] temp = new byte[16];

                //get 16 bytes starting from i to i + 15. If end of file add zeros
                for(int c = 0; c < 16; c++){
                    try{
                        temp[c] = fileBytes[c + i];
                    } catch (ArrayIndexOutOfBoundsException e){
                        //add zeros if the end of the file
                        temp[c] = (byte) 0;
                    }
                }



                //Run encryption algorithm
                Encrypt.encrypt(temp, keyValue);

                //Combine the encrypted bytes
                for(int c = 0; c < 16; c++){
                    encryptedBytes[c+i] = temp[c];
                }
            }

            //Add size of input file to end of file for reconstruction
            int fileSize = fileBytes.length;

            int[] ints = new int[4];
            ints[0] = (fileSize >> 24) & 0xFF;
            ints[1] = (fileSize >> 16) & 0xFF;
            ints[2] = (fileSize >> 8) & 0xFF;
            ints[3] = fileSize & 0xFF;

            for(int i = 0; i < 4; i++){
                encryptedBytes[((fileBytes.length) / 16) * 16 + 16 + i] = (byte) ints[i];
            }

            //Save file Bytes to specified output location
            FileIO.save(encryptedBytes, args[2]);

        } else if (args[0].equals("-d")){
            //Load encoded file into memory
            byte[] fileBytes = FileIO.load(new File(args[1]));
            byte[] decryptedBytes = new byte[fileBytes.length - 8];

            //get key bytes
            byte[] keyValue = FileIO.load(new File(args[3]));

            for(int i = 0; i < fileBytes.length - 8; i += 16){
                byte[] temp = Arrays.copyOfRange(fileBytes, i, i+15);

                //Run decryption algorithm
                Decrypt.decrypt(fileBytes, keyValue);

                //Combine the decrypted bytes
                for(int c = 0; c < 15; c++){
                    decryptedBytes[c+i] = temp[c];
                }

            }

            //Get the length of the original file
            byte[] intBytes = new byte[4];
            for(int i = 3; i >= 0; i--){
               intBytes[fileBytes.length - i] = fileBytes[fileBytes.length - i];
            }

            int newLength = intBytes[3] + (int)(intBytes[2]) >> 8 + (int)(intBytes[1]) >> 16 + (int)(intBytes[0]) >> 24;

            //Copy decryptedBytes to new array that is length newLength
            byte[] trimmedDecryptedBytes = new byte[newLength];
            for(int i = 0; i < newLength; i++){
                trimmedDecryptedBytes[i] = decryptedBytes[i];
            }

            //Save file Bytes to specified output location
            FileIO.save(trimmedDecryptedBytes, args[2]);
        }





    }



}