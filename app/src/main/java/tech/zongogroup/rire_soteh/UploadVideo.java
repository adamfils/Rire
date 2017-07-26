package tech.zongogroup.rire_soteh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class UploadVideo extends AppCompatActivity {
    ImageButton videoPush;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    String videoUrl=null;
    Button upload;
    EditText titles,desc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.showOverflowMenu();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        progressDialog = new ProgressDialog(this);

        titles = (EditText) findViewById(R.id.title_text);
        desc = (EditText) findViewById(R.id.desc_text);

        upload = (Button) findViewById(R.id.upload);
        videoPush = (ImageButton) findViewById(R.id.upload_video);
        videoPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent picIntent = new Intent();
                picIntent.setAction(Intent.ACTION_GET_CONTENT);
                picIntent.setType("video/*");
                startActivityForResult(picIntent,100);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tit,descrip;
                tit = titles.getText().toString();
                descrip = desc.getText().toString();
                if(TextUtils.isEmpty(tit)||TextUtils.isEmpty(descrip) || videoUrl ==null){
                    Toast.makeText(UploadVideo.this, "Fill All Field Before Continuing", Toast.LENGTH_SHORT).show();
                    return;
                }

                SendVideo sendVideo = new SendVideo(videoUrl,tit,descrip);
                FirebaseDatabase.getInstance().getReference().child("Videos")
                        .child(user.getUid()).setValue(sendVideo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                        Toast.makeText(UploadVideo.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UploadVideo.this,HomeActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadVideo.this, "Error Check Network", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        if(requestCode==100 && resultCode==RESULT_OK){
            UploadTask videoTask = storageReference.child(user.getUid()).putFile(uri);
            videoTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount())* 100;
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setMessage("Uploaded "+progress);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    videoUrl = taskSnapshot.getDownloadUrl().toString();
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UploadVideo.this, "Could Not Complete", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
