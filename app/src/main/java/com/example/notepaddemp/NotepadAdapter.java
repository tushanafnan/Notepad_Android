package com.example.notepaddemp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NotepadAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Note> list;

    public NotepadAdapter(Context context, List<Note> list) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.note_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Note note = (Note) getItem(position);
        viewHolder.tvNotepadTime.setText(note.getNotepadTime());
        viewHolder.tvNotepadContent.setText(note.getNotepadContent());

        return convertView;
    }

    static class ViewHolder {
        TextView tvNotepadTime;
        TextView tvNotepadContent;

        public ViewHolder(View view) {
            tvNotepadTime = view.findViewById(R.id.item_time);
            tvNotepadContent = view.findViewById(R.id.item_content);
        }
    }
}