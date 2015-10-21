package com.example.thongdt.myapplication.objects;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by thongdt on 14/10/2015.
 */

@Data
@NoArgsConstructor
public class Menu {
    private String title;
    private String url;
    private String urlNotStart;
    private String urlStarted;
    private boolean isSelected;
}
