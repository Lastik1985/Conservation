import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress Player1 = new GameProgress(100, 5, 99, 5);
        GameProgress Player2 = new GameProgress(80, 8, 88, 8);
        GameProgress Player3 = new GameProgress(77, 7, 77, 7);

        String file1 = "C:\\Users\\galka\\IdeaProjects\\Games\\savegames\\save1.dat";
        String file2 = "C:\\Users\\galka\\IdeaProjects\\Games\\savegames\\save2.dat";
        String file3 = "C:\\Users\\galka\\IdeaProjects\\Games\\savegames\\save3.dat";

        List<String> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file3);

        saveGame(file1, Player1);
        saveGame(file2, Player2);
        saveGame(file3, Player3);

        String filesDirPath = "C:\\Users\\galka\\IdeaProjects\\Games\\savegames\\zip_output.zip";

        zipFiles(filesDirPath, files);
        deleteSerializedFiles(files);

    }

    public static void saveGame(String file, GameProgress GameProgress) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(GameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String filesDirPath, List<String> files) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(filesDirPath))) {
            int i = 0;
            for (String file : files) {
                i++;
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(String.format("save%d.txt", i));
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
    }
    public static void deleteSerializedFiles(List<String> filesPath) {
        for (String file : filesPath) {
            File fileToBeDeleted = new File(file);
            fileToBeDeleted.delete();
        }
    }

}
