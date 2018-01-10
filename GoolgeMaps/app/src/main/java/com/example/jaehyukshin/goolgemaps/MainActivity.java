package com.example.jaehyukshin.goolgemaps;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<CustomListview> list;
    private FirebaseAuth mAuth;
    String userName;

    ListView listView1;
    String[] name = {"","","","","","","","","",""};
    String[] desc = {"","","","","","","","","",""};
    Integer[] imgid={R.drawable.first,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,
            R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable.ten};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        userName = intent.getStringExtra("name");

        getSupportActionBar().setTitle(" ");
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFebe7e3));

        list = new ArrayList<CustomListview>();

        listView1=(ListView)findViewById(R.id.listView);
        CustomListview customListview = new CustomListview(this,name,desc,imgid);
        listView1.setAdapter(customListview);


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Intent newActivity0 = new Intent(MainActivity.this, Restaurant1.class);
                    startActivity(newActivity0);
                }
                if(position==1) {
                    Intent newActivity1 = new Intent(MainActivity.this, Restaurant2.class);
                    startActivity(newActivity1);
                }
                if(position==2) {
                    Intent newActivity2 = new Intent(MainActivity.this, Restaurant3.class);
                    startActivity(newActivity2);
                }
                if(position==3) {
                    Intent newActivity3 = new Intent(MainActivity.this, Restaurant4.class);
                    startActivity(newActivity3);;
                }
                if(position==4) {
                    Intent newActivity4 = new Intent(MainActivity.this, Restaurant5.class);
                    startActivity(newActivity4);
                }
                if(position==5) {
                    Intent newActivity5 = new Intent(MainActivity.this, Restaurant6.class);
                    startActivity(newActivity5);
                }
                if(position==6) {
                    Intent newActivity6 = new Intent(MainActivity.this, Restaurant7.class);
                    startActivity(newActivity6);
                }
                if(position==7) {
                    Intent newActivity7 = new Intent(MainActivity.this, Restaurant8.class);
                    startActivity(newActivity7);
                }
                if(position==8) {
                    Intent newActivity8 = new Intent(MainActivity.this, Restaurant9.class);
                    startActivity(newActivity8);
                }
                if(position==9) {
                    Intent newActivity9 = new Intent(MainActivity.this, Restaurant10.class);
                    startActivity(newActivity9);
                }
            }
        });
        //list.add(customListview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_user:
                //사용자 버튼 눌렀을 때
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(userName);
                builder.setMessage("환영합니다");
//                builder.setPositiveButton("종료",new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int which){
//                        finish();
//                    }
//                }) ;

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.menu_today:
                //맛집추천
                AlertDialog.Builder builderOne = new AlertDialog.Builder(MainActivity.this);
                builderOne.setTitle(userName + "님!");
                builderOne.setMessage("오늘의 추천 맛집입니다!");

                getRandomRestaurant();
                break;
            case R.id.menu_logout:
                //로그아웃버튼 눌렀을 때
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void getRandomRestaurant(){
        Random random = new Random();
        int position = random.nextInt(9);
        if(position==0) {
            Intent newActivity0 = new Intent(MainActivity.this, Restaurant1.class);
            startActivity(newActivity0);
        }
        if(position==1) {
            Intent newActivity1 = new Intent(MainActivity.this, Restaurant2.class);
            startActivity(newActivity1);
        }
        if(position==2) {
            Intent newActivity2 = new Intent(MainActivity.this, Restaurant3.class);
            startActivity(newActivity2);
        }
        if(position==3) {
            Intent newActivity3 = new Intent(MainActivity.this, Restaurant4.class);
            startActivity(newActivity3);;
        }
        if(position==4) {
            Intent newActivity4 = new Intent(MainActivity.this, Restaurant5.class);
            startActivity(newActivity4);
        }
        if(position==5) {
            Intent newActivity5 = new Intent(MainActivity.this, Restaurant6.class);
            startActivity(newActivity5);
        }
        if(position==6) {
            Intent newActivity6 = new Intent(MainActivity.this, Restaurant7.class);
            startActivity(newActivity6);
        }
        if(position==7) {
            Intent newActivity7 = new Intent(MainActivity.this, Restaurant8.class);
            startActivity(newActivity7);
        }
        if(position==8) {
            Intent newActivity8 = new Intent(MainActivity.this, Restaurant9.class);
            startActivity(newActivity8);
        }

    }

}