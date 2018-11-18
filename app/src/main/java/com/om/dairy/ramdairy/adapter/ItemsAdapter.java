package com.om.dairy.ramdairy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.om.dairy.ramdairy.R;
import com.om.dairy.ramdairy.activity.ItemListActivity;
import com.om.dairy.ramdairy.communicator.TotalInterface;
import com.om.dairy.ramdairy.model.ItemMaster;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder>{

    private static final String TAG = ItemsAdapter.class.getName();
    private ArrayList<ItemMaster> mItemMasters;
    private Context mContext;


    private float tazaSubTotal = 0;

    private TotalInterface mtotalInterface;

    public  ItemsAdapter(Context context, ArrayList<ItemMaster> itemMasters, TotalInterface totalInterface){

        mContext = context;
        mItemMasters = itemMasters;
        mtotalInterface= totalInterface;

    }
    @NonNull
    @Override
    public ItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemsAdapter.MyViewHolder myViewHolder, int i) {

        final ItemMaster itemMaster = mItemMasters.get(i);

        String gold5litter = "<font color=#000000>"+itemMaster.getName()+"</font><br> <font color=#cc0029>"+ String.valueOf(itemMaster.getPrice()) +"</font>";
        myViewHolder.tvItemLable.setText(Html.fromHtml(gold5litter));
//        myViewHolder.tvItemPriceLable.setText(String.valueOf(itemMaster.getPrice()));


        myViewHolder.tvItemPriceLable.setText(String.valueOf(itemMaster.getSubTotal()));

//        myViewHolder.etItemAm.setText(String.valueOf(itemMaster.getUnitAm()));
//        myViewHolder.etItemPm.setText(String.valueOf(itemMaster.getUnitAm()));

        myViewHolder.etItemAm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    int unit = 0;
                    if(s.toString().equals("")){
                        unit = Integer.parseInt("0");
                    }else {
                        unit = Integer.parseInt(s.toString());
                    }
                    itemMaster.setUnitAm(unit);
                    calculatePrice(myViewHolder.tvItemPriceLable,itemMaster);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });


        myViewHolder.etItemPm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    int unit = 0;
                    if(s.toString().equals("")){
                        unit = Integer.parseInt("0");
                    }else {
                        unit = Integer.parseInt(s.toString());
                    }

                    itemMaster.setUnitPm(unit);
                    calculatePrice(myViewHolder.tvItemPriceLable,itemMaster);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mItemMasters.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemLable,tvItemPriceLable;
        EditText etItemAm;
        EditText etItemPm;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tvItemLable = (TextView) view.findViewById(R.id.tvItemLable);
            tvItemPriceLable = (TextView) view.findViewById(R.id.tvItemPriceLable);
            etItemAm = view.findViewById(R.id.etItemAm);
            etItemPm = view.findViewById(R.id.etItemPm);
        }
    }

    private void calculatePrice(TextView mtvPriceLable,ItemMaster itemMaster){
        float subTotal = (itemMaster.getUnitAm() + itemMaster.getUnitPm()) * itemMaster.getPrice();

        itemMaster.setSubTotal(subTotal);
        mtvPriceLable.setText(String.valueOf(subTotal));

        mtotalInterface.totalPrice(mItemMasters);

//        calculateGrandTotal(subTotal);
    }

    private void calculateGrandTotal(float subTotal){

        float grandTotal = subTotal + tazaSubTotal ;

        mtotalInterface.totalPrice(mItemMasters);
        Log.d(TAG, "calculateGrandTotal: "+grandTotal);
//        mtvTotalPrice.setText(""+grandTotal);

    }
}
