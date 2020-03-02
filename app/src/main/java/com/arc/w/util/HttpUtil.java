package com.arc.w.util;

import com.alibaba.fastjson.JSON;
import com.arc.w.model.request.AppContactRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 叶超
 * @since 2020/2/27 21:23
 */
public class HttpUtil {

    /**
     * POST方法提交HTTP请求，返回请求的结果
     *
     * @param url
     * @param params
     * @return 请求结果
     * @throws IOException
     */
    public static String postFun1(String url, String params) throws IOException {

        StringBuffer result = new StringBuffer();

        // 创建URL对象
        URL _url = new URL(url);
        // 创建HTTP连接
        /**
         * 使用.openConnection()方法实例化一个URLConnection对象
         * */
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();

        // 以下设置网络连接的相关参数
        /* 使用POST方法进行请求传递时，必须定义setDoInput和setDoOutput方法 */
        // 设置输入可用
        conn.setDoInput(true);
        // 设置输出可用
        conn.setDoOutput(true);

        // 设置不使用缓存
        conn.setUseCaches(false);

        // 设置连接超时的时间 - 5s
        conn.setConnectTimeout(5000);
        // 设置读取超时的时间 - 5s
        conn.setReadTimeout(5000);
        // 设置HTTP请求的方法 - POST
        conn.setRequestMethod("POST");

        // 设置HTTP请求属性 - 连接方式：保持
        conn.setRequestProperty("Connection", "Keep-Alive");
        // 设置HTTP请求属性 - 字符集：UTF-8
        conn.setRequestProperty("Charset", "UTF-8");
        // 设置HTTP请求属性 - 传输内容的类型 - 简单表单
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        // 设置HTTP请求属性 - 传输内容的长度
        conn.setRequestProperty("Content-Length", String.valueOf(params.length()));

        // 设置HTTP请求属性 - 用户代理
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
        // 发送参数 ，采用字符流发送数据
        PrintWriter pw = new PrintWriter(conn.getOutputStream());
        pw.write(params);
        pw.flush();
        pw.close();
        // 获取返回的结果
        if (200 == conn.getResponseCode()) {// 判断状态码
            // 读取服务器返回的 结果 - 字符流
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            // 每次读取一行
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        }
        // 关闭HTTP连接
        conn.disconnect();
        return result.toString();
    }

    /**
     * GET方法提交HTTP请求，返回请求的结果
     *
     * @param url
     * @return 请求的结果
     * @throws IOException
     */
    public static String getFun1(String url) throws IOException {

        StringBuffer result = new StringBuffer();
        // 创建URL对象
        URL _url = new URL(url);
        // 创建HTTP连接
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
        // 设置网络连接的相关参数
        // 设置输入可用
        conn.setDoInput(true);
        // 设置输出可用
        conn.setDoOutput(true);
        // 设置不使用缓存
        conn.setUseCaches(false);
        // 设置连接超时的时间 - 5s
        conn.setConnectTimeout(5000);
        // 设置读取超时的时间 - 5s
        conn.setReadTimeout(5000);
        // 设置HTTP请求的方法 - GET
        conn.setRequestMethod("GET");
        // 设置HTTP请求属性 - 连接方式：保持
        conn.setRequestProperty("Connection", "Keep-Alive");
        // 设置HTTP请求属性 - 字符集：UTF-8
        conn.setRequestProperty("Charset", "UTF-8");
        // 设置HTTP请求属性 - 用户代理
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
        // 获取返回的结果
        if (200 == conn.getResponseCode()) {// 判断状态码
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            // 每次读取一行
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        }
        // 关闭HTTP连接
        conn.disconnect();
        return result.toString();
    }

}
