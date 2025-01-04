import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Huffman Compressor!");
        System.out.print("Enter the path of the source file to compress: ");
        String sourcePath = scanner.nextLine();

        System.out.print("Enter the destination path for the compressed file: ");
        String destPath = scanner.nextLine();

        Compressor.compress(sourcePath, destPath);

        System.out.println("Compression completed. The compressed file is saved at: " + destPath);

        // Optionally, you can provide a decompression method for testing decompression.
        // Implementing a decompressor can follow a similar pattern by reversing the Huffman coding process.

        scanner.close();
    }
}
