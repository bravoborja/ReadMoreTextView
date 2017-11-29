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

import com.borjabravo.readmoretextview.ReadMoreTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int COUNT = 30;
    private List<Item> mItems = new ArrayList<>();

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
        initMockData();
        listView.setAdapter(new ItemAdapter(mItems));
    }

    private void initMockData() {
        for (int i = 0; i < COUNT; i++) {
            Item item = new Item();
            item.text = i + " : " + getString(R.string.lorem_ipsum);
            item.readMore = true;
            mItems.add(item);
        }
    }

    static class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Item> mItems;

        public ItemAdapter(List<Item> items) {
            mItems = new ArrayList<>(items);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final Context context = holder.itemView.getContext();
            holder.text.setText(mItems.get(position).text);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "you clicked " + position, Toast.LENGTH_SHORT).show();
                }
            });

            holder.text.setToggleWatcher(new ReadMoreTextView.Watcher() {
                @Override
                public void onExpanded() {
                    mItems.get(position).readMore = false;
                }

                @Override
                public void onCollapsed() {
                    mItems.get(position).readMore = true;
                }
            });

            holder.text.toggleCollapsed(mItems.get(position).readMore);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ReadMoreTextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (ReadMoreTextView) itemView.findViewById(R.id.text);
        }
    }

    static class Item {
        public String text;
        public boolean readMore;
    }
}
