package pe.edu.upeu.SISRA.utilidades;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Random;

import javax.annotation.Resource;

import java.nio.file.FileSystem;

import org.apache.tomcat.jni.Directory;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class Archivos {
    public static String directorio = System.getProperty("user.dir") + "/archivos";

    public static String almacenarArchivo(MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        File fls = new File(directorio);
        if (!fls.exists() && !fls.isFile()) {
            Path path = Paths.get(directorio);
            Files.createDirectory(path);
        }

        String resl = (new Random().nextInt(6000)) + file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(directorio, resl);

        fileNames.append(fileNameAndPath);

        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resl;
    }

    public static void EliminarArchivo(String nombre) {
        Path fileNameAndPath = Paths.get(System.getProperty("user.dir") + "/archivos", nombre);
        try {
            Files.delete(fileNameAndPath);
        } catch (IOException e) {
            System.out.print(e + " RUTA:" + fileNameAndPath);
        }
    }

}
