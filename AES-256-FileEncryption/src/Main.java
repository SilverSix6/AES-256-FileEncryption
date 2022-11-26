import Algorithm.Encrypt;
import Algorithm.Decrypt;
import FileIO.*;

import java.io.File;
import java.io.IOException;

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
        if(args[0].equals("-e")){
            //Load file into memory
            byte[] fileBytes = FileIO.load(new File(args[1]));

            //Run encryption algorithm
            Encrypt.encrypt(fileBytes, args[3]);

            //Save file Bytes to specified output location
            FileIO.save(fileBytes, args[2]);

        } else if (args[0].equals("-d")){
            //Load encoded file into memory
            byte[] fileBytes = FileIO.load(new File(args[1]));

            //Run decryption algorithm
            Decrypt.decrypt(fileBytes, args[3]);

            //Save file Bytes to specified output location
            FileIO.save(fileBytes, args[2]);
        }

    }

}