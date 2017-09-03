package utils;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 23:32
 **/

import com.futurever.elm.api.redis.JedisUtil;
import com.futurever.elm.api.utils.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 23:32
 **/
public class JedisUtilTest extends Thread {
    int i = 0;

    public JedisUtilTest(int i) {
        this.i = i;
    }

    public void run() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        JedisUtil.setString("foo", time);
        String foo = JedisUtil.getString("foo");
        System.out.println("【输出>>>>】foo:" + foo + " 第：" + i + "个线程" + "当前时间：" + DateUtils.currentTimestampStr());
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            JedisUtilTest t = new JedisUtilTest(i);
            t.start();
        }
    }
}
