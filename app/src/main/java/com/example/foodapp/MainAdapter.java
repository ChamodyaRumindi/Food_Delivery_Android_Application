package com.example.foodapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    //default constructor
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    //viewHolder used to store data that makes binding view contents easier
    //binding data with model

    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel model) {

        //can do direct operations for particular item in recyclerview using holder
        holder.food_name.setText(model.getFood_name());     //set text from the model
        holder.price.setText(model.getPrice());             //set text from the model

        Glide.with(holder.img.getContext())     //set the image
                .load(model.getFurl())          //set the name as url
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)   //if an error occurs in while loading the image, then displays this default image
                .into(holder.img);



        //update button functions

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())         //popup update dialog box
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1200)
                        .create();
                //dialogPlus.show();

                View view = dialogPlus.getHolderView();


                //access all the text fields in recyclerView
                EditText food_name = view.findViewById(R.id.txtName);
                EditText price = view.findViewById(R.id.txtPrice);
                EditText furl = view.findViewById(R.id.txtImageUrl);

                Button btnUpdate = view.findViewById(R.id.btUpdate);

                //access the data from MainModel class
                food_name.setText(model.getFood_name());
                price.setText(model.getPrice());
                furl.setText(model.getFurl());

                dialogPlus.show();      //show the update popup dialog box


                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();           //pass the data from database to update form

                        //extract the values into text fields
                        map.put("food_name", food_name.getText().toString());
                        map.put("price", price.getText().toString());
                        map.put("furl", furl.getText().toString());


                        //add updated data into the database
                        FirebaseDatabase.getInstance().getReference().child("foods")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.food_name.getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.food_name.getContext(), "Error While Updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });




        //delete function

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.food_name.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted data can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        FirebaseDatabase.getInstance().getReference().child("foods")           //access the database and delete record using key
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(holder.food_name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();   //show alert dialog box
            }
        });

    }





    //retrieve function

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{         //interact with main_item.xml

        CircleImageView img;
        TextView food_name, price;

        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            //register data

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            food_name = (TextView)itemView.findViewById(R.id.nametext);
            price = (TextView)itemView.findViewById(R.id.pricetext);

            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);
        }
    }
}
