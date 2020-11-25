package edu.pucmm.isc581.parcial2isc581.activities;

import android.content.Intent;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import edu.pucmm.isc581.parcial2isc581.R;
import edu.pucmm.isc581.parcial2isc581.adapters.TabsLayoutAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.inflateMenu(R.menu.menu_appbar);
        toolbar.setTitle("Examen SQLite");
        
        TabsLayoutAdapter tabsLayoutAdapter = new TabsLayoutAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabsLayoutAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        toolbar.setOnMenuItemClickListener(i -> {
            if (tabs.getSelectedTabPosition() == 0){
                Intent intent = new Intent(MainActivity.this, CreateProductActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(MainActivity.this, CreateCategoryActivity.class);
                startActivity(intent);
            }
            return true;
        });
    }
}