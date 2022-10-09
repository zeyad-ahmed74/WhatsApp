package com.example.whatsapp.ui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.whatsapp.R;
import com.example.whatsapp.data.model.MyModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

public class Settings extends AppCompatActivity {

   private TextView Name,Phone;
   private String PhoneNumber,MyName;
   private ImageView Image;
   private Uri img_uri;
   private String MyImage;
   private FrameLayout uploadFrame;
   private Button confirm;
   StorageReference storageReference = FirebaseStorage.getInstance().getReference();
   DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Name =findViewById(R.id.My_Name);
        Phone=findViewById(R.id.My_Phone);
        Image=findViewById(R.id.My_Image);
        uploadFrame=findViewById(R.id.frame_layout_settings);



        // Receive data from SignUp Activity


            ReceiveMyData();




        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    checkUserPermission();
                   // uploadImage(img_uri);
                    uploadMyDataOnFireBase();

            }
        });

    }

    private void uploadMyDataOnFireBase() {

        databaseReference.child("My Info")
                .setValue(new MyModel(MyName,PhoneNumber));


    }

    private void displayImageFromServer(String imgURL) {
        Glide.with(this).load(imgURL).into(Image);
    }

    private void checkUserPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED){
            getImageFromGallery();
        }else if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            showUIDialog("Need This Permission");
        }
        else
            storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private void showUIDialog(String msg) {
       final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
       alertDialogBuilder.setMessage(msg);
       alertDialogBuilder.setTitle(R.string.storage_permission);
       alertDialogBuilder.setCancelable(false);
       alertDialogBuilder.setIcon(R.drawable.ic_baseline_storage_24);
       alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
               storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
           }
       });

       alertDialogBuilder.show();

    }

    private void ReceiveMyData() {
        SharedPreferences sharedPreferences =getSharedPreferences("share",MODE_PRIVATE);
        PhoneNumber=sharedPreferences.getString("phone",null);
        MyName=sharedPreferences.getString("name",null);
        Phone.setText(PhoneNumber);
        Name.setText(MyName);


    }

    private  ActivityResultLauncher<String> ImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent()
            , new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    img_uri = uri;
                    MyImage=img_uri.toString();
                    Image.setImageURI(uri);
                }
            });

    private ActivityResultLauncher<String> storagePermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission() , isGranted ->{
           if(isGranted)
               getImageFromGallery();
           else
               Toast.makeText(this, "Need This Permission", Toast.LENGTH_SHORT).show();


    });


    private void getImageFromGallery() {
        ImageLauncher.launch("image/*");

    }

}