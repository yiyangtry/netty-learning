package org.lwl.netty.chapter.test;


import org.lwl.netty.chapter.rpc.server.service.HelloService;
import org.lwl.netty.chapter.rpc.server.service.ServicesFactory;

public class TestServicesFactory {
    public static void main(String[] args) {
        HelloService service = ServicesFactory.getService(HelloService.class);
        System.out.println(service.sayHello("hi"));
    }
}
