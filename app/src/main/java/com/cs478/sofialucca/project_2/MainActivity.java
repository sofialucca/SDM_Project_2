package com.cs478.sofialucca.project_2;

import android.content.Intent;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> myList,URLList;
    private ArrayList<Integer> mThumbIdsAnimals;
    private RecyclerView nameView;
    private String current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameView = (RecyclerView) findViewById(R.id.recycler_view);

        List<String> wild_animals = Arrays.asList(getResources().getStringArray(R.array.wild_animals));
        List<String> url_animals = Arrays.asList(getResources().getStringArray(R.array.url_animals));
        mThumbIdsAnimals = new ArrayList<>(

                Arrays.asList(R.drawable.cheeta, R.drawable.crocodile,
                        R.drawable.elephant, R.drawable.gazelle, R.drawable.giraffe,
                        R.drawable.hippo, R.drawable.hyena, R.drawable.lion,
                        R.drawable.rhino, R.drawable.tiger, R.drawable.zebra));
        myList = new ArrayList<>();
        URLList = new ArrayList<>();
        URLList.addAll(url_animals);
        myList.addAll(wild_animals);


        //Define the listener with a lambda and access the name of the list item from the view
        RVClickListener listener = (view, position) -> {
            Log.i("ON_CLICK","item clicked to go online "+ URLList.get(position));
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLList.get(position)));
            urlIntent.addCategory(Intent.CATEGORY_BROWSABLE);
            startActivity(urlIntent);

        };

        RVClickListener contextMenuOnClick = (view, position) -> {
            Intent imageIntent = new Intent(MainActivity.this,ImageViewActivity.class);
            imageIntent.putExtra("RESOURCE",(int) mThumbIdsAnimals.get(position));
            startActivity(imageIntent);
        };

        current = "Grid";
        MyAdapter adapter = new MyAdapter(myList,mThumbIdsAnimals, listener,contextMenuOnClick);

        nameView.setHasFixedSize(true);
        nameView.setAdapter(adapter);
        nameView.setLayoutManager(new GridLayoutManager(this,2)); //use this line to see as a grid

    }


    // Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    // Process clicks on Options Menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.grid_layout:
                if(current.equals("Grid")){
                    Toast.makeText(getApplicationContext(), "Grid layout already displayed", Toast.LENGTH_LONG).show();
                }else{
                    current = "Grid";
                    ((MyAdapter) nameView.getAdapter()).setCurrentOrientation(current);
                    nameView.setLayoutManager(new GridLayoutManager(this,2));
                }
                return true;
            case R.id.list_layout:
                if(current.equals("List")){
                    Toast.makeText(getApplicationContext(), "Linear layout already displayed", Toast.LENGTH_LONG).show();

                }else{

                    current = "List";
                    ((MyAdapter) nameView.getAdapter()).setCurrentOrientation(current);
                    nameView.setLayoutManager(new LinearLayoutManager(this));
                }
            default:
                return false;
        }
    }

}