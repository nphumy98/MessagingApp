package nz.ac.aut.dms.android.messagingapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.preference.EditTextPreference;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MainActivity extends AppCompatActivity {
    private EditText phoneNumber;
    private EditText text;
    private Button sendButton;
    private final int SEND_SMS_PERMISSION_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumber= (EditText) findViewById(R.id.phoneNumberEditText);
        text= (EditText) findViewById(R.id.messageEditText);
        sendButton= (Button) findViewById(R.id.sendButton);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                !=PackageManager.PERMISSION_GRANTED)
        {
            //if we need to explain to user that they need to allow use sms
            if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                Toast.makeText(this,"Please allow send sms permission!", Toast.LENGTH_SHORT).show();
            }
            //ask user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST);
        }


    }

    public void sendAMessage(View aView)
    {
        Log.d("SmsApp","sendMessage called");
        String number= phoneNumber.getText().toString();
        String message= text.getText().toString();

        Log.d("SmsApp","phone number: "+number);
        Log.d("SmsApp","message: "+message);
        try
        {
            SmsManager smsManager= SmsManager.getDefault();
            smsManager.sendTextMessage(number, null,message, null,null);
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            //the way to print out exception . print stack trace to string
            StringWriter sw= new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString= sw.toString();
            Log.d("SmsApp","exception: "+exceptionAsString);
            Toast.makeText(this, "Fail Message:", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode== SEND_SMS_PERMISSION_REQUEST)
        {
            if (grantResults.length>0&& grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Log.d("SmsApp","Send SMS Permission granted!");
            }
            else
            {
                Log.d("SmsApp","Send SMS Permission Denied!");
            }
        }
    }
}
