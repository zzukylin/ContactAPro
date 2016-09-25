package com.ruanko.service;

import android.content.Context;

import com.ruanko.dao.ContactDAO;
import com.ruanko.dao.DataBaseImp;
import com.ruanko.model.Contact;

import java.util.List;

/**
 * Created by zzuitachi on 2016/5/27.
 */
public class Service {
    private ContactDAO dao=null;

    public Service(Context context){
        dao=new DataBaseImp(context);
    }
    public boolean save(Contact contact){
        boolean flag=dao.save(contact);
        return flag;
    }
    public List getByName(String queryName){
        List list=dao.getByName(queryName);
        return list;
    }
   public Contact get(int id){
        Contact contact=dao.get(id);
        return contact;
    }

    public void update(Contact contact) {

    }

    public void delete(int id) {
    }

}
