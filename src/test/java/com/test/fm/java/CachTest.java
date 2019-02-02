package com.test.fm.java;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.Test;

import javax.tools.JavaFileObject;
import java.util.concurrent.TimeUnit;

public class CachTest {

    @Test
    public void test1() {
        Cache<String, JavaFileObject> fileObjects = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .build();

        JavaFileObject javaFileObject = fileObjects.getIfPresent("fdsa");
        System.out.println(javaFileObject);
    }
}
