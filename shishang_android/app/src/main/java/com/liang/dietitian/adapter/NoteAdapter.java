package com.liang.dietitian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liang.dietitian.R;
import com.liang.dietitian.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> noteList = new ArrayList<>();
    private Context context;

    //静态内部类， 每个条目对应的布局
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView note_headline;
        TextView note_content;
        ImageView note_right_arrow;

        public ViewHolder (View view)
        {
            super(view);
            note_headline = view.findViewById(R.id.note_headline);
            note_content = view.findViewById(R.id.note_content);
            note_right_arrow = view.findViewById(R.id.note_right_arrow);
        }

    }


    public void addNote(Note note){
        noteList.add(note);
    }

    public void clearNote(){
        noteList.clear();
    }

    public Note getItem(int i) {
        if (i > noteList.size()){
            return null;
        }else{
            return noteList.get(i);
        }
    }

    public void deleteNote(int position){
        noteList.remove(position);
    }

    //用于创建ViewHolder实例,并把加载的布局传入到ViewHolder的构造函数去
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item,parent,false);
        context = parent.getContext();
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //是用于对子项的数据进行赋值,会在每个子项被滚动到屏幕内时执行。position得到当前项的实例
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = noteList.get(position);
        if(note != null){
            holder.note_headline.setText(note.getTitle());
            holder.note_content.setText(note.getContent());
        }
    }

    //返回RecyclerView的子项数目
    @Override
    public int getItemCount() {
        return noteList == null ? 0 : noteList.size();
    }



}
