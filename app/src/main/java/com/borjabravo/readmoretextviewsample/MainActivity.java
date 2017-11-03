package com.borjabravo.readmoretextviewsample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text1 = findViewById(R.id.text1);
        text1.setText(getString(R.string.lorem_ipsum));
        TextView text2 = findViewById(R.id.text2);
        text2.setText(getString(R.string.lorem_ipsum2));
        TextView text3 = findViewById(R.id.text3);
        text3.setText(getString(R.string.lorem_ipsum3));
        TextView text4 = findViewById(R.id.text4);
        text4.setText(getString(R.string.one_line_text));
        RecyclerView listView = (RecyclerView) findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(new ItemAdapter());
    }

    static class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final Context context = holder.itemView.getContext();
            holder.text.setText(context.getString(R.string.lorem_ipsum));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "you clicked " + position, Toast.LENGTH_SHORT).show();
                }
            });

            //todo ReadMoreTextView should have a pulbic method to reset textView's collapse status
            // e.g.
            //((ReadMoreTextView) holder.itemView).setCollapsed(position % 2 == 0)
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
