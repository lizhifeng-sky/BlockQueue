package com.android.queue.block;



/**
 * @author lizhifeng
 * @date 2020/9/9 09:31
 */
public class BlockTaskBean{
    private UserBean data;
    private long time;

    public BlockTaskBean(UserBean data, long time) {
        this.data = data;
        this.time = time;
    }

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BlockTaskBean{" +
                "data=" + data +
                ", time=" + time +
                '}';
    }
}
