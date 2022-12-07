package basic.springboot.simple.config.jwt;

import basic.springboot.simple.model.AuthUserDetails;
import basic.springboot.simple.util.AESCipherUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;
	
	public static final long JWT_TOKEN_VALIDITY = 10000;

	@Value("${jwt.secret}")
	private String secret;

	public String getUsernameFromToken(String token) {
		// return getClaimFromToken(token, Claims::getSubject);
		try {
			return AESCipherUtil.decrypt(getClaimFromToken(token, Claims::getSubject));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		Key publicKey = null;
		try {
			publicKey = JwtUtil.loadPublicKey(new FileInputStream("src/main/resources/keys/public.pem"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
		// return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(AuthUserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		try {
			// Encrypt subject by custom salt
			String encodedSubject = AESCipherUtil.encrypt(subject);

			Key privateKey = JwtUtil.loadPrivateKey(new FileInputStream("src/main/resources/keys/private.pem"));
			Key publicKey = JwtUtil.loadPublicKey(new FileInputStream("src/main/resources/keys/public.pem"));

			String jwt = Jwts.builder()
					.setClaims(claims)
					.setSubject(encodedSubject)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
					.signWith(SignatureAlgorithm.RS256, privateKey)
					.compact();

			return jwt;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		// return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
			//	.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateRefreshToken(String username){
		 return UUID.randomUUID().toString() + "-" + UUID.nameUUIDFromBytes(username.getBytes()).toString();
	}

	private static Key loadKey(InputStream in, Function<byte[], Key> keyParser) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			String line;
			StringBuilder content = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				if (!(line.contains("BEGIN") || line.contains("END"))) {
					content.append(line).append('\n');
				}
			}
			byte[] encoded = Base64.decodeBase64(content.toString());
			return keyParser.apply(encoded);
		}
	}

	public static Key loadPrivateKey(InputStream in) throws IOException, NoSuchAlgorithmException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		return JwtUtil.loadKey(in, bytes -> {
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
			try {
				RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
				return key;
			} catch (InvalidKeySpecException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public static Key loadPublicKey(InputStream in) throws IOException, NoSuchAlgorithmException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		return JwtUtil.loadKey(in, bytes -> {
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
			try {
				X509EncodedKeySpec spec =
						new X509EncodedKeySpec(bytes);
				return keyFactory.generatePublic(spec);
			} catch (InvalidKeySpecException e) {
				throw new RuntimeException(e);
			}
		});
	}
}
