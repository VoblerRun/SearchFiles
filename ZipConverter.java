import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipConverter {

    private static final int BUFFER = 80000;

    public FileOutputStream zip(List<File> files, String zipFileName) {
        try {

            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            for (File srcFile : files) {
                FileInputStream fis = new FileInputStream(srcFile);
                ZipEntry zipEntry = new ZipEntry(srcFile.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[4 * 1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
            zipOut.close();
            fos.close();
            return fos;
        } catch (IOException exception) {

        }
        return null;
    }
}
