package com.example.shakeit;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
public class DetailsActivity extends AppCompatActivity {
    String genWord = "";
    DatabaseReference mRef;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//        BasicNetwork appnetwork = new BasicNetwork(new HurlStack());
//        DiskBasedCache appcache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
//        requestQueue = new RequestQueue(appcache, appnetwork);
//        requestQueue.start();
        Intent intent= getIntent();
        genWord=intent.getStringExtra("word");
        Query wordReference = FirebaseDatabase.getInstance().getReference().child("words").orderByChild("word").equalTo(genWord);
        wordReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Your code to retrieve the details and display them in the DetailActivity goes here
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    if(postSnapshot!=null){
                    String word = postSnapshot.child("word").getValue(String.class);
                    String partOfSpeech = postSnapshot.child("partsofspeech").getValue(String.class);
                    String definition  = postSnapshot.child("definition").getValue(String.class);
                    String pronunciation  = postSnapshot.child("audio").getValue(String.class);
                        TextView wordTextView = findViewById(R.id.wordTextView);
                        TextView partOfSpeechTextView = findViewById(R.id.partOfSpeechTextView);
                        TextView meaningTextView = findViewById(R.id.meaningTextView);
                        TextView pronunciationTextView = findViewById(R.id.pronunciationTextView);
                        Log.i("word",word);
                        Log.i("partOfSpeech",partOfSpeech);
                        Log.i("definition",definition);
                        Log.i("pronunciation",pronunciation);
                        wordTextView.setText(word);
                        partOfSpeechTextView.setText("partOfSpeech:\n" + partOfSpeech);
                        meaningTextView.setText("definition\n" + definition);
                        pronunciationTextView.setText("pronunciation\n" + pronunciation);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {// Handle the error
            }
        });
        Log.i("getWord",genWord);
        mp = new MediaPlayer();
        Log.i("DetailsActivity",genWord);
       // fetchDefinition();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.release();
    }
}
