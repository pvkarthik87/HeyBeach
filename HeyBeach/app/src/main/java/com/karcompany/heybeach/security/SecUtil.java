package com.karcompany.heybeach.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

import static android.R.attr.key;
import static android.R.id.input;

/**
 * Created by pvkarthik on 2017-02-24.
 */

public class SecUtil {

	private static final String AndroidKeyStore = "AndroidKeyStore";
	private static final String AES_MODE = "AES/GCM/NoPadding";

	private static final String KEY_ALIAS = "heybeach";
	private static final String FIXED_IV = "FIXED_IV";

	private static final String RSA_MODE =  "RSA/ECB/PKCS1Padding";
	private static final String ENCRYPTED_KEY = "ENCRYPTED_KEY";

	private static final String AES_MODE_OLD = "AES/ECB/PKCS7Padding";

	public static void init(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			// Generating the key
			/*try {
				KeyStore keyStore = KeyStore.getInstance(AndroidKeyStore);
				keyStore.load(null);

				if (!keyStore.containsAlias(KEY_ALIAS)) {
					KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, AndroidKeyStore);
					keyGenerator.init(
							new KeyGenParameterSpec.Builder(KEY_ALIAS,
									KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
									.setBlockModes(KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
									.setRandomizedEncryptionRequired(false)
									.build());
					keyGenerator.generateKey();
				}
			}  catch (Exception ex) {

			}*/
			try {
				// The key can also be obtained from the Android Keystore any time as follows:
				KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
				keyStore.load(null);
				SecretKey key = (SecretKey) keyStore.getKey(KEY_ALIAS, null);
				if (key != null) {
					KeyGenerator keyGenerator = KeyGenerator.getInstance(
							KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
					keyGenerator.init(
							new KeyGenParameterSpec.Builder(KEY_ALIAS,
									KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
									.setBlockModes(KeyProperties.BLOCK_MODE_GCM)
									.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
									.build());
					key = keyGenerator.generateKey();
				}
			} catch (Exception ex) {

			}


		} else {
			try {
				// Generate the RSA key pairs
				KeyStore keyStore = KeyStore.getInstance(AndroidKeyStore);
				keyStore.load(null);

// Generate the RSA key pairs
				if (!keyStore.containsAlias(KEY_ALIAS)) {
					// Generate a key pair for encryption
					Calendar start = Calendar.getInstance();
					Calendar end = Calendar.getInstance();
					end.add(Calendar.YEAR, 30);

					KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
							.setAlias(KEY_ALIAS)
							.setSubject(new X500Principal("CN=" + KEY_ALIAS))
							.setSerialNumber(BigInteger.TEN)
							.setStartDate(start.getTime())
							.setEndDate(end.getTime())
							.build();
					KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, AndroidKeyStore);
					kpg.initialize(spec);
					kpg.generateKeyPair();

					//Generate and Store the AES Key
					SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
					String enryptedKeyB64 = pref.getString(ENCRYPTED_KEY, null);
					if (enryptedKeyB64 == null) {
						byte[] key = new byte[16];
						SecureRandom secureRandom = new SecureRandom();
						secureRandom.nextBytes(key);

						byte[] encryptedKey = rsaEncrypt(keyStore, key);
						enryptedKeyB64 = Base64.encodeToString(encryptedKey, Base64.DEFAULT);
						SharedPreferences.Editor edit = pref.edit();
						edit.putString(ENCRYPTED_KEY, enryptedKeyB64);
						edit.commit();
					}
				}
			} catch (Exception ex) {

			}
		}
	}

	private static byte[] rsaEncrypt(KeyStore keyStore, byte[] secret) throws Exception{
		KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
		// Encrypt the text
		Cipher inputCipher = Cipher.getInstance(RSA_MODE, "AndroidOpenSSL");
		inputCipher.init(Cipher.ENCRYPT_MODE, privateKeyEntry.getCertificate().getPublicKey());

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, inputCipher);
		cipherOutputStream.write(secret);
		cipherOutputStream.close();

		byte[] vals = outputStream.toByteArray();
		return vals;
	}

	private static byte[] rsaDecrypt(KeyStore keyStore, byte[] encrypted) throws Exception {
		KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(KEY_ALIAS, null);
		Cipher output = Cipher.getInstance(RSA_MODE, "AndroidOpenSSL");
		output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());
		CipherInputStream cipherInputStream = new CipherInputStream(
				new ByteArrayInputStream(encrypted), output);
		ArrayList<Byte> values = new ArrayList<>();
		int nextByte;
		while ((nextByte = cipherInputStream.read()) != -1) {
			values.add((byte)nextByte);
		}

		byte[] bytes = new byte[values.size()];
		for(int i = 0; i < bytes.length; i++) {
			bytes[i] = values.get(i).byteValue();
		}
		return bytes;
	}

	public static String getEncryptedData(Context context, String data) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			// key retrieval
			try {
				// The key can also be obtained from the Android Keystore any time as follows:
				KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
				keyStore.load(null);
				SecretKey key = (SecretKey) keyStore.getKey(KEY_ALIAS, null);

				Cipher cipher = Cipher.getInstance(AES_MODE);
				cipher.init(Cipher.ENCRYPT_MODE, key);

				byte[] encodedBytes = cipher.doFinal(data.getBytes());
				return Base64.encodeToString(encodedBytes, Base64.DEFAULT);
			} catch (Exception ex) {

			}
		} else {
			try {
				Cipher c = Cipher.getInstance(AES_MODE, "BC");
				c.init(Cipher.ENCRYPT_MODE, getSecretKey(context));
				byte[] encodedBytes = c.doFinal(data.getBytes());
				return Base64.encodeToString(encodedBytes, Base64.DEFAULT);
			} catch (Exception ex) {

			}
		}
		return "";
	}

	public static String getDecryptedData(Context context, String encryptedData) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			// key retrieval
			try {
				// The key can also be obtained from the Android Keystore any time as follows:
				KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
				keyStore.load(null);
				SecretKey key = (SecretKey) keyStore.getKey(KEY_ALIAS, null);
				Cipher cipher = Cipher.getInstance(AES_MODE);
				cipher.init(Cipher.DECRYPT_MODE, key);
				byte[] decodedBytes = cipher.doFinal(encryptedData.getBytes());
				return new String(decodedBytes);
			} catch (Exception ex) {

			}
		} else {
			try {
				Cipher c = Cipher.getInstance(AES_MODE, "BC");
				c.init(Cipher.DECRYPT_MODE, getSecretKey(context));
				byte[] decodedBytes = c.doFinal(encryptedData.getBytes());
				return new String(decodedBytes);
			} catch (Exception ex) {

			}
		}
		return "";
	}

	private static Key getSecretKey(Context context) throws Exception {
		KeyStore keyStore = KeyStore.getInstance(AndroidKeyStore);
		keyStore.load(null);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		String enryptedKeyB64 = pref.getString(ENCRYPTED_KEY, null);

		// need to check null, omitted here
		byte[] encryptedKey = Base64.decode(enryptedKeyB64, Base64.DEFAULT);
		byte[] key = rsaDecrypt(keyStore, encryptedKey);
		return new SecretKeySpec(key, "AES");
	}

}
