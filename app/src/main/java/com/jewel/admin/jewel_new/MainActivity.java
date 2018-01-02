package com.jewel.admin.jewel_new;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.jewel.admin.jewel_new.Adapter.CredAdapter;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends AppCompatActivity {

    EditText input_aadhar, input_pass;
    Button button_login;
    String aadhar, password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    Firebase fb;
    String url="https://jewel-o-track.firebaseio.com/";

    Calligrapher calligrapher;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        fb=new Firebase(url);
        sharedPreferences=getSharedPreferences("dataset",MODE_PRIVATE);
        edit=sharedPreferences.edit();
        Boolean  log =sharedPreferences.getBoolean("islogged",false);
        if(log){
            startActivity(new Intent(MainActivity.this,Home.class));
            finish();
        }
//        if (firebaseAuth.getCurrentUser() != null) {
//            startActivity(new Intent(MainActivity.this,Home.class));
//            finish();
//        }
        calligrapher = new Calligrapher(this);
        calligrapher.setFont(MainActivity.this,"Ubuntu_R.ttf",true);
        input_aadhar = (EditText)findViewById(R.id.input_aadhar);
        input_pass = (EditText) findViewById(R.id.input_password);
        button_login = (Button) findViewById(R.id.button_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aadhar = input_aadhar.getText().toString().trim();

                password = input_pass.getText().toString().trim();
                if(!aadhar.equals("") || !password.equals("")){
                    if(aadhar.length()==12){
                        new MyTask().execute();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid Aadhaar Number", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onStart()
    {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, Home.class));
        }
    }
    private void validate(){


    }
    public class MyTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {
            fb.child("Customer_Details").child(aadhar).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                         if(dataSnapshot.exists()){
                             System.out.println("Bowwwww");
                             CredAdapter credadap= dataSnapshot.getValue(CredAdapter.class);
                             System.out.println("Vaibhavvvv"+ credadap.getAathar()+ credadap.getCuspass());
                             if(aadhar.equals(credadap.getAathar())){
                                 if(password.equals(credadap.getCuspass())){
                                     edit.putBoolean("islogged",true);
                                     edit.putString("cusname",credadap.getCusname());
                                     edit.putString("aadhaar",credadap.getAathar());
                                     edit.commit();
                                     startActivity(new Intent(MainActivity.this,Home.class));
                                     finish();
                                 }
                                 else{
                                     runOnUiThread(new Runnable() {
                                         @Override
                                         public void run() {
                                             Toast.makeText(MainActivity.this, "INvalid Password...", Toast.LENGTH_SHORT).show();
                                         }
                                     });
                                 }
                             }
                             else{
                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         Toast.makeText(MainActivity.this, "Invalid Username...", Toast.LENGTH_SHORT).show();
                                     }
                                 });
                             }
                         }
                         else{
                             System.out.println("Vaibhavvvv");
                             runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                 }
                             });

                         }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            return null;
        }
    }
}
