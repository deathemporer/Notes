package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTaskNotes extends AppCompatActivity {

    EditText titleNotes, descNotes, dateNotes;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference reference;
    Calendar calender;
    int currentHour, currentMinute;

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

        dateNotes.setInputType(InputType.TYPE_NULL);

        dateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(dateNotes);

            }
        });


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

    private void showDateTimeDialog(final EditText datenote) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);


                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd'\n'HH:mm a");

                        datenote.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(EditTaskNotes.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(EditTaskNotes.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

}
