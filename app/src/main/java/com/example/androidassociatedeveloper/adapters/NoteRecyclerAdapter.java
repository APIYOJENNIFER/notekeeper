package com.example.androidassociatedeveloper.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidassociatedeveloper.R;
import com.example.androidassociatedeveloper.models.NoteInfo;
import com.example.androidassociatedeveloper.ui.NoteActivity;
import com.example.androidassociatedeveloper.ui.NoteListActivity;

import java.util.List;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<NoteInfo> mNotes;

    public NoteRecyclerAdapter(Context context, List<NoteInfo> mNotes) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.mNotes = mNotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_note_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteInfo noteInfo = mNotes.get(position);
        holder.textCourse.setText(noteInfo.getCourse().getTitle());
        holder.textTitle.setText(noteInfo.getTitle());
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textCourse, textTitle;
        public int mCurrentPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            textCourse = itemView.findViewById(R.id.text_course);
            textTitle = itemView.findViewById(R.id.text_title);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra(NoteActivity.NOTE_POSITION, mCurrentPosition);
                context.startActivity(intent);
            });
        }

    }
}
