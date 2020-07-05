package com.test.first_exercise;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultItemModel {
    private String title;
    private String text;
    private String url;

    public ResultItemModel(String title, String url){
        this.title = title;
        this.url = url;
    }
}
