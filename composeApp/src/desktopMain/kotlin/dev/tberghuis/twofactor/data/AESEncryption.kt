package dev.tberghuis.twofactor.data

import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

// thanks chatgpt
object AESEncryption {
  private const val ALGORITHM = "AES"
  private const val TRANSFORMATION = "AES/GCM/NoPadding"
  private const val KEY_DERIVATION_ALGORITHM = "PBKDF2WithHmacSHA256"
  private const val ITERATION_COUNT = 65536
  private const val KEY_LENGTH = 256
  private const val GCM_TAG_LENGTH = 128
  private const val SALT_LENGTH = 16
  private const val IV_LENGTH = 12

  private fun generateSalt(): ByteArray {
    val salt = ByteArray(SALT_LENGTH)
    SecureRandom().nextBytes(salt)
    return salt
  }

  private fun generateIV(): ByteArray {
    val iv = ByteArray(IV_LENGTH)
    SecureRandom().nextBytes(iv)
    return iv
  }

  private fun deriveKey(password: String, salt: ByteArray): SecretKey {
    val factory = SecretKeyFactory.getInstance(KEY_DERIVATION_ALGORITHM)
    val spec = PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH)
    val secretKey = factory.generateSecret(spec)
    return SecretKeySpec(secretKey.encoded, ALGORITHM)
  }

  fun encrypt(data: String, password: String): String {
    val salt = generateSalt()
    val iv = generateIV()
    val key = deriveKey(password, salt)
    val cipher = Cipher.getInstance(TRANSFORMATION)
    cipher.init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(GCM_TAG_LENGTH, iv))
    val encryptedBytes = cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))
    val combined = salt + iv + encryptedBytes
    return Base64.getEncoder().encodeToString(combined)
  }

  fun decrypt(encryptedData: String, password: String): String {
    val combined = Base64.getDecoder().decode(encryptedData)
    val salt = combined.copyOfRange(0, SALT_LENGTH)
    val iv = combined.copyOfRange(SALT_LENGTH, SALT_LENGTH + IV_LENGTH)
    val encryptedBytes = combined.copyOfRange(SALT_LENGTH + IV_LENGTH, combined.size)
    val key = deriveKey(password, salt)
    val cipher = Cipher.getInstance(TRANSFORMATION)
    cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(GCM_TAG_LENGTH, iv))
    val decryptedBytes = cipher.doFinal(encryptedBytes)
    return String(decryptedBytes, StandardCharsets.UTF_8)
  }
}