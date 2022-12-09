package basic.springboot.simple.service.impl;

import basic.springboot.simple.entity.User;
import basic.springboot.simple.service.EmailService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {
    private String storeUrl = "https://api.getvero.com/";
    private String sendEventPath = "api/v2/events/track";

    @Value("${vero.auth.token}")
    private String AuthToken;
    private String emailPath = null;

    public EmailServiceImpl(){
        emailPath = this.storeUrl + this.sendEventPath;
    }
    
    public enum EventType{
        ONBOARD_USER("Onboard User"),
        INVITE_USER("Invite User"),
        ORDER_PLACED("Order Placed");

        private String event = null;

        private EventType(String event) {
            this.event = event;
        }
    }

    public boolean sendMail(User user, JSONObject data, EmailServiceImpl.EventType action) {
        try{
            System.out.println("Vero Event initiated!! " + action.event);

            JSONObject defaultJSON = new JSONObject();

            // User attributes to deliver email
            JSONObject identity = new JSONObject();
            identity.put("id", user.getEmail());
            identity.put("email", user.getEmail());
            identity.put("username", user.getEmail());
            identity.put("Username", user.getEmail());
            identity.put("name", user.getName());
            identity.put("Name", user.getName());

            defaultJSON.put("auth_token", AuthToken);
            defaultJSON.put("identity", identity);
            defaultJSON.put("event_name", action.event);
            defaultJSON.put("data", data);

            URL url = new URL(emailPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = defaultJSON.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            System.out.println("Vero ResponseCode : " + responseCode);

            return true;
        }catch(JSONException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Date : 09-12-2022
     * Update : sendMail method cloned to send email to end users (contacts)
     * @author : Shubham Khunt
     * */
    public boolean sendMail(JSONObject identity, JSONObject data, EmailServiceImpl.EventType action) {
        try{
            System.out.println("Vero Event initiated!! " + action.event);

            JSONObject defaultJSON = new JSONObject();

            // User attributes to deliver email
            /* JSONObject identity = new JSONObject();
            identity.put("id", user.getEmail());
            identity.put("email", user.getEmail());
            identity.put("username", user.getEmail());
            identity.put("Username", user.getEmail());
            identity.put("name", user.getName());
            identity.put("Name", user.getName()); */

            defaultJSON.put("auth_token", AuthToken);
            defaultJSON.put("identity", identity);
            defaultJSON.put("event_name", action.event);
            defaultJSON.put("data", data);

            URL url = new URL(emailPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = defaultJSON.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            System.out.println("Vero ResponseCode : " + responseCode);

            return true;
        }catch(JSONException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
