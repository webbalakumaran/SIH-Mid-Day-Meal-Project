package com.example.saravanan.smsreceiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static MainActivity inst;
    ContentResolver contentResolver;
    Cursor smsInboxCursor;
    public String st,st1,st2,sms;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    int count=0;
String str,str1;
String value;
    public static MainActivity instance() {
        return inst;
    }
    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "permission granted!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "permission Denied!", Toast.LENGTH_SHORT).show();
                }
                return;

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smsListView = (ListView) findViewById(R.id.SMSList);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);

//Toast.makeText(MainActivity.this,"Hi",Toast.LENGTH_SHORT).show();
        // Add SMS Read Permision At Runtimes
        // Todo : If Permission Is Not GRANTED
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            // Todo : If Permission Granted Then Show SMS
            //final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                   // count=0;
//                    refreshSmsInbox();
//                    handler.postDelayed(this,5000);
//                }
//            },10000);
refreshSmsInbox();

        } else {
            // Todo : Then Set Permission
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
                    refreshSmsInbox();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            }

        } else {
            //do nothing

        }

//Toast.makeText(MainActivity.this,smsMessagesList.toString(),Toast.LENGTH_SHORT).show();

    }


    public void refreshSmsInbox() {
        contentResolver = getContentResolver();
        smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();

        do {//            String str = "SMS From saravanan: " + smsInboxCursor.getString(indexAddress) +
//                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            str =  smsInboxCursor.getString(indexBody) + "\n";
           str1=smsInboxCursor.getString(indexAddress);
           String c=smsInboxCursor.getString(0);
           Toast.makeText(MainActivity.this,c,Toast.LENGTH_LONG).show();
            if(str.contains("REQUESTMDMCOOK")||str.contains("REQUESTMDMRAW")) {
               try {
                   //arrayAdapter.getPosition(0);
                   arrayAdapter.add(str1+"\n"+str);
                   if(count==0)
                   {
                       StudentCount();
                  // arrayAdapter.remove(smsMessagesList.get(0));
                   }
                    String[] s1=str.split("\\s");
                   String s2=s1[1];
                   Log.e("s2",s2);
                   String s=smsMessagesList.get(0);
                   String[] separate = s.split("\\s");
                   st = separate[2];
                   Log.e("st",st);
                   st1=separate[0];
                   Log.e("st1",st1);
                   st2=separate[1];
                   Log.e("st2",st2);
                   if(st2.contains("REQUESTMDMRAW"))
                   {
                       value="R";
                   }
                   else if(st2.contains("REQUESTMDMCOOK"))
                   {
                       value="C";
                   }
                   //int count=0;
                   //if(count==0) {

                  // }
                   //Toast.makeText(MainActivity.this,"Your number is"+st1,Toast.LENGTH_SHORT).show();

                 //  arrayAdapter.notifyDataSetChanged();
               }
               catch (ArrayIndexOutOfBoundsException e)
               {

               }

            }


        } while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMessage) {
      //  if(smsMessage.contains("Hi")) {
            arrayAdapter.insert(smsMessage, 0);
        //}
        //     Toast.makeText(MainActivity.this,smsMessage,Toast.LENGTH_SHORT).show();
        arrayAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }

            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;
            Toast.makeText(this, smsMessageStr+"sms", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void StudentCount()
    {
        String url="http://fundevelopers.website/sih/booking.php";
        //String url1="http://fundevelopers.website/sih/get_supply.php";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        Toast.makeText(MainActivity.this,ServerResponse,Toast.LENGTH_SHORT).show();
                        String number=st1;
                        sms=ServerResponse;
                        Log.e("sms",sms);
                        String sd[]=sms.split(":");
                        try {
                        String sd1 = sd[5];
                          Log.e("sd", sd1);
                          SupplierCount(sd1,sms);
                          SmsManager smsManager = SmsManager.getDefault();
      smsManager.sendTextMessage(number, null, sms, null, null);

       Toast.makeText(MainActivity.this, "sent", Toast.LENGTH_SHORT).show();
//                       smsMessagesList.remove(0);
//                       arrayAdapter.notifyDataSetChanged();
  }catch (ArrayIndexOutOfBoundsException e)
  {

  }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        //progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                    }

                })

        {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
            //    params.put("mob", st1);
                params.put("mob",st1);
               // params.put("Password", pass1);

                return params;
            }

        };
        requestQueue.add(stringRequest);
        count++;

    }

    private void SupplierCount(final String sd1, final String sms) {
        String url="http://fundevelopers.website/sih/get_supply.php";
        //String url1="http://fundevelopers.website/sih/get_supply.php";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        Toast.makeText(MainActivity.this,ServerResponse,Toast.LENGTH_SHORT).show();
                        Log.e("responsesup",ServerResponse);
                        String[] get=ServerResponse.split(":");
                        String number=get[5];
                        String s5=get[8];
                        Log.e("c",number);
                        Log.e("s5",s5);

                        //                        String number=st1;
//                        sms=ServerResponse;
//                        Log.e("sms",sms);
//                        String sd[]=sms.split(":");
//                        try {
//                            String sd1 = sd[5];
//                            Log.e("sd", sd1);
//                            SupplierCount(sd1,sms);
                            SmsManager smsManager = SmsManager.getDefault();
      smsManager.sendTextMessage(number, null, sms+"\nMenu:"+s5, null, null);
 Toast.makeText(MainActivity.this, "sent to supplier", Toast.LENGTH_SHORT).show();
////                       smsMessagesList.remove(0);
////                       arrayAdapter.notifyDataSetChanged();
//                        }catch (ArrayIndexOutOfBoundsException e)
//                        {
//
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        //progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                    }

                })

        {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                //    params.put("mob", st1);
                params.put("sup",value);
                params.put("st",sd1);
                Log.e("sup",value);
                Log.e("st",sd1);
                // params.put("Password", pass1);

                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

}


