package com.car.rental.project.social;


import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class FBConnection {

    public static final String FB_APP_ID ="228175701665524";
    public static final String FB_APP_SECRET = "11a490d60c0e9fba44b38bc440555872";
    public static final String REDIRECT_URI = "http://localhost:9090/fblogin";

    String accessToken = "";

    public String getFBAuthUrl() {
        String fbLoginUrl = "";
        fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
                + FB_APP_ID + "&redirect_uri="
                + URLEncoder.encode(FBConnection.REDIRECT_URI, StandardCharsets.UTF_8)
                + "&scope=email";
        return fbLoginUrl;
    }

    public String getFBGraphUrl(String code) {
        String fbGraphUrl = "";
        fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
                + "client_id=" + FB_APP_ID + "&redirect_uri="
                + URLEncoder.encode(FBConnection.REDIRECT_URI, StandardCharsets.UTF_8)
                + "&client_secret=" + FB_APP_SECRET + "&code=" + code;
        return fbGraphUrl;
    }

    public String getAccessToken(String code) {
        if ("".equals(accessToken)) {
            URL fbGraphURL;
            try {
                fbGraphURL = new URL(getFBGraphUrl(code));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Invalid code received " + e);
            }
            URLConnection fbConnection;
            StringBuffer b = null;
            try {
                fbConnection = fbGraphURL.openConnection();
                BufferedReader in;
                in = new BufferedReader(new InputStreamReader(
                        fbConnection.getInputStream()));
                String inputLine;
                b = new StringBuffer();
                while ((inputLine = in.readLine()) != null)
                    b.append(inputLine + "\n");
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to connect with Facebook "
                        + e);
            }
            JSONObject json = new JSONObject(b.toString());
            String token = json.getString("access_token");
            String ex = Integer.toString(json.getInt("expires_in"));
            accessToken = "access_token="+token+"&expires_in="+ex;
            if (accessToken.startsWith("{")) {
                throw new RuntimeException("ERROR: Access Token Invalid: "
                        + accessToken);
            }
        }
        return accessToken;
    }
}