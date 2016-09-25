package com.ruanko.dao;

import com.ruanko.model.Contact;

import java.util.List;

/**
 * Created by zzuitachi on 2016/5/26.
 */
public interface ContactDAO {
    public boolean save(Contact contact);
    public List getByName(String queryName);
    public List getAll();
    public Contact get(int id);
}
