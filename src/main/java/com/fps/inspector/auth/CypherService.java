package com.fps.inspector.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.List;
import java.util.UUID;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;

public class CypherService {

	public static String generateJWT(PrivateKey key, String subject, List<String> groups) {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .keyID("inspectorKey")
                .build();

        MPJWTToken token = new MPJWTToken();
        token.setAud("inspectorGt");
        token.setIss("http://inspeccion.legall.com.pe");  // Must match the expected issues configuration values
        token.setJti(UUID.randomUUID().toString());

        token.setSub(subject);
        token.setUpn(subject);

        token.setIat(System.currentTimeMillis());
        // Tiempo expiracion Token (1 Hora Aprox)
        token.setExp(System.currentTimeMillis() + 3600000);

        token.setGroups(groups);

        JWSObject jwsObject = new JWSObject(header, new Payload(token.toJSONString()));

        // Firma del token generado, utilizando la llave.
        JWSSigner signer = new RSASSASigner(key);

        try {
            jwsObject.sign(signer);
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return jwsObject.serialize();
    }

	public PrivateKey readPrivateKey() throws IOException {

        InputStream inputStream = CypherService.class.getResourceAsStream("/privateKey.pem");

        PEMParser pemParser = new PEMParser(new InputStreamReader(inputStream));
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());
        Object object = pemParser.readObject();
        KeyPair kp = converter.getKeyPair((PEMKeyPair) object);
        return kp.getPrivate();
    }	
}
