package com.example.myapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.Interface.ItemClickListener;
import com.example.myapp.Model.Food;
import com.example.myapp.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId="";


    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //fire base
        database =FirebaseDatabase.getInstance();
        foodList =database.getReference("Foods");

        recyclerView=(RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get intern here
        if(getIntent()!=null)
            categoryId=getIntent().getStringExtra("CategoryID");
        if(!categoryId.isEmpty())
        {
            loadListFood(categoryId);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadListFood(String categoryId) {
     adapter=new
                FirebaseRecyclerAdapter<Food,FoodViewHolder>(Food.class, R.layout.food_item, FoodViewHolder.class,
                        foodList.orderByChild("MenuId").equalTo(categoryId))
            //like : select * from foods where menuId
        {

            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                      .into(viewHolder.food_image);
                final Food local=model;
              viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDatail=new Intent(FoodList.this,FoodDetail.class);
                        foodDatail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDatail);


                    }
                });
            }
        };

        Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);

    }
}
