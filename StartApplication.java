import java.io.File;
import java.util.Scanner;

public class StartApplication {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String pathFile = getEnterValue("Введите директорию с которой начать искать файл: ");
        System.out.println(pathFile);
        File path = new File(pathFile);
        String nameFile = getEnterValue("Введите имя или часть имени для поиска файла: ");
        scanner.close();
        new SearchFileService(nameFile, path);
    }

    private static String getEnterValue(String textDisplayUser){
        System.out.print(textDisplayUser);
        String inputSearchValue = scanner.next();
        scanner.nextLine();
        return inputSearchValue;
    }

}
