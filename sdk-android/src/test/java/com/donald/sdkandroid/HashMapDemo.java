package com.donald.sdkandroid;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author donald
 * @date 2021/08/30
 */
public class HashMapDemo {

    public static void main(String[] args) {

        Map<Integer, String> map = new LinkedHashMap<>(16, 0.75f, true);
        map.put(2, "牛yyds");
        map.put(1, "哈xswl");
        map.put(3, "牛哇牛哇");
        map.put(2, "牛yyds-1");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }
}
