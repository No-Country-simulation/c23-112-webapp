package Back_end.authentication.services;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static io.jsonwebtoken.JwsHeader.ALGORITHM;
import static javax.crypto.Cipher.SECRET_KEY;

@Service
public class EncryptionService {

    public String decryptId(String encryptedId) {
        return encryptedId; // El ID ya es un UUID en formato String, no hay que desencriptarlo
    }
}
