package FileIO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileIO {

    public static byte[] load(File file){
        try {
            //Read bytes into memory
            return Files.readAllBytes(Path.of(file.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(byte[] fileBytes, String outputPath) throws IOException {
        FileOutputStream stream = null;
        try {
            //Create a new file if one does not already exist
            File outputFile = new File(outputPath);
            outputFile.createNewFile();

            //Write encoded/decoded byte to said file
            stream = new FileOutputStream(outputFile);
            stream.write(fileBytes);

            System.out.println("The file was successfully saved to: " + outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            //Make sure to close stream no matter what
            stream.close();
        }
    }
}
