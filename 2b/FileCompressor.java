import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

// Custom exception for invalid compression format
class InvalidCompressionFormatException extends Exception {
    public InvalidCompressionFormatException(String message) {
        super(message);
    }
}

public class FileCompressor {

    public static void main(String[] args) {
        String inputFilePath = "input.txt"; // Change this to your input file path
        String compressedFilePath = "compressed.txt";
        String decompressedFilePath = "decompressed.txt";

        try {
            compressFile(inputFilePath, compressedFilePath);
            decompressFile(compressedFilePath, decompressedFilePath);
        } catch (IOException e) {
            System.out.println("File I/O error: " + e.getMessage());
        } catch (InvalidCompressionFormatException e) {
            System.out.println("Decompression error: " + e.getMessage());
        } finally {
            System.out.println("File processing completed.");
        }
    }

    public static void compressFile(String inputFilePath, String compressedFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(compressedFilePath))) {

            StringBuilder compressedData = new StringBuilder();
            int currentChar = reader.read(); // Read the first character
            if (currentChar == -1) return; // Handle empty file

            char previousChar = (char) currentChar;
            int count = 1;

            while ((currentChar = reader.read()) != -1) {
                if ((char) currentChar == previousChar) {
                    count++;
                } else {
                    compressedData.append(previousChar).append(count);
                    previousChar = (char) currentChar;
                    count = 1;
                }
            }
            compressedData.append(previousChar).append(count); // Append the last sequence

            writer.write(compressedData.toString());
            System.out.println("Compression completed. Compressed file size: " +
                    Files.size(Paths.get(compressedFilePath)) + " bytes");
        }
    }

    public static void decompressFile(String compressedFilePath, String decompressedFilePath) throws IOException, InvalidCompressionFormatException {
        try (BufferedReader reader = new BufferedReader(new FileReader(compressedFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(decompressedFilePath))) {

            StringBuilder decompressedData = new StringBuilder();
            int currentChar;

            while ((currentChar = reader.read()) != -1) {
                char character = (char) currentChar;
                StringBuilder countBuilder = new StringBuilder();

                while ((currentChar = reader.read()) != -1 && Character.isDigit((char) currentChar)) {
                    countBuilder.append((char) currentChar);
                }

                if (countBuilder.length() == 0) {
                    throw new InvalidCompressionFormatException("Invalid format in compressed file.");
                }

                int count = Integer.parseInt(countBuilder.toString());
                decompressedData.append(String.valueOf(character).repeat(count));

                if (currentChar == -1) break; // Handle EOF cleanly
            }

            writer.write(decompressedData.toString());
            System.out.println("Decompression completed. Decompressed file size: " +
                    Files.size(Paths.get(decompressedFilePath)) + " bytes");
        }
    }
}
