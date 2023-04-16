package com.soc.taskaro;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.soc.taskaro.activities.CreateNotesActivity;
import com.soc.taskaro.activities.CreateTaskActivity;
import com.soc.taskaro.databinding.ActivityMainBinding;
import com.soc.taskaro.fragments.AnalyticsFragment;
import com.soc.taskaro.fragments.HomeFragment;
import com.soc.taskaro.fragments.NotesFragment;
import com.soc.taskaro.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        Menu menu = binding.bottomNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.placeholder);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivity(i);
            }
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    binding.fab.setVisibility(View.VISIBLE);
                    binding.fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(MainActivity.this, CreateTaskActivity.class);
                            startActivity(i);
                        }
                    });
                    menuItem.setVisible(true);
                    break;

                case R.id.analytics:
                    replaceFragment(new AnalyticsFragment());
                    binding.fab.setVisibility(View.GONE);
                    menuItem.setVisible(false);
                    break;

                case R.id.notes:
                    replaceFragment(new NotesFragment());
                    binding.fab.setVisibility(View.VISIBLE);
                    binding.fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(MainActivity.this, CreateNotesActivity.class);
                            startActivity(i);
                        }
                    });
                    menuItem.setVisible(true);
                    break;

                case R.id.other:
                    replaceFragment(new SettingsFragment());
                    binding.fab.setVisibility(View.GONE);
                    menuItem.setVisible(false);
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}