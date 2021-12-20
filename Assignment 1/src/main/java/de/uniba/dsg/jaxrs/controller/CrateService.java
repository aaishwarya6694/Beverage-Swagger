package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import java.util.List;

public class CrateService implements BeverageService {
    public static final CrateService instance = new CrateService();

    private final DB db;

    public CrateService() {
        this.db=new DB();
    }

    public List<Crate> getAllCrates(){
        return this.db.getAllCrates();
    }

    public Crate getCrate(final int id){
        return this.db.getCrate(id);
    }

    @Override
    public List getBeverages() {
        return getAllCrates();
    }

    public Crate addCrate(Crate crate){
        if(crate==null){
            return null;
        }
        return this.db.addCrate(crate);
    }

    public Crate updateCrate(final int id,Crate crate){
        return this.db.updateCrate(id,crate);
    }
    public boolean deleteCrate(final int id){
        return this.db.deleteCrate(id);
    }
}
