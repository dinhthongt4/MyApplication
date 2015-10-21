package com.example.thongdt.myapplication.objects;

import java.util.ArrayList;

import lombok.Data;

/**
 * Created by thongdt on 20/10/2015.
 */

@Data
public class FullInformationVideo {
    private ArrayList<Item> items;

    public class Item {
         public Id id;
         public Snippet snippet;
    }

    public class Id {
        public String videoId;
    }
    public class Snippet {
        public String title;
        public Thumbnail thumbnails;
    }

    public class Thumbnail {
        public Hight high;
    }

    public class Hight {
        public String url;
    }
}
