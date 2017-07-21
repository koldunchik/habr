package sber.tech.habr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import sber.tech.habr.adapters.HabrItemsAdapter;
import sber.tech.habr.managers.HabrItemsManager;
import sber.tech.habr.models.HabrItem;
import sber.tech.habr.parsers.HabrParser;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvHabrItems;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvHabrItems = (RecyclerView)findViewById(R.id.rvHabrItems);
        mLayoutManager = new LinearLayoutManager(this);
        rvHabrItems.setLayoutManager(mLayoutManager);

        mAdapter = new HabrItemsAdapter(HabrItemsManager.ITEMS);
        rvHabrItems.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRefresh:
                //startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                ((HabrItemsAdapter)mAdapter).clear();
                ((HabrItemsAdapter)mAdapter).updateDataset(HabrItemsManager.ITEMS);
                HabrParser.parseRSS(getResources().getXml(R.xml.stub));
                mAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
