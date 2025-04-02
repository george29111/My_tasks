import java.io.*;
import java.util.Scanner;

public class Installer {


    public static void commandBuilder(String wingetAdress) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(wingetAdress , "install", "yt-dlp.yt-dlp" );
        builder.redirectErrorStream(true); 

        Process process = builder.start();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write("Y\n");
            writer.flush();
        }


        try (Scanner sc = new Scanner(process.getInputStream())){
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        }
        int exitCode = process.waitFor(); 
        System.out.println("Process exited with code: " + exitCode); 
    }



    public static void main(String[] args) {
        System.out.println("Starting installation...");

        try {
            String wingetAdress = System.getProperty("user.home") + "\\AppData\\Local\\Microsoft\\WindowsApps\\winget.exe";
            commandBuilder(wingetAdress);
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        System.out.println("Press Enter to exit.");
        try { System.in.read(); } catch (IOException ignored) {}
    }
}
