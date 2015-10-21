package com.example.thongdt.myapplication.objects;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by thongdt on 15/10/2015.
 */

@Data
@NoArgsConstructor
public class InformationGame {
    private String date;
    private String nameHomeClub;
    private String nameAwayClub;
    private String time;
    private String urlVideo;
    private int type;
    private String title;
    private boolean isBg;
}
