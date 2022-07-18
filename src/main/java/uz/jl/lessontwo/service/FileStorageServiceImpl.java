package uz.jl.lessontwo.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import uz.jl.lessontwo.configs.ApplicationContextHolder;
import uz.jl.lessontwo.dao.BookDao;
import uz.jl.lessontwo.dao.Dao;
import uz.jl.lessontwo.domain.Uploads;

import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CompletableFuture;

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

    @Override
    public Uploads uploadCover(Part file) {
        try {
            String contentType = file.getContentType();
            String originalFilename = file.getSubmittedFileName();
            long size = file.getSize();
            String generatedName = System.currentTimeMillis() + ".png";
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
            PDDocument document = PDDocument.load(file.getInputStream());
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
            ImageIOUtil.writeImage(bufferedImage, uploadPath.toString(), 300);
            return uploads;
        } catch (IOException e) {
            throw new RuntimeException("Something wrong try again");
        }
    }
}
