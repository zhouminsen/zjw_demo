import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhoujiawei on 2020-08-03.
 */
public class HttpUtils {

    @Test
    public void test() throws IOException, NoSuchAlgorithmException {
        //秘钥
        String appSecret = "A272ADAA36372C828E057F029D300BA2";
        //业务数据
        String data = "{\"dc\":\"SH03\",\"expressCode\":\"YUNDA\",\"lineList\":[{\"collectValue\":0.0,\"itemList\":[{\"count\":1,\"itemDesc\":\"黑色\",\"itemName\":\"外套\"}],\"nodeId\":\"kd100\",\"orderNo\":\"2012121715001\",\"price\":20.0,\"receiver\":{\"address\":\"江苏省徐州市新沂市湖东路999号\",\"city\":\"江苏省，徐州市，新沂市\",\"company\":\"凯利\",\"mobile\":\"13761960078\",\"name\":\"王小虎\",\"phone\":\"8592652\"},\"sender\":{\"address\":\"江苏省徐州市新沂市湖东路999号\",\"city\":\"江苏省，徐州市，新沂市\",\"company\":\"凯利\",\"mobile\":\"13761960078\",\"name\":\"王小虎\",\"phone\":\"8592652\"},\"size\":\"20,20,20\",\"tradeNo\":\"AAA1000012365\",\"weight\":11.0}],\"wareHouseCode\":\"SHA3\"}";
        Map<String, String> map = new HashMap<>();
        //appkey
        map.put("appKey", "31501308");
        //业务数据格式
        map.put("format", "json");
        //版本号
        map.put("version", "1.0");
        //请求方
        map.put("method", "com.cjlogistics.sos.express.BookService");
        String sign = makeQmSign(map, data, appSecret);
        map.put("sign", sign);
        map.put("data", data);
        map.put("appSecret", appSecret);
        URL httpUrl = new URL("http://10.131.27.18:8054/ifm/expressService/requestData");
        URLConnection connection = httpUrl.openConnection();
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
        StringBuilder buffer = new StringBuilder();
        StringBuilder result = new StringBuilder();
        String line;
        if (map != null && map.size() > 0) {
            Iterator var8 = map.keySet().iterator();

            while (var8.hasNext()) {
                line = (String) var8.next();
                buffer.append(line).append("=").append(URLEncoder.encode((String) map.get(line), "utf-8")).append("&");
            }
            buffer.deleteCharAt(buffer.toString().length() - 1);
        }
        printWriter.print(buffer.toString());
        printWriter.flush();
        connection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }

        bufferedReader.close();
        System.out.println(result);
    }

    public static String makeQmSign(Map<String, String> params, String body, String secret) throws NoSuchAlgorithmException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        query.append(secret);
        for (String key : keys) {
            if ("sign".equals(key)) {
                continue; // 签名时不计算sign本身
            }
            String value = params.get(key);
            query.append(key).append(value);
        }
        // 第三步：把请求主体拼接在参数后面
        if (body != null) {
            query.append(body);
        }
        query.append(secret);
        // 第四步：使用MD5加密
        return getMd5(query.toString()).toUpperCase();
    }

    public static String getMd5(String str) throws NoSuchAlgorithmException {
        // 生成一个MD5加密计算摘要
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 计算md5函数
        md.update(str.getBytes());
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
        String md5 = new BigInteger(1, md.digest()).toString(16);
        //BigInteger会把0省略掉，需补全至32位
        return fillMd5(md5);
    }

    public static String fillMd5(String md5) {
        return md5.length() == 32 ? md5 : fillMd5("0" + md5);
    }
}

