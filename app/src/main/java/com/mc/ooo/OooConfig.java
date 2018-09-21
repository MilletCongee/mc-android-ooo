package com.mc.ooo;

/**
 * Create by ZzzCy
 * on 2018/8/23
 * e-mail: zzzcy1014@gmail.com
 */
public class OooConfig {
    public static final String client_id = "aQmOQuR6rRY7WzamqeHi";
    public static final String client_secret = "RTuML00PGLGrMas0sisVtncdAbge8V2D";
    public static final String grant_type = "authorization_code";
    public static final String redirect_uri = "millet_congee";
    /**
     * 调用 /action/oauth2/authorize 接口返回的授权码(grant_type为authorization_code时必选)
     * */
    public static final String code = "yingyoubao";

    /**
    *上次调用 /action/oauth2/token
     * 接口返回的refresh_token(grant_type为refresh_token时必选)*/
    public static final String refresh_token = "yingyoubao";
    public static final String dataType = "json";
    public static final String callback = "\tjson";


}