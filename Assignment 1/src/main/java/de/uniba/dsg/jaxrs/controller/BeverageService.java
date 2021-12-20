package de.uniba.dsg.jaxrs.controller;

import java.util.List;

public interface BeverageService<T> {

    List<T> getBeverages();
}
