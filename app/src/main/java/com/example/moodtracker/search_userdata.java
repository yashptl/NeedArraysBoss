package com.example.moodtracker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class search_userdata extends AppCompatActivity {
    private TextView tv, tv_uid;
    private FirebaseAuth mAuth;
    private String text;
    private User user;
    private User user2;
    private DocumentReference documentReference;
    private DocumentReference documentReference2;
    private ImageView imageView;
    int flag;
    TextView followerstv;
    TextView followingtv;

    /**
     * Inflates the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_userdata);
        mAuth = FirebaseAuth.getInstance();

        final Button testButton = findViewById(R.id.button1);
        testButton.setTag(1);

        final Button unfollow = findViewById(R.id.button2);

        imageView = findViewById(R.id.imgUser);
        tv=findViewById(R.id.emailaddress);
        tv.setText(getIntent().getStringExtra("email"));

        followerstv = findViewById(R.id.followers_no);
        followingtv = findViewById(R.id.following_no);
        tv_uid=findViewById(R.id.tv_name);

        text=getIntent().getStringExtra("email");
        documentReference = FirebaseFirestore.getInstance().collection("users").document("user"+getIntent().getStringExtra("email"));
        documentReference2 = FirebaseFirestore.getInstance().collection("users").document("user"+mAuth.getCurrentUser().getEmail());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user=documentSnapshot.toObject(User.class);
                decodeImage(user.getProfilePic(), imageView);
                followerstv.setText(Integer.toString(user.getNumFollwers()));
                followingtv.setText(Integer.toString(user.getFollowingList().size()));
                tv_uid.setText(user.getUserID());
            }
        });




        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Notification notification = new Notification(1, mAuth.getCurrentUser().getEmail(),getIntent().getStringExtra("email"));

                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                        flag=1;
                        for(int i=0; i<user.getNotification().size();i++)
                        {
                            if(user.getNotification().get(i).getUser1().compareTo(mAuth.getCurrentUser().getEmail())==0 && user.getNotification().get(i).getType()==1)
                            {
                                flag=0;
                                Toast.makeText(getApplicationContext(), "Follow request already pending!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        documentReference2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                user2=documentSnapshot.toObject(User.class);
                                for(int i=0; i<user2.getFollowingList().size();i++)
                                {
                                    if(user2.getFollowingList().get(i).getUser().compareTo(user.getEmail())==0)
                                    {
                                        flag=0;
                                        Toast.makeText(getApplicationContext(), "Already following user!", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                if(flag==1) {
                                    user.getNotification().add(notification);
                                    documentReference.set(user);
                                    Toast.makeText(getApplicationContext(), "Follow request sent!", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                    }
                });
            }
        });

        unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Notification notification = new Notification(4, mAuth.getCurrentUser().getEmail(),getIntent().getStringExtra("email"));

                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                        flag=0;
                        documentReference2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                user2=documentSnapshot.toObject(User.class);
                                for(int i=0; i<user2.getFollowingList().size();i++)
                                {
                                    if(user2.getFollowingList().get(i).getUser().compareTo(user.getEmail())==0)
                                    {
                                        flag=1;
                                        Toast.makeText(getApplicationContext(), "User unfollowed!", Toast.LENGTH_SHORT).show();
                                        user.getNotification().add(notification);
                                        user.setNumFollwers(user.getNumFollwers()-1);
                                        documentReference.set(user);
                                        user2.getFollowingList().remove(i);
                                        documentReference2.set(user2);

                                    }
                                }
                                if(flag==0) {
                                    Toast.makeText(getApplicationContext(), "Not currently following user!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
            }
        });

    }

    /**
     *
     * @param completeImageData - will use the image data to decode it
     * @param imageView - uses the image veiw to view the image
     */
    public void decodeImage(String completeImageData, ImageView imageView) {
        if (completeImageData == null) { return; }

        // Incase you're storing into aws or other places where we have extension stored in the starting.
        String imageDataBytes = completeImageData.substring(completeImageData.indexOf(",")+1);
        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        imageView.setImageBitmap(bitmap);
    }
    

}




