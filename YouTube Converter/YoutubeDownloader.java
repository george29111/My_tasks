import java.io.IOException;

public class YoutubeDownloader {
    

    public static void downloadVideo(String url, String videoNameAdress,String yt_dlpAdress) throws IOException,InterruptedException{
        ProcessBuilder builder = new ProcessBuilder(yt_dlpAdress, "--no-mtime", "-f", "bestvideo[ext=mp4]+bestaudio[ext=m4a]", "-o", videoNameAdress, url);
        builder.inheritIO();
        Process process = builder.start();
        process.waitFor();
    }

    //"bestvideo[ext=mp4]+bestaudio[ext=m4a]"
    //"bv*+ba/b"
    // b
    // bestvideo
}
