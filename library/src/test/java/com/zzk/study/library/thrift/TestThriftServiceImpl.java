package com.zzk.study.thrift;

import org.apache.thrift.TException;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/1/14 9:30 PM
 */
public class TestThriftServiceImpl implements gen.TestThriftService.Iface {
    @Override
    public String getStr(String srcStr1, String srcStr2) throws TException {

        long startTime = System.currentTimeMillis();
        String res = srcStr1 + srcStr2;
        long stopTime = System.currentTimeMillis();

        System.out.println("[getStr]time interval: " + (stopTime-startTime));
        return res;
    }

    @Override
    public int getInt(int val) throws TException {
        long startTime = System.currentTimeMillis();
        int res = val * 10;
        long stopTime = System.currentTimeMillis();

        System.out.println("[getInt]time interval: " + (stopTime-startTime));
        return res;
    }
}
