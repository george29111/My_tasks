import java.io.IOException;

public class AudioDownloader {


    public static void downloadMP3(String audio_path, String audioNameAdress, String yt_dlpAdress) throws IOException,InterruptedException{

        ProcessBuilder builder = new ProcessBuilder(yt_dlpAdress, "--no-mtime","-f", " bestaudio[ext=m4a]","--no-playlist" ,"--audio-quality", 
        "0", "--postprocessor-args", "ffmpeg:-vn", "-o", audioNameAdress  , audio_path );
        builder.inheritIO();
        Process process = builder.start();
        process.waitFor();
    }
}
