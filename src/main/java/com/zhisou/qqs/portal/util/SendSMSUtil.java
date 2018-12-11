package com.zhisou.qqs.portal.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 手机短信发送类
 *
 * @author JiangTao
 * @version 1.0
 * @since 2017-3-3
 */
public class SendSMSUtil {
    private static final String SMS_URL = "http://sms.kingtto.com:9999/sms.aspx";
    private static final String SMS_ACTION = "send";
    private static final String SMS_ACCOUNT = "13537858173";
    private static final String SMS_PASSWORD = "ac82202592";
    private static final String SMS_USERID = "4389";
    public static final String SMS_CONTENT = "【米兰云】您的手机验证码是：{验证码}，该验证码{lostTime}分钟内有效。";

    public static boolean sendSMS(String content, String mobile) {
        boolean isSendSuccess = false;
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        String httpUrl = SMS_URL + "?action=" + SMS_ACTION + "&userid=" + SMS_USERID + "&account=" + SMS_ACCOUNT
                + "&password=" + SMS_PASSWORD + "&mobile=" + mobile + "&content=" + content;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            String result = sbf.toString();

            if (result != null && result.contains("<returnstatus>Success</returnstatus>")) {
                isSendSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSendSuccess;
    }

    public static void main(String[] args) {
        String content = SMS_CONTENT.replace("{验证码}", "123456");
        System.out.println(content);

        boolean jsonResult = sendSMS(content, "138661390493");
        System.out.println(jsonResult);
    }
}
