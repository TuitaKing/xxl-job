package com.xxl.job.admin.util;

import org.apache.commons.lang.StringUtils;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lijing on 2017/7/12.
 */
public class UUIDGenerator {
    private static final AtomicInteger counter;
    private static final int JVM;
    private static final int IP;

    static {
        counter = new AtomicInteger(0);
        JVM = (int) (System.currentTimeMillis() >>> 8);
        int ipadd;
        try {
            ipadd = toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }

    private static int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    private static short getCount() {
        counter.compareAndSet(Short.MAX_VALUE, 0);
        return (short) counter.incrementAndGet();
    }

    private static String format(int intValue) {
        return StringUtils.leftPad(Integer.toHexString(intValue), 8, "0");
    }

    private static String format(short shortValue) {
        return StringUtils.leftPad(Integer.toHexString(shortValue), 4, "0");
    }

    /**
     * 生成一个由当年系统时间、服务器IP、JVM时间、AtomicInteger共同组成的32位的不重复ID
     * @return
     */
    public static String generate() {
        long now = System.currentTimeMillis();
        short hiTime = (short) (now >>> 32);
        int loTime = (int) now;
        StringBuilder sb = new StringBuilder(32)
                .append(format(hiTime))
                .append(format(loTime))
                .append(format(IP))
                .append(format(JVM))
                .append(format(getCount()));
        sb.setCharAt(0, 'f');
        return sb.toString();
    }
}
