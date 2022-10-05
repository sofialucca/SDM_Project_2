package com.cs478.sofialucca.project_2;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> nameList; //data: the names displayed
    private ArrayList<Integer> pictureList;
    private RVClickListener RVlistener; //listener defined in main activity
    // private Context context;

    /*
    passing in the data and the listener defined in the main activity
     */
    public MyAdapter(ArrayList<String> theList,ArrayList<Integer> secondList, RVClickListener listener){
        // save list of names to be displayed passed by main activity
        nameList = theList;
        pictureList = secondList;
        // save listener defined and passed by main activity
        this.RVlistener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        // get inflater and inflate XML layout file
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.rv_item, parent, false);

        // create ViewHolder passing the view that it will wrap and the listener on the view
        ViewHolder viewHolder = new ViewHolder(listView, RVlistener); // create ViewHolder

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // populate the item at the input position
        holder.name.setText(nameList.get(position));
        holder.image.setImageResource(pictureList.get(position));
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }




    /*
        This class creates a wrapper object around a view that contains the layout for
         an individual item in the list. It also implements the onClickListener so each ViewHolder in the list is clickable.
        It's onclick method will call the onClick method of the RVClickListener defined in
        the main activity.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        public TextView name;
        public ImageView image;
        private RVClickListener listener;
        private View itemView;

        public ViewHolder(@NonNull View itemView, RVClickListener passedListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            this.itemView = itemView;
            itemView.setOnCreateContextMenuListener(this); //set context menu for each list item (long click)
            this.listener = passedListener;

            /* don't forget to set the listener defined here to the view (list item) that was
                passed in to the constructor. */
            itemView.setOnClickListener(this); //set short click listener
        }

        @Override
        public void onClick(View v) {
            // getAdapterPosition() returns the position of the current ViewHolder in the adapter.
            listener.onClick(v, getAdapterPosition());
            Log.i("ON_CLICK", "in the onclick in view holder");
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            //inflate menu from xml

            MenuInflater inflater = new MenuInflater(v.getContext());
/*            inflater.inflate(R.menu.context_menu, menu );
            menu.getItem(0).setOnMenuItemClickListener(onMenu);
            menu.getItem(1).setOnMenuItemClickListener(onMenu);
            menu.getItem(2).setOnMenuItemClickListener(onMenu);
            menu.getItem(3).setOnMenuItemClickListener(onMenu);

          /*  //create menu in code

            menu.setHeaderTitle("My context menu");

            //add menu items and set the listener for each
            menu.add(0,v.getId(),0,"option 1").setOnMenuItemClickListener(onMenu);
            menu.add(0,v.getId(),0,"option 2").setOnMenuItemClickListener(onMenu);
            menu.add(0,v.getId(),0,"option 3").setOnMenuItemClickListener(onMenu);

*/
        }

        /*
            listener for menu items clicked
         */
        private final MenuItem.OnMenuItemClickListener onMenu = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                Log.i("ON_CLICK", name.getText() + " adapter pos: " + getAdapterPosition());
                return true;
            }
        };



    }
}
