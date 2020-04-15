package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewTaskAct extends AppCompatActivity {
    EditText titlenote, descnote, datenote;
    Button btnSaveTask, btnCancel;
    DatabaseReference reference;
    Integer noteNum = new Random().nextInt();
    String keynote = Integer.toString(noteNum);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        titlenote = findViewById(R.id.titlenote);
        descnote = findViewById(R.id.descnote);
        datenote = findViewById(R.id.datenote);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btncancel);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("BoxNotes").child("Note" + noteNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titlenote").setValue(titlenote.getText().toString());
                        dataSnapshot.getRef().child("descnote").setValue(descnote.getText().toString());
                        dataSnapshot.getRef().child("datenote").setValue(datenote.getText().toString());
                        dataSnapshot.getRef().child("keynote").setValue(keynote);

                        Intent a = new Intent(NewTaskAct.this, MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
