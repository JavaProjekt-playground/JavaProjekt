package Database;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageLoader {
    private static final String password = "E*PZH)dF{j?a";
    private static final String user = "imageloader@javaproject.zerdoner.com";
    private static FTPClient client;
    private static final String domain = "javaproject.zerdoner.com";
    private static final String directory = "javaproject_images";

    public static URL getImageURL(String fileName){
        try{
            return new URL(String.format("%s/%s/%s", domain, directory, fileName));
        }catch(MalformedURLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static class FTPLoader{

        private static void openConn() throws IOException {
            if(client == null) client = new FTPClient();

            if(!client.isConnected()){
                client.connect(domain);
                client.login(user, password);
                client.enterLocalPassiveMode();
            }
        }

        private static void closeConn() throws IOException {
            if(client.isConnected()){
                client.logout();
                client.disconnect();
            }
        }

        public static boolean uploadImage(String path, String filename){

            try {
                openConn();

                File localFile = new File(path);
                InputStream inputStream = new FileInputStream(localFile);
                System.out.println("File upload started");
                boolean success = client.storeFile(filename, inputStream);
                System.out.println("File upload stopped");

                closeConn();
                return success;

            }catch(IOException ex){
                ex.printStackTrace();
            }
            finally {
                try {
                    closeConn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }


    }

}
