package com.etoak.test;

import org.dom4j.DocumentException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


/**
 * Created by zhangcheng on 2016/7/3.
 */
public class StreamTest {
    public static void main(String[] args){
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens =
                Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);
        System.out.println(evens);
    }

    Dom4jDemo dom4jDemo = new Dom4jDemo();


}
