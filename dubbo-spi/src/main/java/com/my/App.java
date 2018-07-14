package com.my;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
/*        Protocol p = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("myProtocol");
        System.out.println(p.getDefaultPort());*/
        System.out.println(ExtensionLoader.getExtensionLoader(Protocol.class).getDefaultExtension());
    }
}
