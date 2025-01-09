import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Welcome to the Huffman Compressor!");
            System.out.println("Choose an option:");
            System.out.println("1. Compress a file");
            System.out.println("2. Decompress a file");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the path of the source file to compress: ");
                    String sourcePath = scanner.nextLine();

                    System.out.print("Enter the destination path for the compressed file: ");
                    String destPath = scanner.nextLine();

                    Compressor.compress(sourcePath, destPath);
                    System.out.println("Compression completed. The compressed file is saved at: " + destPath);
                    break;

                case 2:
                    System.out.print("Enter the path of the compressed file to decompress: ");
                    String compressedPath = scanner.nextLine();

                    System.out.print("Enter the destination path for the decompressed file: ");
                    String decompressedPath = scanner.nextLine();

                    Decompressor.decompress(compressedPath, decompressedPath);
                    System.out.println("Decompression completed. The decompressed file is saved at: " + decompressedPath);
                    break;

                case 3:
                    System.out.println("Exiting the program. Goodbye!");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }

        scanner.close();
    }
}
