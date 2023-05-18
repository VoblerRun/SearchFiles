import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSendFiles implements Runnable{
    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out;
    private List<File> files = new ArrayList<>();
    private ZipConverter zipConverter = new ZipConverter();

    ServerSendFiles(List<File> files){
        this.files = files;
    }

    private void sendFiles()  {
        zipConverter.zip(files, "files.zip");
        try {
            try  {
                server = new ServerSocket(123443);
                System.out.println("Сервер запущен!");

                clientSocket = server.accept();
                try {
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    String word = in.readLine();
                    out.flush();
                } finally {
                    //PS вместо блока finally можно использовать try with resources
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Сервер закрыт!");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    public void run() {
      sendFiles();
    }
}
