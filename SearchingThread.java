import java.io.File;

public class SearchingThread implements Runnable{
    private SearchFileService service;
    private File[] files;

    public SearchingThread(File[] files, SearchFileService service){
        this.service = service;
        this.files = files;
    }

    @Override
    public void run() {
    service.searching(files);
    }
}
