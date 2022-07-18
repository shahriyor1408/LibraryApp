package uz.jl.lessontwo.service;

import uz.jl.lessontwo.dao.BookDao;
import uz.jl.lessontwo.domain.Uploads;

import javax.servlet.http.Part;

public interface FileStorageService {
    Uploads upload(Part partFile);

    Uploads getByPath(String requestedFile);

    Uploads uploadCover(Part file);
}
