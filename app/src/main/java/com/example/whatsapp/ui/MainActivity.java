package com.example.whatsapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.whatsapp.SignUp;
import com.example.whatsapp.ui.Adapter.WhatsAppViewPagerAdapter;
import com.example.whatsapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private WhatsAppViewPagerAdapter wAdapter;
    private TabLayout tablayout;
    private ViewPager2 viewPager2;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference;
    String PhoneNumber,MyName;

    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();

        databaseReference=FirebaseDatabase.getInstance().getReference();


        // actionBar();

      /*
       databaseReference=FirebaseDatabase.getInstance().getReference();
       databaseReference.child("test1").setValue("Hello");
*/
       /* databaseReference=FirebaseDatabase.getInstance().getReference("TEST");
        databaseReference.child("TEST2").setValue("Hello");*/

        /*
        databaseReference.child("Users").push()
                .child("age").setValue("22");*/

        // if you need to catch the Random id
        /*

       String key = databaseReference.child("Users").push().getKey();
        if (key !=null)
        databaseReference.child("Users")
                .child(key)
                .setValue(new UserModel("https://firebasestorage.googleapis.com/v0/b/whatsapp-4c03f.appspot.com/o/Screenshot_2021-06-02-18-43-57-38.jpg?alt=media&token=fe32838f-dc8c-4f18-b759-e6436d85d8d6","Mohammed"));


        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("tttt","OnDataChanged :"+i++);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        //Receive MyData

       /* if(PhoneNumber != null && MyName != null)
        {
            Bundle bundle = getIntent().getExtras();
            PhoneNumber = bundle.getString("phone");
            MyName = bundle.getString("MyName");
        }*/




    }


    private void initViewPager(){
        tablayout = findViewById(R.id.whatsapp_tab_layout);
        viewPager2 = findViewById(R.id.viewpager_container);
        auth=FirebaseAuth.getInstance();

        wAdapter = new WhatsAppViewPagerAdapter(getSupportFragmentManager(),this.getLifecycle(),tablayout.getTabCount());

        viewPager2.setAdapter(wAdapter);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(viewPagerCallBack);


    }

   private ViewPager2.OnPageChangeCallback viewPagerCallBack = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            tablayout.selectTab(tablayout.getTabAt(position));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager2.unregisterOnPageChangeCallback(viewPagerCallBack);
    }

    @Override
    public void onBackPressed() {

        if(tablayout.getSelectedTabPosition()==0){
            super.onBackPressed();
        }/*else
            viewPager2.setCurrentItem(tablayout.getTabAt(0));*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    /*
   public void actionBar(){
        actionBar=getSupportActionBar();

        // set Title to Action Bar
        actionBar.setTitle("WhatsAPP");

        // to set Icon To action Bar
       // actionBar.setIcon(R.drawable.whatsapplogo2);

       // methods to show Action Bar
        //actionBar.setDisplayUseLogoEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

   }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(this,"Search Clicked",Toast.LENGTH_LONG).show();
                return true;
            case R.id.settings:
                startActivity(new Intent(this, Settings.class));
                return true;
            case R.id.SignOut:
                   auth.signOut();
                   startActivity(new Intent(this, SignUp.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

