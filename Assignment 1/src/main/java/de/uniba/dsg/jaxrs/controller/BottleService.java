package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;


import java.util.List;

public class BottleService implements BeverageService{

    public static final BottleService instance = new BottleService();

    private final DB db;

    public BottleService() {
        this.db=new DB();
    }

    public List<Bottle> getAllBottles(){
        return this.db.getAllBottles();
    }

    public Bottle getBottle(final int id){
        return this.db.getBottle(id);
    }

    @Override
    public List getBeverages() {
        return getAllBottles();
    }

    public Bottle addBottle(Bottle bottle){
        if(bottle==null){
            return null;
        }
        return this.db.addBottle(bottle);
    }

    public Bottle updateBottle(final int id,final Bottle updatedBottle){
        return this.db.updateBottle(id,updatedBottle);
    }

    public boolean deleteBottle(final int id){
        return this.db.deleteBottle(id);
    }

    public List<Bottle> getMinAndMaxPriceBeverages(final double min,final double max){
        return this.db.getMinAndMaxPriceBeverages(min,max);
    }
}
