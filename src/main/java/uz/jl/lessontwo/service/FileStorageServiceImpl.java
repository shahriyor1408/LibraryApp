package uz.jl.lessontwo.service;

import uz.jl.lessontwo.dao.BookDao;
import uz.jl.lessontwo.domain.Uploads;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileStorageServiceImpl implements FileStorageService {
    BookDao bookDao;
    private static final Path rootPath = Path.of("apps/libAppEE/uploads");

    static {
        if (!Files.exists(rootPath)) {
            try {
                Files.createDirectories(rootPath);
            } catch (IOException e) {
                throw new RuntimeException("");
            }
        }
    }

    @Override

    public Uploads upload(Part partFile) {
        try {
            String contentType = partFile.getContentType();
            String originalFilename = partFile.getSubmittedFileName();
            long size = partFile.getSize();
            String filenameExtension = originalFilename.split("\\.")[1];
            String generatedName = System.currentTimeMillis() + "." + filenameExtension;
            String path = "/uploads/" + generatedName;
            Uploads uploads = Uploads
                    .builder()
                    .contentType(contentType)
                    .originalName(originalFilename)
                    .size(size)
                    .generatedName(generatedName)
                    .path(path)
                    .build();
            Path uploadPath = rootPath.resolve(generatedName);
            bookDao.save(uploads);
            Files.copy(partFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
            return uploads;
        } catch (IOException e) {
            throw new RuntimeException("Something wrong try again");
        }
    }
}
