import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class   Main {
    public static void main(String[] args) {
        Path filesPath = FileSystems.getDefault().getPath("C:\\Users\\LUKMAJ\\Desktop\\resTime");
        DirectoryStream.Filter<Path> isDirectoryFilter = p -> Files.isDirectory(p);
        ReadFile readFile = new ReadFile();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(filesPath, isDirectoryFilter)) {
            MyFileVisitor myFileVisitor = new MyFileVisitor();
            Files.walkFileTree(filesPath, myFileVisitor);
            for (Path p : myFileVisitor.getPaths()) {
                readFile.readFile(new File(p.toString()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        readFile.getResolutionsTime();
    }
}

