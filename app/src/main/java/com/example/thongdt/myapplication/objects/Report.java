package com.example.thongdt.myapplication.objects;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by thongdt on 16/10/2015.
 */

@Data
@NoArgsConstructor
public class Report {
    private String urlImage;
    private String title;
    private String information;
    private String link;
}
