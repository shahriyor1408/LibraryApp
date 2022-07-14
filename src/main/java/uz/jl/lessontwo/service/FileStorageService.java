package uz.jl.lessontwo.service;

import uz.jl.lessontwo.domain.Uploads;

import javax.servlet.http.Part;

public interface FileStorageService {

    Uploads upload(Part partFile);
}
