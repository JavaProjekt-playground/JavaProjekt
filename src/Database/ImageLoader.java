package Database;

import org.apache.commons.net.ftp.FTPClient;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

public class ImageLoader {
    private static final String password = "E*PZH)dF{j?a";
    private static final String user = "imageloader@javaproject.zerdoner.com";
    private static FTPClient client;
    private static final String protocol = "http";
    private static final String domain = "javaproject.zerdoner.com";
    private static final String directory = "javaproject_images";

    private static final int _maxImageWidth = 800;
    private static final int _maxImageHeight = 800;

    public static URL getImageURL(String fileName){
        try{
            return new URL(String.format("%s://%s/%s/%s", protocol, domain, directory, fileName));
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
                client.setFileType(FTPClient.BINARY_FILE_TYPE);
            }
        }

        private static void closeConn() throws IOException {
            if(client.isConnected()){
                client.logout();
                client.disconnect();
            }
        }

        public static boolean uploadImage(String path, String filename){

            String suffix = path.toLowerCase().substring(path.lastIndexOf(".") + 1);
            if(!suffix.equals("png") && !suffix.equals("jpg")){
                System.out.println("Invalid file type ("+suffix+"), skipping");
                return false;
            }

            try {
                openConn();


                BufferedImage im = ImageIO.read(new File(path));
                int w = im.getWidth();
                int h = im.getHeight();

                if(h > _maxImageHeight ) {
                    double r = (double)_maxImageHeight / (double)h;
                    h = (int)Math.floor(r*(double)h);
                    w = (int)Math.floor(r*(double)w);
                }
                if(h > _maxImageWidth ) {
                    double r = (double)_maxImageHeight / (double)w;
                    h = (int)Math.floor(r*(double)h);
                    w = (int)Math.floor(r*(double)w);
                }


                Image scaledImg = im.getScaledInstance(w, h, BufferedImage.SCALE_DEFAULT);
                BufferedImage resizedImage = new BufferedImage(w, h, im.getType());
                Graphics2D g2 = resizedImage.createGraphics();
                g2.drawImage(scaledImg, 0, 0, null);


                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, suffix, bs);

                InputStream inputStream = new ByteArrayInputStream(bs.toByteArray());
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
