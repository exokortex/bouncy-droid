package at.acn2020.bouncydroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.bouncycastle.crypto.InvalidCipherTextException;

import java.io.ByteArrayOutputStream;
import java.io.StringBufferInputStream;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    EditText plaintext;
    EditText ciphertext;

    SecretKeySpec key;

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plaintext = findViewById(R.id.plaintext);
        ciphertext = findViewById(R.id.ciphertext);

        byte[] keyBytes = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(keyBytes);
        key = new SecretKeySpec(keyBytes, "AES");
    }

    public void encrypt(View view) throws
            BadPaddingException, IllegalBlockSizeException, ShortBufferException, InvalidCipherTextException {
        String pt = plaintext.getText().toString();
        Log.d("test", pt);

        AES_BC encrypter = new AES_BC();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encrypter.encrypt(new StringBufferInputStream(pt), baos);
        byte[] encr = baos.toByteArray();

        ciphertext.setText(bytesToHex(encr), TextView.BufferType.EDITABLE);
    }

}
