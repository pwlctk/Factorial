package pl.pwlctk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class SaveToDisk {
    static void writeToDisk(String invocation, String path) {
        try {
            Files.write(Paths.get(path), invocation.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
