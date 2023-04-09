package com.xtool.manager.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TicketUtils {
    /**
     * 向list里生成count个随机数，随机数的范围[start,end)
     */
    public static void randomNumber(List<Integer> list, int count, int start, int end,boolean isRepeat) {
        Random r = new Random();
        int temp = 0;
        while (temp != count) {
            //生成1个start到end的随机数,不包括end
            int randomNumber = r.ints(1, start, end).findFirst().getAsInt();
            if (!isRepeat){
                if ( !list.contains(randomNumber)) {
                    list.add(randomNumber);
                    temp++;
                }
            }else{
                list.add(randomNumber);
                temp++;
            }
        }
    }

    /**
     * 福彩3D
     */
    public static List<String> threeD(int amount) {
        List<String> result=new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            List<Integer> list = new ArrayList<>();
            randomNumber(list, 3, 0, 10,true);
            Collections.sort(list);
            result.add(list.stream().map(String::valueOf).collect(Collectors.joining(", ")));
            }
        return result;
    }

    /**
     * 七乐彩
     */
    public static List<String> sevenTicket(int amount) {
        List<String> result=new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            List<Integer> list = new ArrayList<>();
            randomNumber(list, 7, 1, 31,false);
            Collections.sort(list);
            result.add(list.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        }
        return result;
    }


    /**
     * 七星彩
     */
    public static List<String> sevenStartTicket(int amount) {
        List<String> result=new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            List<Integer> list = new ArrayList<>();
            randomNumber(list, 6, 0, 10,true);
            Collections.sort(list);
            randomNumber(list, 1, 0, 15,false);
            result.add(list.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        }
        return result;
    }

    /**
     * 排列3
     */
    public static List<String> arrangeThree(int amount) {
        List<String> result=new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            List<Integer> list = new ArrayList<>();
            randomNumber(list, 3, 0, 10,true);
            Collections.sort(list);
            result.add(list.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        }
        return result;
    }

    /**
     * 排列5
     */
    public static List<String> arrangeFive(int amount) {
        List<String> result=new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            List<Integer> list = new ArrayList<>();
            randomNumber(list, 5, 0, 10,true);
            Collections.sort(list);
            result.add(list.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        }
        return result;
    }

    /**
     * 大乐透模拟号码
     */
    public static List<String> lotteryTicket(int amount) {
        List<String> result=new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            List<Integer> list = new ArrayList<>();
             randomNumber(list, 5, 1, 36,false);
            Collections.sort(list);
             randomNumber(list, 2, 1, 13,false);
            if (list.get(5) > list.get(6)) {
                int temp = list.get(5);
                list.set(5, list.get(6));
                list.set(6, temp);
            }
            result.add(list.stream().map(String::valueOf).collect(Collectors.joining(", ")));
            //System.out.println("大乐透第" + i + "注：" + list.stream().map(v->String.valueOf(v)).collect(Collectors.joining(", ")));
        }
        return result;
    }

    /**
     * 双色球模拟号码
     */
    public static List<String> twoColorBall(int amount) {
        List<String> result=new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            List<Integer> list = new ArrayList<>();
            randomNumber(list, 6, 1, 34,false);
            Collections.sort(list);
            randomNumber(list, 1, 1, 17,false);
            result.add(list.stream().map(String::valueOf).collect(Collectors.joining(", ")));
            //System.out.println("双色球第" + i + "注：" + list.stream().map(v->String.valueOf(v)).collect(Collectors.joining(", ")));
        }
        return result;
    }

}
