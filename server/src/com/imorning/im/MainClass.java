package com.imorning.im;

import com.imorning.im.database.DBPool;

public class MainClass {
    public static void main(String[] args) {
        System.out.println(DBPool.getCon());
    }

}
