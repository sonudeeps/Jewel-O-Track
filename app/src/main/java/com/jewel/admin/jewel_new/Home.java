package com.jewel.admin.jewel_new;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jewel.admin.jewel_new.Adapter.Jewel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    TextView textView2, ifsc1;
    Intent intent;
    Firebase fb;
    String url="https://jewel-o-track.firebaseio.com/";
    SharedPreferences sharedPreferences;
    private List<Jewel> myJewels = new ArrayList<Jewel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ifsc1 = (TextView) findViewById(R.id.ifsc1);
        textView2 = (TextView) findViewById(R.id.textView2);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"oleoScript.ttf");
        ifsc1.setTypeface(typeface);
        textView2.setTypeface(typeface);

        Firebase.setAndroidContext(this);
        fb=new Firebase(url);



        populateCarList();

        registerClickCallback();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_actions, menu);
        return super.onCreateOptionsMenu(menu);}

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case R.id.report:
                intent = new Intent(Home.this, Complaint.class);
                startActivity(intent);
                break;

            case R.id.logout:
                finish();
                startActivity(new Intent(Home.this, MainActivity.class));
        }
        return true;
    }
    private void populateCarList() {

        myJewels.add(new Jewel("Ring", "1111", "Rs.11860"));
        myJewels.add(new Jewel("Chain", "2222", "Rs.28300"));
        myJewels.add(new Jewel("Nose Ring", "3333", "Rs.460"));
        myJewels.add(new Jewel("Ear ring", "4444", "Rs.42550"));
        myJewels.add(new Jewel("Necklace","5555" ,"Rs.48795"));

        new Jewel_Task().execute();

    }
    private void populateListView() {
        ArrayAdapter<Jewel> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.jewelsListView);
        list.setAdapter(adapter);

        registerClickCallback();
    }
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.jewelsListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jewel clickedJewel = myJewels.get(position);
                String item = myJewels.get(position).getIJSC();
               // String img = clickedJewel.getIconID();
                String type = clickedJewel.getJeweltype();
                String price = clickedJewel.getPrice();
                String certified=clickedJewel.getCertified();
                String weight=clickedJewel.getWeight();
                String size=clickedJewel.getSize();
                String color=clickedJewel.getColor();

                final Dialog dialog = new Dialog(Home.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.activity_jewel_details);
                         TextView ifsc=(TextView)dialog.findViewById(R.id.Jewel_Text1);
                         TextView txtype=(TextView)dialog.findViewById(R.id.textView_type);
                         TextView txprice=(TextView)dialog.findViewById(R.id.textView_price);
                         TextView txcertified=(TextView)dialog.findViewById(R.id.textView_cert);
                         TextView txsize=(TextView)dialog.findViewById(R.id.textView_size);
                         TextView txweight=(TextView)dialog.findViewById(R.id.textView_Wt);
                         TextView txcolor=(TextView)dialog.findViewById(R.id.textView_color);

                         ifsc.setText(item);
                         txtype.setText(type);
                         txprice.setText(price);
                         txcertified.setText(certified);
                         txweight.setText(weight);
                         txsize.setText(size);
                         txcolor.setText(color);

            }

        });
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View viewClicked,
//                                    int position, long id) {
//
//                Jewel clickedJewel = myJewels.get(position);
//                Integer item = clickedJewel.getIfsc();
//                String item1 = item.toString();
//                Integer img = clickedJewel.getIconID();
//                String type = clickedJewel.getType();
//                String price = clickedJewel.getPrice();
//
//                switch (item){
//
//                    case 1111:
//                        final Dialog dialog = new Dialog(Home.this);
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        dialog.setContentView(R.layout.activity_jewel_details);
//                        TextView textView = (TextView) dialog.findViewById(R.id.ifsc1);
//                        textView.setText(item1);
//
//                        CircleImageView imageView = (CircleImageView) dialog.findViewById(R.id.image1);
//                        imageView.setImageResource(img);
//
//                        TextView textView_type = (TextView) dialog.findViewById(R.id.textView_type);
//                        textView_type.setText(type);
//
//                        TextView textView_price = (TextView) dialog.findViewById(R.id.textView_price);
//                        textView_price.setText(price);
//
//                        TextView textView_wt = (TextView) dialog.findViewById(R.id.textView_Wt);
//                        textView_wt.setText("4gms");
//
//                        TextView textView_color = (TextView) dialog.findViewById(R.id.textView_color);
//                        textView_color.setText("Gold");
//
//                        TextView textView_cert = (TextView) dialog.findViewById(R.id.textView_cert);
//                        textView_cert.setText("Yes");
//
//                        TextView textView_size = (TextView) dialog.findViewById(R.id.textView_size);
//                        textView_size.setText("4");
//
//                        Button Sell1btn = (Button) dialog.findViewById(R.id.Sell1btn);
//                        Sell1btn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                TextView textView_sell = (TextView) dialog.findViewById(R.id.textView_sell);
//                                textView_sell.setText("Rs.12200");
//                            }
//                        });
//
//                        Button Contact = (Button) dialog.findViewById(R.id.contact);
//                        Contact.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
////                                Intent i = new Intent(Profile_Activity.this, MapsActivity.class);
////                                startActivity(i);
//                            }
//                        });
//                        dialog.dismiss();
//                        dialog.show();
//                        break;
//
//                    case 2222:
//                        final Dialog dialog1 = new Dialog(Home.this);
//                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        dialog1.setContentView(R.layout.activity_jewel_details);
//                        TextView textView1 = (TextView) dialog1.findViewById(R.id.ifsc1);
//                        textView1.setText(item1);
//                        CircleImageView imageView1 = (CircleImageView) dialog1.findViewById(R.id.image1);
//                        imageView1.setImageResource(img);
//
//                        TextView textView_type1 = (TextView) dialog1.findViewById(R.id.textView_type);
//                        textView_type1.setText(type);
//
//                        TextView textView_price1 = (TextView) dialog1.findViewById(R.id.textView_price);
//                        textView_price1.setText(price);
//
//                        TextView textView_wt1 = (TextView) dialog1.findViewById(R.id.textView_Wt);
//                        textView_wt1.setText("9.5gms");
//
//                        TextView textView_color1 = (TextView) dialog1.findViewById(R.id.textView_color);
//                        textView_color1.setText("Gold");
//
//                        TextView textView_cert1 = (TextView) dialog1.findViewById(R.id.textView_cert);
//                        textView_cert1.setText("Yes");
//
//                        TextView textView_size1 = (TextView) dialog1.findViewById(R.id.textView_size);
//                        textView_size1.setText("15''");
//
//                        Button Sell1btn1 = (Button) dialog1.findViewById(R.id.Sell1btn);
//                        Sell1btn1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                TextView textView_sell1 = (TextView) dialog1.findViewById(R.id.textView_sell);
//                                textView_sell1.setText("Rs.28640");
//                            }
//                        });
//
//                        Button Contact1 = (Button) dialog1.findViewById(R.id.contact);
//                        Contact1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
////                                Intent i = new Intent(Profile_Activity.this, SecondMap.class);
////                                startActivity(i);
//                            }
//                        });
//                        dialog1.dismiss();
//                        dialog1.show();
//                        break;
//
//                    case 3333:
//                        final Dialog dialog2 = new Dialog(Home.this);
//                        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        dialog2.setContentView(R.layout.activity_jewel_details);
//                        TextView textView2 = (TextView) dialog2.findViewById(R.id.ifsc1);
//                        textView2.setText(item1);
//                        CircleImageView imageView2 = (CircleImageView) dialog2.findViewById(R.id.image1);
//                        imageView2.setImageResource(img);
//
//                        TextView textView_type2 = (TextView) dialog2.findViewById(R.id.textView_type);
//                        textView_type2.setText(type);
//
//                        TextView textView_price2 = (TextView) dialog2.findViewById(R.id.textView_price);
//                        textView_price2.setText(price);
//
//                        TextView textView_wt2 = (TextView) dialog2.findViewById(R.id.textView_Wt);
//                        textView_wt2.setText("10.5gms");
//
//                        TextView textView_color2 = (TextView) dialog2.findViewById(R.id.textView_color);
//                        textView_color2.setText("Silver");
//
//                        TextView textView_cert2 = (TextView) dialog2.findViewById(R.id.textView_cert);
//                        textView_cert2.setText("Yes");
//
//                        TextView textView_size2 = (TextView) dialog2.findViewById(R.id.textView_size);
//                        textView_size2.setText("14mm");
//
//                        Button Sell1btn2 = (Button) dialog2.findViewById(R.id.Sell1btn);
//                        Sell1btn2.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                TextView textView_sell2 = (TextView) dialog2.findViewById(R.id.textView_sell);
//                                textView_sell2.setText("Rs.720");
//                            }
//                        });
//
//                        Button Contact2 = (Button) dialog2.findViewById(R.id.contact);
//                        Contact2.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
////                                Intent i = new Intent(Profile_Activity.this, ThirdMap.class);
////                                startActivity(i);
//                            }
//                        });
//                        dialog2.dismiss();
//                        dialog2.show();
//                        break;
//
//                    case 4444:
//                        final Dialog dialog3 = new Dialog(Home.this);
//                        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        dialog3.setContentView(R.layout.activity_jewel_details);
//                        TextView textView3 = (TextView) dialog3.findViewById(R.id.ifsc1);
//                        textView3.setText(item1);
//                        CircleImageView imageView3 = (CircleImageView) dialog3.findViewById(R.id.image1);
//                        imageView3.setImageResource(img);
//
//
//                        TextView textView_type3 = (TextView) dialog3.findViewById(R.id.textView_type);
//                        textView_type3.setText(type);
//
//                        TextView textView_price3 = (TextView) dialog3.findViewById(R.id.textView_price);
//                        textView_price3.setText(price);
//
//                        TextView textView_wt3 = (TextView) dialog3.findViewById(R.id.textView_Wt);
//                        textView_wt3.setText("3.65gms");
//
//                        TextView textView_color3 = (TextView) dialog3.findViewById(R.id.textView_color);
//                        textView_color3.setText("Diamond");
//
//                        TextView textView_cert3 = (TextView) dialog3.findViewById(R.id.textView_cert);
//                        textView_cert3.setText("Yes");
//
//                        TextView textView_size3 = (TextView) dialog3.findViewById(R.id.textView_size);
//                        textView_size3.setText("9mm");
//
//                        Button Sell1btn3 = (Button) dialog3.findViewById(R.id.Sell1btn);
//                        Sell1btn3.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                TextView textView_sell3 = (TextView) dialog3.findViewById(R.id.textView_sell);
//                                textView_sell3.setText("Rs.43590");
//                            }
//                        });
//
//                        Button Contact3 = (Button) dialog3.findViewById(R.id.contact);
//                        Contact3.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                         //       Intent i = new Intent(Profile_Activity.this, ForthMap.class);
////                                startActivity(i);
//                            }
//                        });
//                        dialog3.dismiss();
//                        dialog3.show();
//                        break;
//
//                    case 5555:
//                        final Dialog dialog4 = new Dialog(Home.this);
//                        dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        dialog4.setContentView(R.layout.activity_jewel_details);
//                        TextView textView4 = (TextView) dialog4.findViewById(R.id.ifsc1);
//                        textView4.setText(item1);
//                        CircleImageView imageView4 = (CircleImageView) dialog4.findViewById(R.id.image1);
//                        imageView4.setImageResource(img);
//
//                        TextView textView_type4 = (TextView) dialog4.findViewById(R.id.textView_type);
//                        textView_type4.setText(type);
//
//                        TextView textView_price4 = (TextView) dialog4.findViewById(R.id.textView_price);
//                        textView_price4.setText(price);
//
//                        TextView textView_wt4 = (TextView) dialog4.findViewById(R.id.textView_Wt);
//                        textView_wt4.setText("16.25gms");
//
//                        TextView textView_color4 = (TextView) dialog4.findViewById(R.id.textView_color);
//                        textView_color4.setText("Gold");
//
//                        TextView textView_cert4 = (TextView) dialog4.findViewById(R.id.textView_cert);
//                        textView_cert4.setText("Yes");
//
//                        TextView textView_size4 = (TextView) dialog4.findViewById(R.id.textView_size);
//                        textView_size4.setText("21''");
//
//                        Button Sell1btn4 = (Button) dialog4.findViewById(R.id.Sell1btn);
//                        Sell1btn4.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                TextView textView_sell4 = (TextView) dialog4.findViewById(R.id.textView_sell);
//                                textView_sell4.setText("Rs.48845");
//                            }
//                        });
//
//                        Button Contact4 = (Button) dialog4.findViewById(R.id.contact);
//                        Contact4.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
////                                Intent i = new Intent(Home.this, FifthMap.class);
////                                startActivity(i);
//                            }
//                        });
//                        dialog4.dismiss();
//                        dialog4.show();
//                        break;
//                }
//            }
//        });

    }
    private class MyListAdapter extends ArrayAdapter<Jewel> {
        public MyListAdapter() {
            super(Home.this, R.layout.jewel_item, myJewels);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.jewel_item, parent, false);
            }
            Jewel currentJewel = myJewels.get(position);

