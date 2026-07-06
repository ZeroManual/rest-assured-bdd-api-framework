package com.company.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostResponse {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}
