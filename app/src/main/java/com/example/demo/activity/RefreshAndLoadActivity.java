package com.example.demo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demo.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefreshAndLoadActivity extends AppCompatActivity implements OnRefreshLoadMoreListener {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    MyListAdapter<Map<String, String>> adapter;
    LinearLayoutManager linearLayoutManager;

    private List<Map<String, String>> initDta;
    private List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_and_load);

        initData();
        initView();
    }

    private void initData(){
        initDta = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Map<String, String> map = new HashMap<>();
            map.put("title", "title" + i);
            map.put("data", "data" + i);
            initDta.add(map);
        }
    }

    private void initView(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshLoadMoreListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        list = initDta;
        adapter = new MyListAdapter(list, android.R.layout.simple_list_item_2);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        list.addAll(initDta);
        adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore(true);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        list = initDta;
        adapter.notifyDataSetChanged();
        refreshLayout.finishRefresh(true);
    }

    static class MyListAdapter<T> extends RecyclerView.Adapter {

        List<T> list;
        int resId;

        MyListAdapter(List<T> list, int resId){
            this.list = list;
            this.resId = resId;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false)){
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Map<String, String> map = (Map<String, String>)list.get(position);
            TextView textView1 = (TextView) holder.itemView.findViewById(android.R.id.text1);
            TextView textView2 = (TextView) holder.itemView.findViewById(android.R.id.text2);
            textView1.setText(map.get("title"));
            textView2.setText(map.get("data"));
            Log.i("adapter", map.get("title"));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        void refresh(List<T> list) {
            this.list.clear();
            this.loadMore(list);
        }

        void loadMore(List<T> list) {
            this.list.addAll(list);
            this.notifyDataSetChanged();
        }
    }
}