//            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
//            imageView.setImageResource(currentJewel.getIconID());

            TextView makeText = (TextView) itemView.findViewById(R.id.item_txtMake);
            makeText.setText(currentJewel.getJeweltype());

            TextView yearText = (TextView) itemView.findViewById(R.id.item_txtYear);
            yearText.setText("" + currentJewel.getIJSC());

            TextView condionText = (TextView) itemView.findViewById(R.id.item_txtCondition);
            condionText.setText(currentJewel.getPrice());

            return itemView;
        }
    }
    public class Jewel_Task extends  AsyncTask<Integer, String, Integer>{

        @Override
        protected Integer doInBackground(Integer... integers) {
            sharedPreferences =getSharedPreferences("dataset",MODE_PRIVATE);
            String username=sharedPreferences.getString("aadhaar","");
            System.out.println("bowwwwwww"+username);
            fb.child("Customer_jewels").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot!=null){
                        System.out.println("Bowwwwwwwwwwwwww"+dataSnapshot.getKey());
                        for(DataSnapshot child:dataSnapshot.getChildren()){
                            Jewel adap=child.getValue(Jewel.class);
                            myJewels.add(new Jewel(adap.getJeweltype(),adap.getIJSC(),adap.getPrice()));
                        }

                    }
                    populateListView();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            return null;
        }
    }
}
