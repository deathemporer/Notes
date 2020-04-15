package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTaskNotes extends AppCompatActivity {

    EditText titleNotes, descNotes, dateNotes;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_notes);

        titleNotes = findViewById(R.id.titlenote);
        descNotes = findViewById(R.id.descnote);
        dateNotes = findViewById(R.id.datenote);

        btnSaveUpdate = findViewById(R.id.btnSaveUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        titleNotes.setText(getIntent().getStringExtra("titlenote"));
        descNotes.setText(getIntent().getStringExtra("descnote"));
        dateNotes.setText(getIntent().getStringExtra("datenote"));

        final String keykeynote = getIntent().getStringExtra("keynote");

        reference = FirebaseDatabase.getInstance().getReference().child("BoxNotes").child("Note" + keykeynote);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent a = new Intent(EditTaskNotes.this, MainActivity.class);
                            startActivity(a);
                        } else {
                            Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titlenote").setValue(titleNotes.getText().toString());
                        dataSnapshot.getRef().child("descnote").setValue(descNotes.getText().toString());
                        dataSnapshot.getRef().child("datenote").setValue(dateNotes.getText().toString());
                        dataSnapshot.getRef().child("keynote").setValue(keykeynote);

                        Intent a = new Intent(EditTaskNotes.this, MainActivity.class);
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
