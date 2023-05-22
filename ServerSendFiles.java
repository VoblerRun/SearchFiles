import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

public class ServerSendFiles implements Runnable{
    private  DataOutputStream dataOutputStream = null;
    private  DataInputStream dataInputStream = null;
    private List<File> files = new ArrayList<>();
    private ZipConverter zipConverter = new ZipConverter();

    ServerSendFiles(List<File> files){
        this.files = files;
    }



    private void sendFiles()  {
        System.out.println(
        zipConverter.zip(files, "files.zip"));
//        System.out.println("zipOutputStream  " + zipOutputStream);
        try (Socket socket = new Socket("127.0.0.1", 25)) {
            File myFile = new File("C:\\Users\\Федор\\IdeaProjects\\SearchFiles\\files.zip");
            byte[] mybytearray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            //bis.read(mybytearray, 0, mybytearray.length);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            OutputStream os = socket.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();

            //Sending file data to the server
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();

            //Closing socket
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
      sendFiles();
    }
}
