package com.cs478.sofialucca.project_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

    ArrayList<String> myList,URLList;
    ArrayList<Integer> mThumbIdsAnimals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView nameView = (RecyclerView) findViewById(R.id.recycler_view);

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

        //Define the listener with a lambda and access the list of names with the position passed in
        //  RVClickListener listener = (view, position)-> Toast.makeText(this, "position: "+position, Toast.LENGTH_LONG).show();

        //Define the listener with a lambda and access the name of the list item from the view
        RVClickListener listener = (view, position) -> {
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLList.get(position)));
            urlIntent.addCategory(Intent.CATEGORY_BROWSABLE);
            startActivity(urlIntent);
            Log.i("ON_CLICK","item clicked to go online");
        };

        MyAdapter adapter = new MyAdapter(myList,mThumbIdsAnimals, listener);
        nameView.setHasFixedSize(true);
        nameView.setAdapter(adapter);
        //nameView.setLayoutManager(new GridLayoutManager(this,2)); //use this line to see as a grid
        nameView.setLayoutManager(new LinearLayoutManager(this)); //use this line to see as a standard vertical list


    }
/*  //you can put the contextItem selected method here or use a listener in the ViewHolder class
    @Override
    public boolean onContextItemSelected(MenuItem item){
        Log.i("ON_CLICK","menu item clicked");

        return true;
    }
    */

}