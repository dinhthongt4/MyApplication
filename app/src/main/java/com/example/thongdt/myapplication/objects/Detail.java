package com.example.thongdt.myapplication.objects;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by thongdt on 19/10/2015.
 */

@Data
@NoArgsConstructor
public class Detail {
    private int type;
    private String information;
    private String title;
    private String date;
    private String author;
    private String urlImage;
    private String urlNews;
}
