import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class SearchFileService implements Runnable{
    private String nameFile;
    private File path;
    private ArrayList<File> foundFiles = new ArrayList<>();
    private ArrayList<Thread> threads = new ArrayList<>();
    private File[] files;
    private Thread thread;

    private  ArrayList<File> allFiles = new ArrayList<>();

    public SearchFileService(String nameFile, File path) {
        super();
        this.nameFile = nameFile;
        this.path = path;
        files = path.listFiles();
        thread = new Thread(this);
        thread.start();
    }

    public void searching(File[] files){
        if(files == null) return;

        for(File file : files){
            if(file.isFile() && file.getName().contains(nameFile)){
                System.out.format("%s has found %s\n", Thread.currentThread().getName(), file.getAbsolutePath(), file.getName());
                allFiles.add(file);
            }

            if(file.isDirectory()){
                SearchingThread searchingThread = new SearchingThread(file.listFiles(), this);
                Thread newThread = new Thread(searchingThread);
                newThread.start();
                threads.add(newThread);
            }
        }
    }

    @Override
    public void run() {
        searching(files);
        for (int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (foundFiles.size() == 0) {
            System.out.println("Ничего больше не найдено!");
            if(foundFiles.size() != 0) {
                ServerSendFiles serverSendFiles = new ServerSendFiles(foundFiles);
                Thread newThread = new Thread(serverSendFiles);
                newThread.start();
            }
        } else {
            for (File i : foundFiles) {
                System.out.println(i.getAbsoluteFile());
            }
        }
    }
}


