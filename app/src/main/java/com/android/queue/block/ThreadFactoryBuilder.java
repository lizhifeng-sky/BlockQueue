package com.android.queue.block;

import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lizhifeng
 * @date 2020/9/9 15:14
 */
public class ThreadFactoryBuilder {
    private String nameFormat = null;
    private Boolean daemon = null;
    private Integer priority = null;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;
    private ThreadFactory backingThreadFactory = null;

    public ThreadFactoryBuilder() {
    }

    public ThreadFactoryBuilder setNameFormat(String nameFormat) {
        format(nameFormat, 0);
        this.nameFormat = nameFormat;
        return this;
    }

    public ThreadFactory build() {
        return doBuild(this);
    }

    private static ThreadFactory doBuild(ThreadFactoryBuilder builder) {
        final String var1 = builder.nameFormat;
        final Boolean var2 = builder.daemon;
        final Integer var3 = builder.priority;
        final Thread.UncaughtExceptionHandler var4 = builder.uncaughtExceptionHandler;
        final ThreadFactory var5 = builder.backingThreadFactory != null
                ? builder.backingThreadFactory : Executors.defaultThreadFactory();
        final AtomicLong var6 = var1 != null ? new AtomicLong(0L) : null;
        return runnable -> {
            Thread var2x = var5.newThread(runnable);
            if (var1 != null) {
                var2x.setName(ThreadFactoryBuilder.format(var1, var6.getAndIncrement()));
            }

            if (var2 != null) {
                var2x.setDaemon(var2);
            }

            if (var3 != null) {
                var2x.setPriority(var3);
            }

            if (var4 != null) {
                var2x.setUncaughtExceptionHandler(var4);
            }

            return var2x;
        };
    }

    private static String format(String format, Object... args) {
        return String.format(Locale.ROOT, format, args);
    }
}
