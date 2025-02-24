import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);


        System.out.println("That's a mp3/mp4 converter!");


        System.out.print("Enter the type you want to convert: ");
        String temp = sc.nextLine();
        temp = nameValidation(sc, temp);


        System.out.println("Enter the URL of the video: " + "\n Be careful to enter correct URL!!!");
        String link = sc.nextLine();


        System.out.print("\n" + "Write the name of the file: ");
        String name = sc.nextLine();
        name = sanitizeFileName(name);

        


        try {

            String defauntPath;

            if(temp.equalsIgnoreCase("mp3")){
                defauntPath = System.getProperty("user.home")+ "\\Downloads" +"\\"+ name + ".mp3";
            }else {
                defauntPath = System.getProperty("user.home") + "\\Downloads" +"\\"+ name + ".mp4";
            }




            String yt_dlpAdress = CmdCOmmandInstaller();

            if ("mp3".equalsIgnoreCase(temp)) {

                AudioDownloader.downloadMP3(link, defauntPath,yt_dlpAdress);

                System.out.println("The mp3 is ready");
            } else {
                YoutubeDownloader.downloadVideo(link, defauntPath,yt_dlpAdress);
                System.out.println("The video is ready");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        sc.close();
    }



    public static String nameValidation(Scanner sc, String temp){
        while (!(temp.equals("mp3")|| temp.equals("mp4"))) {
            System.out.print("Invalid download type! Please enter mp3 or mp4:");
            temp = sc.nextLine();
            
        }

        return temp;
    }

    
    public static String sanitizeFileName(String fileName) {

        char replacementChar = '_';
        
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("The file name canot be empty or blak spaces!");
        }

        String invalidChars = "\\/:*?\"<>|";

        StringBuilder sanitized = new StringBuilder();

        for (char c : fileName.toCharArray()) {
            if (invalidChars.indexOf(c) != -1) {
                sanitized.append(replacementChar);
            } else {
                sanitized.append(c);
            }
        }

        return sanitized.toString();
    }



    public static String CmdCOmmandInstaller() throws IOException, InterruptedException{

        ProcessBuilder builder = new ProcessBuilder("powershell", "-Command","[Console]::OutputEncoding = [System.Text.Encoding]::UTF8; (Get-Command yt-dlp).Source");
        builder.redirectErrorStream(true);

        Process process = builder.start();
        process.waitFor();

        StringBuilder output = new StringBuilder();
        try (Scanner sc = new Scanner(process.getInputStream())){
            while(sc.hasNextLine()){
                output.append(sc.nextLine());
            }
        }

        return output.toString();

    }
}