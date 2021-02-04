package com.tracenull.j20210204;

import java.util.concurrent.TimeUnit;

public class TestClassLoading {

    public static class A {
        static {
            System.out.println("class A init");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new B();
        }

        public static void test() {
            System.out.println("aaa");
        }
    }

    public static class B {
        static {
            System.out.println("class B init");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new A();
        }

        public static void test() {
            System.out.println("bbb");
        }
    }

    public static void main(String[] args) {
        new Thread(() -> A.test()).start();
        new Thread(() -> B.test()).start();
    }
}
