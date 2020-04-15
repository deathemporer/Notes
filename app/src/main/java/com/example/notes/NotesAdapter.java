package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyNotes> myNotes;

    public NotesAdapter(Context c, ArrayList<MyNotes> p){
        context = c;
        myNotes = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.titlenote.setText(myNotes.get(i).getTitlenote());
        myViewHolder.descnote.setText(myNotes.get(i).getDescnote());
        myViewHolder.datenote.setText(myNotes.get(i).getDatenote());

        final String getTitleNotes = myNotes.get(i).getTitlenote();
        final String getDescNotes = myNotes.get(i).getDescnote();
        final String getDateNotes = myNotes.get(i).getDatenote();
        final String getKeyNotes = myNotes.get(i).getKeynote();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(context, EditTaskNotes.class);
                aa.putExtra("titlenote", getTitleNotes);
                aa.putExtra("descnote", getDescNotes);
                aa.putExtra("datenote", getDateNotes);
                aa.putExtra("keynote", getKeyNotes);
                context.startActivity(aa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myNotes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titlenote, descnote, datenote, keynote;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titlenote = (TextView) itemView.findViewById(R.id.titlenote);
            descnote = (TextView) itemView.findViewById(R.id.descnote);
            datenote = (TextView) itemView.findViewById(R.id.datenote);
        }
    }

}
