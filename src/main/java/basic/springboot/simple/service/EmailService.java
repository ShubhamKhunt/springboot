package basic.springboot.simple.service;

import basic.springboot.simple.entity.User;
import basic.springboot.simple.service.impl.EmailServiceImpl;
import org.json.JSONObject;

public interface EmailService {
    public boolean sendMail(User user, JSONObject data, EmailServiceImpl.EventType action);

    public boolean sendMail(JSONObject identity, JSONObject data, EmailServiceImpl.EventType action);
}
