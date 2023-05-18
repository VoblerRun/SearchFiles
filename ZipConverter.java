import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipConverter {

    private static final int BUFFER = 80000;

    public ZipOutputStream zip(List<File> files, String zipFileName) {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)));
            byte data[] = new byte[BUFFER];
            for (File file : files) {
                processFile(out, data, file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(out);
        }
        return out;
    }

    private void processFile(ZipOutputStream out, byte[] data, File file) {
        BufferedInputStream origin = null;
        try {
            origin = new BufferedInputStream(new FileInputStream(file), BUFFER);
            ZipEntry entry = new ZipEntry(file.toString());
            out.putNextEntry(entry);
            int count;
            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(origin);
        }
    }

    private void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}
