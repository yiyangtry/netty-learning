package org.lwl.netty.chapter.source;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lwl.netty.chapter.rpc.protocol.Serializer;

public class TestGson {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new Serializer.ClassCodec()).create();
        System.out.println(gson.toJson(String.class));
    }
}
