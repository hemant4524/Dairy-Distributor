package com.om.dairy.ramdairy.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.om.dairy.ramdairy.R;
import com.om.dairy.ramdairy.adapter.ItemsAdapter;
import com.om.dairy.ramdairy.communicator.TotalInterface;
import com.om.dairy.ramdairy.model.ItemMaster;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity implements TotalInterface {

    private static final String TAG = ItemListActivity.class.getName();
    private TextView mtvTotalPrice;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ItemsAdapter mItemsAdapter;
    private ArrayList<ItemMaster> mItems;
    private DatabaseReference mDatabase;
    private Context mContext;

    private ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        mContext = this;
        mProgressBar = new ProgressDialog(mContext);
        mProgressBar.setTitle("Milk Data");
        mProgressBar.setMessage("Loading data...");
        mProgressBar.show();

        mtvTotalPrice =  findViewById(R.id.tvTotalPrice);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mItems = new ArrayList<>();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("milkdb/itemdb");
        mDatabase = database;
        mDatabase.addValueEventListener(postListener);

        //

//        mItemsAdapter = new ItemsAdapter();
//        mRecyclerView.setAdapter(mItemsAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    ValueEventListener postListener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {



            for (DataSnapshot adSnapshot: dataSnapshot.getChildren()) {


                mItems.add(adSnapshot.getValue(ItemMaster.class));

                Log.d(TAG, "onDataChange: "+adSnapshot.getValue(ItemMaster.class).getName());


            }

         mItemsAdapter = new ItemsAdapter(mContext,mItems,ItemListActivity.this);
         mRecyclerView.setAdapter(mItemsAdapter);
         mRecyclerView.setItemViewCacheSize(mItems.size());
         mProgressBar.dismiss();

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

        }
    };

    @Override
    public void totalPrice(ArrayList<ItemMaster> itemMasters) {

        float totalPrice = 0;

        for (ItemMaster item:itemMasters) {

            totalPrice = totalPrice + item.getSubTotal();

        }
        mtvTotalPrice.setText(String.valueOf(totalPrice));

    }
}
