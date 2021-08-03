package com.uhi5d.statibud.application

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class PkceUtil {

    @Throws(UnsupportedEncodingException::class)
    fun generateCodeVerifier(): String {
        val rs = getRandomString(128)
        return rs
    }

    @Throws(UnsupportedEncodingException::class, NoSuchAlgorithmException::class)
    fun generateCodeChallenge(codeVerifier: String): String {
        val codeVerifierBytes: ByteArray = codeVerifier.toByteArray(Charsets.US_ASCII)
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")
        md.update(codeVerifierBytes)
        val codeChallengeBytes: ByteArray = md.digest()
        return Base64.encodeToString(
            codeChallengeBytes,
            Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING
        )
    }

    private fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }


}
