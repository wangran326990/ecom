package com.atguigu.gulimall.search.Thread;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class GenericTests {

    public static void main(String[] args) {
        List<House> list = new ArrayList<>();
        list.add(new House());
        paintAllBuildings(list);
    }
    public static void paintAllBuildings(List<?> buildings) {
    }

}

class Building {

}


class House extends Building {

}