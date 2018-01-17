package com.crw.common.utils.random;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RandomUtilTest {

    @Test
    public void test() {
        System.out.println(RandomUtil.randomInt(100));
        System.out.println(RandomUtil.randomInt(100, 110));

        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add("3");
        list.add("4");
        list.add(5l);

        System.out.println(RandomUtil.randomEles(list, 3));
        System.out.println(RandomUtil.randomEleSet(list, 3));
        System.out.println(RandomUtil.randomString(10));
        System.out.println(RandomUtil.randomNumbers(10));
        System.out.println(RandomUtil.randomNumber());
        System.out.println(RandomUtil.randomUUID());
        System.out.println(RandomUtil.simpleUUID());
        System.out.println(RandomUtil.simpleUUID(12));

    }
}
