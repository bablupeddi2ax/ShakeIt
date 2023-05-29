package com.example.shakeit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements ShakeDetector.OnShakeListener {
    String currWOrd;
    private SensorManager mSensorManager;
    private ShakeDetector mShakeDetector;
    private TextView mWordTextView;
    LinearLayout mContainer;
    String randGenWord;
    FirebaseAuth mAuth;
    DatabaseReference mref;
    AudioManager manager;
    MediaPlayer m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mWordTextView = findViewById(R.id.word_text);
        mContainer= findViewById(R.id.container);
        //add this line in obnshake otherwise word will not be generated everytime hsake happens
       // fetchData();

        //audio manager
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
      m = MediaPlayer.create(this,R.raw.kidscheering);
        // Set up the shake detector
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(this);
       setUpContainer();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
    @Override
    public void onShake(int count) {
        // Handle shake event
        animateContainer();
        //play audio
        m.start();
       // fetchData();
        mAuth = FirebaseAuth.getInstance();
        mref = FirebaseDatabase.getInstance().getReference();
        mref.child("words").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                int randomNumber = new Random().nextInt((int) count);
                int i = 0;
                for (DataSnapshot postSnapshot :snapshot.getChildren()) {
                    if (i == randomNumber) {
                        // Your code to display the word in the HomeActivity goes here
                        randGenWord  = postSnapshot.child("word").getValue(String.class);
                        mWordTextView.setText(randGenWord);
                        break;
                    }
                    i++;}}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});
      currWOrd = randGenWord   ;
        mWordTextView.setTextSize(36);
        mWordTextView.setTextColor(Color.rgb(255,105,180));
        moveTaskToBack(true);

    }
    private void animateContainer() {
        LinearLayout container = findViewById(R.id.container);
        AnimationSet animationSet = new AnimationSet(true);
        // Scale down animation
        ScaleAnimation scaleDown = new ScaleAnimation(1f, 0.5f, 1f, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleDown.setDuration(2000);
        scaleDown.setFillAfter(true);
        animationSet.addAnimation(scaleDown);
        // Pop out animation
        ScaleAnimation popOut = new ScaleAnimation(0.5f, 1.2f, 0.5f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        popOut.setDuration(200);
        popOut.setStartOffset(500);
        popOut.setInterpolator(new AccelerateInterpolator());
        animationSet.addAnimation(popOut);
        // Scale up animation
        ScaleAnimation scaleUp = new ScaleAnimation(1.2f, 1f, 1.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleUp.setDuration(200);
        scaleUp.setStartOffset(700);
        scaleUp.setInterpolator(new DecelerateInterpolator());
        animationSet.addAnimation(scaleUp);
        container.startAnimation(animationSet);
    }
    private void setUpContainer() {
        mContainer.setOnClickListener(view -> {
            // Start a new activity to display the word details
            Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
            intent.putExtra("word", randGenWord);
            startActivity(intent);
        });}

    //new try for animations
    //pouch animation

}