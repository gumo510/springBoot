package com.gumo.demo.utils;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.*;

public class XunFeiYunUtil extends WebSocketListener{

    private final CompletableFuture<String> future;
    private String answer = "";
    private String question; // 新增成员变量

    public static String hostUrl = "https://spark-api.xf-yun.com/v1.1/chat";
    public static String APPID = "b5703ca0";//从开放平台控制台中获取
    public static String APIKEY = "fb3e86cdfb5d720c30d7908b74c0f908";//从开放平台控制台中获取
    public static String APISecret = "NTdkZDg0NTIyOWFmM2U2ZDVhMzMyYzY4";//从开放平台控制台中获取
    public static final Gson json = new Gson();

    public static void main(String[] args) {
        CompletableFuture<String> future = new CompletableFuture<>();
        String question = "你是能帮我指定一个五一出行的计划么?";
        try {
            //构建鉴权httpurl
            String authUrl = getAuthorizationUrl(hostUrl,APIKEY,APISecret);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            String url = authUrl.replace("https://","wss://").replace("http://","ws://");
            Request request = new Request.Builder().url(url).build();
            WebSocket webSocket = okHttpClient.newWebSocket(request, new XunFeiYunUtil(future, question));

        } catch (Exception e) {
            e.printStackTrace();
        }
        // write your code here
    }


    public static CompletableFuture<String> fetchAnswer(String question) {
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            String authUrl = getAuthorizationUrl(hostUrl, APIKEY, APISecret);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            String url = authUrl.replace("https://", "wss://").replace("http://", "ws://");
            Request request = new Request.Builder().url(url).build();
            WebSocket webSocket = okHttpClient.newWebSocket(request, new XunFeiYunUtil(future, question));
            completeOnTimeout(future, question, 60, TimeUnit.SECONDS); // 配置中断和重试时间。这里设置为30秒。
        } catch (Exception e) {
            e.printStackTrace();
        }
        return future;
    }

    private static void completeOnTimeout(CompletableFuture<String> future, String question, long timeout, TimeUnit timeUnit) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            if (!future.isDone()) {
                future.completeExceptionally(new TimeoutException("请求超时"));
                fetchAnswer(question).thenAccept(future::complete);
            }
        }, timeout, timeUnit);
    }

    // 定义 getResult 方法 , 在构造函数中接收输入并赋值给成员变量
    public XunFeiYunUtil(CompletableFuture<String> future, String question) {
        this.future = future;
        this.question = question;
    }

    //鉴权url
   public static String getAuthorizationUrl(String hostUrl , String apikey ,String apisecret) throws Exception {
        //获取host
       URL url = new URL(hostUrl);
       //获取鉴权时间 date
       SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
//       System.out.println("format:\n" + format );
       format.setTimeZone(TimeZone.getTimeZone("GMT"));
       String date = format.format(new Date());
       //获取signature_origin字段
       StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n").
               append("date: ").append(date).append("\n").
               append("GET ").append(url.getPath()).append(" HTTP/1.1");
//       System.out.println("signature_origin:\n" + builder);
       //获得signatue
       Charset charset = Charset.forName("UTF-8");
       Mac mac = Mac.getInstance("hmacsha256");
       SecretKeySpec sp = new SecretKeySpec(apisecret.getBytes(charset),"hmacsha256");
       mac.init(sp);
       byte[] basebefore = mac.doFinal(builder.toString().getBytes(charset));
       String signature = Base64.getEncoder().encodeToString(basebefore);
       //获得 authorization_origin
       String authorization_origin = String.format("api_key=\"%s\",algorithm=\"%s\",headers=\"%s\",signature=\"%s\"",apikey,"hmac-sha256","host date request-line",signature);
       //获得authorization
       String authorization = Base64.getEncoder().encodeToString(authorization_origin.getBytes(charset));
       //获取httpurl
       HttpUrl httpUrl = HttpUrl.parse("https://" + url.getHost() + url.getPath()).newBuilder().//
               addQueryParameter("authorization", authorization).//
               addQueryParameter("date", date).//
               addQueryParameter("host", url.getHost()).//
               build();

        return httpUrl.toString();
    }

    //重写onopen
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        JsonObject frame = new JsonObject();
        JsonObject header = new JsonObject();
        JsonObject chat = new JsonObject();
        JsonObject parameter = new JsonObject();
        JsonObject payload = new JsonObject();
        JsonObject message = new JsonObject();
        JsonObject text = new JsonObject();
        JsonArray ja = new JsonArray();

        //填充header
        header.addProperty("app_id",APPID);
        header.addProperty("uid","123456789");
        //填充parameter
        chat.addProperty("domain","general");
        chat.addProperty("random_threshold",0);
        chat.addProperty("max_tokens",1024);
        chat.addProperty("auditing","default");
        parameter.add("chat",chat);
        //填充payload
        text.addProperty("role","user");
        text.addProperty("content",question);
        ja.add(text);

        message.add("text",ja);
        payload.add("message",message);
        frame.add("header",header);
        frame.add("parameter",parameter);
        frame.add("payload",payload);
        webSocket.send(frame.toString());
    }

    //重写onmessage
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        ResponseData responseData = json.fromJson(text,ResponseData.class);
        if(0 == responseData.getHeader().get("code").getAsInt()){
            if(2 != responseData.getHeader().get("status").getAsInt()){
                Payload pl = json.fromJson(responseData.getPayload(),Payload.class);
                JsonArray temp = (JsonArray) pl.getChoices().get("text");
                JsonObject jo = (JsonObject) temp.get(0);
                answer += jo.get("content").getAsString();
            }else {
                Payload pl1 = json.fromJson(responseData.getPayload(),Payload.class);
                JsonObject jsonObject = (JsonObject) pl1.getUsage().get("text");
                int prompt_tokens = jsonObject.get("prompt_tokens").getAsInt();
                JsonArray temp1 = (JsonArray) pl1.getChoices().get("text");
                JsonObject jo = (JsonObject) temp1.get(0);
                answer += jo.get("content").getAsString();

                webSocket.close(1000,"客户端主动断开链接");
                // 设置 future 的结果
                future.complete(answer);
            }
        }else{
            answer = "" + responseData.getHeader().get("code") +  responseData.getHeader().get("message");
            webSocket.close(1000,"返回结果错误断开链接");
            future.complete(answer);
        }
    }

    //重写onFailure
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        webSocket.close(1000,"失败断开链接");
        System.out.println(response);
        future.complete("AppIdQpsOverFlowError");
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        webSocket.close(1000,"失败断开链接");
    }

    class ResponseData{
        private  JsonObject header;
        private  JsonObject payload;

        public JsonObject getHeader() {
            return header;
        }

        public JsonObject getPayload() {
            return payload;
        }
    }

    class Header{
        private int code ;
        private String message;
        private String sid;
        private String status;

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getSid() {
            return sid;
        }

        public String getStatus() {
            return status;
        }
    }

    class Payload{
        private JsonObject choices;
        private JsonObject usage;

        public JsonObject getChoices() {
            return choices;
        }

        public JsonObject getUsage() {
            return usage;
        }
    }

    class Choices{
        private int status;
        private int seq;
        private JsonArray text;

        public int getStatus() {
            return status;
        }

        public int getSeq() {
            return seq;
        }

        public JsonArray getText() {
            return text;
        }
    }

}
