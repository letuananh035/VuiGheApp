package com.demo.tuananh.demo;

import java.io.Serializable;
import java.util.List;

public class Genres implements Serializable {
    List<DataGenres> data;

    public class DataGenres implements Serializable{
        public String id;
        public String name;
        public String slug;
    }
}

