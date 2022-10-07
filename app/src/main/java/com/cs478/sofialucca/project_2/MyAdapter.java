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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final ArrayList<String> nameList; //data: the names displayed
    private final ArrayList<Integer> pictureList;
    private String currentOrientation;
    private final RVClickListener RVlistener; //listener defined in main activity
    private final RVClickListener CMlistener;

    /*
    passing in the data and the listener defined in the main activity
     */
    public MyAdapter(ArrayList<String> theList,ArrayList<Integer> secondList, RVClickListener listener, RVClickListener contextMenuOnClick){
        // save list of names to be displayed passed by main activity
        nameList = theList;
        pictureList = secondList;
        currentOrientation = "Grid";
        // save listener defined and passed by main activity
        this.RVlistener = listener;
        this.CMlistener = contextMenuOnClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        // get inflater and inflate XML layout file
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.rv_item, parent, false);


        // create ViewHolder passing the view that it will wrap and the listener on the view
        ViewHolder viewHolder = new ViewHolder(listView, RVlistener, CMlistener); // create ViewHolder

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // populate the item at the input position
        holder.checkOrientation(currentOrientation);
        holder.name.setText(nameList.get(position));
        holder.image.setImageResource(pictureList.get(position));

        Log.i("onCreate my adapter","hello"+holder.name+ currentOrientation);
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public void setCurrentOrientation(String newOrientation){
        this.currentOrientation = newOrientation;
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
        private final RVClickListener listener;
        private final RVClickListener contextMenuOnClick;
        private final View itemView;
        private String currentOrientation;
        private final LinearLayout.LayoutParams paramGridImage;
        private final LinearLayout.LayoutParams paramGridText;
        private final LinearLayout.LayoutParams paramListImage;
        private final LinearLayout.LayoutParams paramListText;

        public ViewHolder(@NonNull View itemView, RVClickListener passedListener, RVClickListener contextMenuOnClick) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
            image = itemView.findViewById(R.id.imageView);
            paramGridImage = new LinearLayout.LayoutParams((LinearLayout.LayoutParams) image.getLayoutParams());
            paramGridText = new LinearLayout.LayoutParams((LinearLayout.LayoutParams) name.getLayoutParams());
            paramListImage = new LinearLayout.LayoutParams(paramGridImage);
            paramListImage.width = 0;
            paramListImage.height = 300;
            paramListImage.weight = 0.5f;
            paramListImage.rightMargin = paramListImage.bottomMargin;
            paramListImage.bottomMargin= paramListImage.topMargin;
            paramListText = new LinearLayout.LayoutParams(paramGridText);
            paramListText.width = 0;
            paramListText.height = 300;
            paramListText.weight = 0.5f;
            paramListText.leftMargin = paramListText.topMargin;
            paramListText.topMargin= paramListImage.topMargin;
            currentOrientation = "Grid";

            this.itemView = itemView;
            itemView.setOnCreateContextMenuListener(this); //set context menu for each list item (long click)

            this.listener = passedListener;
            this.contextMenuOnClick = contextMenuOnClick;
            /* don't forget to set the listener defined here to the view (list item) that was
                passed in to the constructor. */
            itemView.setOnClickListener(this); //set short click listener
        }

        public void checkOrientation(String newOrientation){
            if(!this.currentOrientation.equals(newOrientation)){

                switch(newOrientation){
                    case "List":
                        ((LinearLayout) itemView).setOrientation(LinearLayout.HORIZONTAL);

                        image.setLayoutParams(paramListImage);

                        name.setLayoutParams(paramListText);
                        break;
                    case "Grid":
                        ((LinearLayout) itemView).setOrientation(LinearLayout.VERTICAL);
                        image.setLayoutParams(paramGridImage);
                        name.setLayoutParams(paramGridText);
                        break;
                }
                
                this.currentOrientation = newOrientation;
            }
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
            inflater.inflate(R.menu.context_menu, menu );


            menu.getItem(0).setOnMenuItemClickListener(onMenuWebAccess);
            menu.getItem(1).setOnMenuItemClickListener(onMenuImageDisplay);

        }

        /*
            listener for menu items clicked
         */
        private final MenuItem.OnMenuItemClickListener onMenuWebAccess = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                if(item.getItemId() == R.id.web_page){
                    Log.i("context menu", "web access");
                    listener.onClick(itemView, getAdapterPosition());
                    return true;
                }
                return false;

            }
        };
        private final MenuItem.OnMenuItemClickListener onMenuImageDisplay = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                if(item.getItemId() == R.id.display_image){
                    contextMenuOnClick.onClick(itemView, getAdapterPosition());
                    return true;
                }
                return false;

            }
        };



    }
}
