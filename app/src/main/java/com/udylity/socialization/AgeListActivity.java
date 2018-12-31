package com.udylity.socialization;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;


public class AgeListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Future button to change sociologist", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setupList();

        if (findViewById(R.id.age_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void setupList(){
        View recyclerView = findViewById(R.id.age_list);
        assert recyclerView != null;

        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sociologist = getPrefs.getString(getString(R.string.sociologist_data_key), "0");

        int index = Integer.parseInt(sociologist);

        toolbar.setSubtitle(getResources().getStringArray(R.array.sociologistNames)[index]);

        setupRecyclerView((RecyclerView) recyclerView, index);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView, int index) {
        String[] ageRange, ageDetails = null;
        switch (index){
            case 0:
                ageRange = getResources().getStringArray(R.array.ericksonRange);
                ageDetails = getResources().getStringArray(R.array.ericksonDetails);
                break;
            case 1:
                ageRange = getResources().getStringArray(R.array.piagetRange);
                ageDetails = getResources().getStringArray(R.array.piagetDetails);
                break;
            case 2:
                ageRange = getResources().getStringArray(R.array.kohlbergRange);
                ageDetails = getResources().getStringArray(R.array.kohlbergDetails);
                break;
            default:
                ageRange = null;
                ageDetails = null;
        }

        new AgeContent(ageRange, ageDetails);

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(AgeContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>{

        private final List<AgeContent.AgeSection> mValues;

        public SimpleItemRecyclerViewAdapter(List<AgeContent.AgeSection> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.age_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mContentView.setText(mValues.get(position).title);

            holder.mView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        v.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                        Bundle arguments = new Bundle();
                        arguments.putString(AgeDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        AgeDetailFragment fragment = new AgeDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.age_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, AgeDetailActivity.class);
                        intent.putExtra(AgeDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }


        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mContentView;
            public AgeContent.AgeSection mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
