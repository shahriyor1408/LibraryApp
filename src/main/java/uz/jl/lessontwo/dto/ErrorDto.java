package uz.jl.lessontwo.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDto {
    private String message;
    private String detailedMessage;
    private String path;
    @Builder.Default
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
}
