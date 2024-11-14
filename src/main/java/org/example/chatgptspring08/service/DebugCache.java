package org.example.chatgptspring08.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class DebugCache {


    // Example method to check cache content
    @Autowired
    private CacheManager cacheManager;

    public void printCacheContent() {
        Cache cache = cacheManager.getCache("oddsCache");
        if (cache != null) {
            System.out.println("Cache Content: " + cache.getNativeCache());
        }
    }

}
