package com.jamison.test;

public class Demo1 {
    public static void main(String[] args) {
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(5);
                } catch(Exception e) {}
            }
        });
    }
}
