package uz.jl.lessontwo.service;

import uz.jl.lessontwo.configs.ApplicationContextHolder;
import uz.jl.lessontwo.dao.BookDao;
import uz.jl.lessontwo.dao.Dao;
import uz.jl.lessontwo.domain.Uploads;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileStorageServiceImpl implements FileStorageService, Dao {

    private static FileStorageServiceImpl instance;

    public static FileStorageServiceImpl getInstance() {
        if (instance == null) {
            instance = new FileStorageServiceImpl();
        }
        return instance;
    }

    BookDao bookDao = ApplicationContextHolder.getBean(BookDao.class);
    private static final Path rootPath = Path.of("/home/shahriyor/IdeaProjects/lesson-two/apps/library/uploads");

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

    @Override
    public Uploads getByPath(String requestedFile) {
        return bookDao.getByPath(requestedFile);
    }
}
