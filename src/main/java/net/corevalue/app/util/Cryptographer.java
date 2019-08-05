package net.corevalue.app.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;
import org.joda.time.DateTime;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

@UtilityClass
public class Cryptographer {
    public final int TOKEN_EXPIRE_MINS = 20;

    public String createRSAToken(String projectId, String privateKeyFile) throws Exception {
        DateTime now = new DateTime();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(now.toDate())
                .setExpiration(now.plusMinutes(TOKEN_EXPIRE_MINS).toDate())
                .setAudience(projectId);
        byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyFile));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return jwtBuilder.signWith(SignatureAlgorithm.RS256, kf.generatePrivate(spec)).compact();
    }
}
