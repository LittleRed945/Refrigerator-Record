
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class PhotoUploader {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java PhotoUploader <sourceFilePath> <destinationDirectory>");
            return;
        }

        String sourceFilePath = args[0];
        String destinationDirectory = args[1];

        try {
            uploadPhoto(sourceFilePath, destinationDirectory);
            System.out.println("Photo uploaded successfully!");
        } catch (IOException e) {
            System.out.println("Failed to upload photo: " + e.getMessage());
        }
    }

    public static String uploadPhoto(String sourceFilePath, String destinationDirectory) throws IOException {
        File sourceFile = new File(sourceFilePath);

        if (!sourceFile.exists()) {
            throw new IOException("Source file does not exist.");
        }

        File destinationDir = new File(destinationDirectory);

        if (!destinationDir.exists()) {
            if (!destinationDir.mkdirs()) {
                throw new IOException("Failed to create destination directory.");
            }
        }

        if (!destinationDir.isDirectory()) {
            throw new IOException("Destination path is not a directory.");
        }
        String fileName = sourceFile.getName();
        File destinationFile = new File(destinationDir, fileName);

        try (InputStream inputStream = Files.newInputStream(sourceFile.toPath());
             OutputStream outputStream = new FileOutputStream(destinationFile)) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return fileName;
    }
}
