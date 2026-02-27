package com.platform.user.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private Long instructorId;
    private String category;
    private Double price;
    private Integer duration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
