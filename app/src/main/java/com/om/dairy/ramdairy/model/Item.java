package com.om.dairy.ramdairy.model;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class Item {
    public Item(){

    }
  private List<ItemMaster> item_master;
  public void setItem_master(List<ItemMaster> item_master){
   this.item_master=item_master;
  }
  public List<ItemMaster> getItem_master(){
   return item_master;
  }
}