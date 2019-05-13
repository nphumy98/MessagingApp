package nz.ac.aut.dms.android.messagingapp;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MessageActivity extends AppCompatActivity {
    private EditText phoneNumber;
    private EditText text;

    private String[] permissionList= {Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        phoneNumber= (EditText) findViewById(R.id.phoneNumberEditText);
        text= (EditText) findViewById(R.id.messageEditText);


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
}