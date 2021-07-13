package Lesson3;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FilesApp {
    private static File file;
    BufferedOutputStream bos;

    public static void main(String[] args) throws IOException {
        file = new File("demo.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        byte[] bytes = "Hello world".getBytes();

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bos.write(bytes);
        bos.flush();

//        FileOutputStream fos = new FileOutputStream("demo.txt");
//        fos.write(bytes);
    }
}
