package com.qzsoft.tah.config;

public class DBContextHolder {

    /**
     * ThreadLocal数据隔离，但是并非数据的复制，而是在每一个线程中创建一个新的数据对象，然后每一个线程使用的是不一样的
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDataSource(String dataSource) {
        CONTEXT_HOLDER.set(dataSource);
    }

    public static String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }

}
