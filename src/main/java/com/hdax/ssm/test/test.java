package com.hdax.ssm.test;

import org.apache.shiro.crypto.hash.Md5Hash;

public class test {
    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","qwerty",2);
        System.out.println(md5Hash.toString());
    }
}
