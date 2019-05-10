package com.teradata.intenet.jvm;


public class Test07 {

    public static void alloc() {
        byte[] b = new byte[2];
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        //TLAB分配
        //参数：-XX:+UseTLAB -XX:+PrintTLAB -XX:+PrintGC -XX:TLABSize=102400 -XX:-ResizeTLAB -XX:TLABRefillWasteFraction=100 -XX:-DoEscapeAnalysis -server
        //-XX:+UseTLAB -XX:TLABSize=102400 -XX:TLABRefillWasteFraction=100 -XX:-DoEscapeAnalysis
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }

        long end = System.currentTimeMillis();

        System.out.println(end - start);

    }
}
