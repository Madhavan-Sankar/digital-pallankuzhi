package com.example.digital_pallankuzhi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    String ipaddress;
    EditText name;
    Button create,join;
    String roomid;
    ProgressBar progressBar;
    int count=0;
    private static final String ALPHA_NUMERIC_STRING = "0123456789abcdefghijklmnopqrstuvwxyz";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        http://192.168.0.112/digital_pallankuzhi
        //        https://pallankuli.000webhostapp.com
        //        http://digitalpallankuzhi.epizy.com  (epizy)
        ((IpAddress) this.getApplication()).setIp("https://pallankuli.000webhostapp.com");//change the IP address here if needed
        ipaddress = ((IpAddress) this.getApplication()).getIp();
        name = findViewById(R.id.name);
        create = findViewById(R.id.create);
        join = findViewById(R.id.join);
        progressBar=findViewById(R.id.progress);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                final String pname1 = name.getText().toString().trim();
                roomid = randomAlphaNumeric(6);
                if(pname1.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter a name!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "room_id";
                            field[1] = "player1";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = roomid;
                            data[1] = pname1;
                            PutData putData = new PutData(ipaddress+"/write.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Written Successfully!!"))
                                    {
                                        final android.app.AlertDialog.Builder alertDialog2 = new android.app.AlertDialog.Builder(MainActivity.this);
                                        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                                        View v = inflater.inflate(R.layout.roomid_display, null);  // this line
                                        alertDialog2.setView(v);
                                        final android.app.AlertDialog alertDialog = alertDialog2.create();
                                        alertDialog.show();
                                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                                        layoutParams.copyFrom(alertDialog.getWindow().getAttributes());
                                        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                                        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                        final TextView room_id = v.findViewById(R.id.room_id);
                                        final ImageButton share = v.findViewById(R.id.share);
                                        final Button submit = v.findViewById(R.id.submit);
                                        final Button cancel = v.findViewById(R.id.cancel);
                                        room_id.setText(roomid);
                                        share.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent sendIntent = new Intent();
                                                sendIntent.setAction(Intent.ACTION_SEND);
                                                sendIntent.putExtra(Intent.EXTRA_TEXT, "Welcome to Digital Pallankuzhi\nYour room id is : ");
                                                sendIntent.putExtra(Intent.EXTRA_TEXT,roomid);
                                                sendIntent.setType("text/plain");
                                                Intent shareIntent = Intent.createChooser(sendIntent, null);
                                                startActivity(shareIntent);
                                            }
                                        });
                                        submit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("roomid",roomid);
                                                bundle.putString("value","1");
                                                Intent i = new Intent(MainActivity.this, websiteview.class);
                                                i.putExtras(bundle);
                                                startActivity(i);
                                            }
                                        });
                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                alertDialog.dismiss();
                                            }
                                        });
                                        alertDialog.getWindow().setAttributes(layoutParams);
                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, ""+result, LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                final String pname2 = name.getText().toString().trim();
                if(pname2.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter a name!", Toast.LENGTH_SHORT).show();
                }
                else {
                    final android.app.AlertDialog.Builder alertDialog2 = new android.app.AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                    View v = inflater.inflate(R.layout.join_display, null);  // this line
                    alertDialog2.setView(v);
                    final android.app.AlertDialog alertDialog = alertDialog2.create();
                    alertDialog.show();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(alertDialog.getWindow().getAttributes());
                    layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    final EditText room_id = v.findViewById(R.id.room_id);
                    final Button submit = v.findViewById(R.id.submit);
                    final Button cancel = v.findViewById(R.id.cancel);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String roomiddialog = room_id.getText().toString();
                            if(roomiddialog.equals(""))
                            {
                                Toast.makeText(MainActivity.this, "Please enter room id!!!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Starting Write and Read data with URL
                                        //Creating array for parameters
                                        String[] field = new String[1];
                                        field[0]="room_id";
                                        //Creating array for data
                                        String[] data = new String[1];
                                        data[0]=roomiddialog;
                                        PutData putData = new PutData(ipaddress+ "/check.php", "POST", field, data);
                                        if (putData.startPut()) {
                                            if (putData.onComplete()) {
                                                String result = putData.getResult();
                                                if (!result.equals("Checked!")) {
                                                    Toast.makeText(MainActivity.this, result, LENGTH_SHORT).show();
                                                } else {
                                                    progressBar.setVisibility(View.VISIBLE);
                                                    Handler handler1 = new Handler(Looper.getMainLooper());
                                                    handler1.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            //Starting Write and Read data with URL
                                                            //Creating array for parameters
                                                            String[] field1 = new String[2];
                                                            field1[0]="room_id";
                                                            field1[1]="player2";
                                                            //Creating array for data
                                                            String[] data1 = new String[2];
                                                            data1[0]=roomiddialog;
                                                            data1[1]=pname2;
                                                            PutData putData1 = new PutData(ipaddress+"/update.php","POST",field1,data1);
                                                            if(putData1.startPut()){
                                                                if(putData1.onComplete()){
                                                                    progressBar.setVisibility(View.GONE);
                                                                    String result1 = putData1.getResult();
                                                                    if(result1.equals("Updated"))
                                                                    {
                                                                        Bundle bundle1 = new Bundle();
                                                                        bundle1.putString("roomid",roomiddialog);
                                                                        bundle1.putString("value","2");
                                                                        Intent ii = new Intent(MainActivity.this, websiteview.class);
                                                                        ii.putExtras(bundle1);
                                                                        startActivity(ii);
                                                                    }
                                                                    else{
                                                                        Toast.makeText(MainActivity.this, result1, LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                        //End Write and Read data with URL
                                    }
                                });
                            }
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.getWindow().setAttributes(layoutParams);
                }
            }
        });
    }


    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    @Override
    public void onBackPressed() {
        if(count==0)
        {
            Toast.makeText(this, "Press again to exit!!", LENGTH_SHORT).show();
        }
        else
        {
            MainActivity.this.finish();
            System.exit(0);
        }
        count++;
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}