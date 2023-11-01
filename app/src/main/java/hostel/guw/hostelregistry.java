package hostel.guw;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import hostel.guw.databinding.ActivityMainBinding;

public class hostelregistry extends AppCompatActivity {
    private EditText tasbeehcountss,cnic,name,contacts,email,dateofregistration,address,gurdian,contactgurdian,nameguardian,nameguardian2,address2,floor,hosname;
    private Button ok,cancel,phoneaddress,edit;
    private FirebaseDatabase db= FirebaseDatabase.getInstance();
    private DatabaseReference root= db.getReference().child("Registration");
    //
    DatePicker picker;
    Button displayDate;


    //image

   Button upload,select;
    ActivityMainBinding binding;
    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;
   ImageView img;
    private String sUsernamae;


    //


    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostelregistry);
        //
        floor=findViewById(R.id.hfloor);
        hosname=findViewById(R.id.hname);
        //
        upload=findViewById(R.id.uploads);
        select=findViewById(R.id.selects);
        edit=findViewById(R.id.edit);
        //
        nameguardian2=findViewById(R.id.ngurdian1);
        address2=findViewById(R.id.tgurdian1);

        //
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(hostelregistry.this,editregistation.class);
                startActivity(i);
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });


        //
        cnic=findViewById(R.id.tcnic);
        name=findViewById(R.id.tname);
        tasbeehcountss=findViewById(R.id.tfather);
        contacts=findViewById(R.id.tcontact);
        email=findViewById(R.id.temail);

        address=findViewById(R.id.taddress);
        gurdian=findViewById(R.id.tgurdian);
        contactgurdian=findViewById(R.id.tcontactgurdian);
        phoneaddress=findViewById(R.id.ok);
        //
        dateofregistration=findViewById(R.id.tdateofregistration);

        picker=(DatePicker)findViewById(R.id.datePicker);
        displayDate=(Button)findViewById(R.id.button1);
        nameguardian=findViewById(R.id.ngurdian);
        dateofregistration.setText("Current Date: "+getCurrentDate());
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateofregistration.setText("Current Date: "+getCurrentDate());

            }
        });
        //
        nameguardian2=findViewById(R.id.ngurdian1);
        address2=findViewById(R.id.tgurdian1);




        //










        //
        phoneaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(hostelregistry.this, hostel.guw.phoneaddress.class);
                startActivity(i);

            }
        });
        //
//        sUsernamae = name.getText().toString();
//        if (sUsernamae.matches("")) {
//            Toast.makeText(this, "You did not enter a date ", Toast.LENGTH_SHORT).show();
//            return;
//        }


        //



        ok=findViewById(R.id.k);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //

                //
                Intent i= new Intent(hostelregistry.this,dashboard.class);
                startActivity(i);
                String tfather = tasbeehcountss.getText().toString();
                String tcnic=cnic.getText().toString();
                String tname=name.getText().toString();
                String tcontact=contacts.getText().toString();
                String temail=email.getText().toString();
                String tdateofregistration=dateofregistration.getText().toString();
                String taddress=address.getText().toString();
                String tgurdian=gurdian.getText().toString();
                String ngurdian=nameguardian.getText().toString();
                String tcontactgurdian=contactgurdian.getText().toString();
                //
                String ngurdian1=nameguardian2.getText().toString();
                String tgurdian2=address2.getText().toString();
                //
                String hname=hosname.getText().toString();
                String hfloor=floor.getText().toString();





                //

                HashMap<String, String> userMap= new HashMap<>();
                userMap.put("tfather", tfather);
                userMap.put("tname", tname);
                userMap.put("tcnic", tcnic);
                userMap.put("tcontact", tcontact);
                userMap.put("temail", temail);
                userMap.put("tdateofregistration", tdateofregistration);
                userMap.put("taddress", taddress);
                userMap.put("tgurdian",tgurdian);
                userMap.put("tcontactgurdian",tcontactgurdian);
                userMap.put("ngurdian",ngurdian);
                //
                userMap.put("ngurdian1",ngurdian1);
                userMap.put("tgurdian2",tgurdian2);
                //
                userMap.put("hname",hname);
                userMap.put("hfloor",hfloor);

                //
                root.push().setValue(userMap);



                Toast.makeText(hostelregistry.this, "Suceesfully added", Toast.LENGTH_SHORT).show();




            }
        });


        String textview = getIntent().getStringExtra("keyname");
        tasbeehcountss.setText(textview);
    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }


    private void uploadImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);


        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        Toast.makeText(hostelregistry.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(hostelregistry.this,"Failed to Upload",Toast.LENGTH_SHORT).show();


                    }
                });
    }
    //



    //

    private String getCurrentDate() {
        StringBuilder builder=new StringBuilder();;
        builder.append((picker.getMonth() + 1)+"/");//month is 0 based
        builder.append(picker.getDayOfMonth()+"/");
        builder.append(picker.getYear());
        return builder.toString();
    }
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();



        }
    }


    //
}