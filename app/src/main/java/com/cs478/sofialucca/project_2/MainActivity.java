package com.cs478.sofialucca.project_2;

import android.content.Intent;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    protected static final String LAYOUT = "Layout" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameView = findViewById(R.id.recycler_view);


        //create list for name, links and drawables
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
            Log.i("ON_CLICK","item clicked to go online ");
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLList.get(position)));
            urlIntent.addCategory(Intent.CATEGORY_BROWSABLE);
            startActivity(urlIntent);

        };
        //Define the listener with a lambda and access the name of the list item from the view for image display activity
        RVClickListener contextMenuOnClick = (view, position) -> {
            Log.i("ON_CLICK","item clicked to be shown ");
            Intent imageIntent = new Intent(MainActivity.this,ImageViewActivity.class);
            imageIntent.putExtra("RESOURCE",(int) mThumbIdsAnimals.get(position));
            startActivity(imageIntent);
        };


        MyAdapter adapter = new MyAdapter(myList,mThumbIdsAnimals, listener,contextMenuOnClick);

        nameView.setHasFixedSize(true);
        nameView.setAdapter(adapter);

        //check to keep state for configuration change
        if(savedInstanceState == null){
            current = "Grid";
            nameView.setLayoutManager(new GridLayoutManager(this,2));//use this line to see as a grid
        }else{
            current = (String) savedInstanceState.get(LAYOUT);
            switch (current){
                case "Grid":
                    nameView.setLayoutManager(new GridLayoutManager(this,2));//use this line to see as a grid
                    break;
                case "List":
                    ((MyAdapter) nameView.getAdapter()).setCurrentOrientation(current);
                    nameView.setLayoutManager(new LinearLayoutManager(this));
                    break;
            }
        }

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
                    Toast.makeText(getApplicationContext(), "List layout already displayed", Toast.LENGTH_LONG).show();

                }else{

                    current = "List";
                    ((MyAdapter) nameView.getAdapter()).setCurrentOrientation(current);
                    nameView.setLayoutManager(new LinearLayoutManager(this));
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LAYOUT,current);
    }
}