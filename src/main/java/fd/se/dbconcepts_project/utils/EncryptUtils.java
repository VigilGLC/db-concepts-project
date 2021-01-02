package fd.se.dbconcepts_project.utils;


import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Component
public class EncryptUtils {

    public String encrypt(String raw) {
        return DigestUtils.md5DigestAsHex(raw.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validate(String raw, String encoded) {
        return encoded.equals(encrypt(raw));
    }


}
