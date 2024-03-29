package nz.ac.aut.dms.android.messagingapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
    private Button mainActivityButton;
    private final int SEND_SMS_READ_PHONE_STATE_REQUEST_CODE=1;

    private String[] permissionList= {Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check for permission
        if(!hasPermission(this, permissionList))
        {
            ActivityCompat.requestPermissions(this, permissionList,SEND_SMS_READ_PHONE_STATE_REQUEST_CODE);
        }

    }


    private boolean hasPermission(Context context, String[] permissionList)
    {
        for(String aPermission: permissionList)
        {
            if(ActivityCompat.checkSelfPermission(this, aPermission)
                    !=PackageManager.PERMISSION_GRANTED)
            {
                //if we need to explain to user that they need to allow use sms
                if (shouldShowRequestPermissionRationale(aPermission)) {
                    Toast.makeText(this,"Please allow send sms permission!", Toast.LENGTH_SHORT).show();
                }
                return false;
//                //ask user
//                ActivityCompat.requestPermissions(this, new String[]{aPermission}, SEND_SMS_PERMISSION_REQUEST);
            }

        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode== SEND_SMS_READ_PHONE_STATE_REQUEST_CODE)
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

    public void startMessageActivity(View view) {
        startActivity(new Intent(this, MessageActivity.class));
    }
}
