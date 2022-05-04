package com.zzk.study.library.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/14 9:40 PM
 */
public class Client {
    private static final String THRIFT_HOST = "127.0.0.1";
    private static final int THRIFT_PORT = 9999;
    public static void main(String[] args) throws TTransportException {
        TFramedTransport m_transport = new TFramedTransport(new TSocket(THRIFT_HOST, THRIFT_PORT));
        TProtocol protocol = new TBinaryProtocol(m_transport);
        gen.TestThriftService.Client testClient = new gen.TestThriftService.Client(protocol);

        try {
            m_transport.open();

            String res = testClient.getStr("test1", "test2");
            System.out.println("res = " + res);
            m_transport.close();
        } catch (TException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
