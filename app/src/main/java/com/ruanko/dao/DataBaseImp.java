package com.ruanko.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ruanko.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzuitachi on 2016/5/28.
 */
public class DataBaseImp implements  ContactDAO{
    private DBHelper helper = null;

    public DataBaseImp(Context context){
        helper=new DBHelper(context);
    }

    @Override
    public boolean save(Contact contact){
        SQLiteDatabase db=helper.getWritableDatabase();
        if(contact!=null){
            String sql="insert into contact(number,name,phone,email,"+
                    "address,gender,relationship,remark) values"+
                    "(?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] params=new Object[]{
                    contact.getNumber(),contact.getName(),contact.getPhone(),contact.getEmail(),
                    contact.getAddress(),contact.getGender(),contact.getRelationship(),contact.getRemark()
            };
            db.execSQL(sql,params);
            db.close();
            return true;
        }else {
            return false;
        }
    }
    @Override
    public List getAll(){
        List list = null;
        SQLiteDatabase db=helper.getReadableDatabase();
        String sql="select * from contact";
        Cursor cursor = db.rawQuery(sql,null);
        list = new ArrayList();
        while (cursor.moveToNext()){
            Contact contact = new Contact();
            contact.setNumber(cursor.getString(1));
            contact.setName(cursor.getString(2));
            contact.setPhone(cursor.getString(3));
            contact.setEmail(cursor.getString(4));
            contact.setAddress(cursor.getString(5));
            contact.setGender(cursor.getString(6));
            contact.setRelationship(cursor.getString(7));
            contact.setRemark(cursor.getString(8));
            list.add(contact);
        }
        cursor.close();
        db.close();
        return list;
    }
    @Override
    public List getByName(String queryName){
        if(queryName == null || queryName.equals("")){
            return getAll();
        }
        List list = null;
        SQLiteDatabase db=helper.getReadableDatabase();
        String sql="select * from contact where name like ? or phone like ?";
        String[] params = new String[]{"%" + queryName + "%","%" + queryName + "%"};
        Cursor cursor = db.rawQuery(sql,params);
        list = new ArrayList();
        while (cursor.moveToNext()){
            Contact contact = new Contact();
            contact.setId(cursor.getInt(0));
            contact.setNumber(cursor.getString(1));
            contact.setName(cursor.getString(2));
            contact.setPhone(cursor.getString(3));
            contact.setEmail(cursor.getString(4));
            contact.setAddress(cursor.getString(5));
            contact.setGender(cursor.getString(6));
            contact.setRelationship(cursor.getString(7));
            contact.setRemark(cursor.getString(8));
            list.add(contact);
        }
        cursor.close();
        db.close();
        return list;
    }
    public Contact get(int id){
        Contact contact = null;
        if(id>0) {
            SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "select * from contact where _id=?";
            String[] params = new String[]{String.valueOf(id)};
            Cursor cursor = db.rawQuery(sql, params);
            if (cursor.moveToNext()) {
                contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setNumber(cursor.getString(1));
                contact.setName(cursor.getString(2));
                contact.setPhone(cursor.getString(3));
                contact.setEmail(cursor.getString(4));
                contact.setAddress(cursor.getString(5));
                contact.setGender(cursor.getString(6));
                contact.setRelationship(cursor.getString(7));
                contact.setRemark(cursor.getString(8));
            }
            cursor.close();
            db.close();
        }
        return contact;
    }
    public void update(Contact contact){
        if(contact!=null){
            SQLiteDatabase db=helper.getWritableDatabase();
            String sql="update contact set number = ?, name = ?, phone = ?, email = ?, address = ?, gender = ?, relationship = ?, remark = ? where _id = ?";
            Object[] params=new Object[]{
                    contact.getNumber(),contact.getName(),contact.getPhone(),contact.getEmail(),
                    contact.getAddress(),contact.getGender(),contact.getRelationship(),contact.getRemark(),
                    contact.getId()
            };
            db.execSQL(sql,params);
            db.close();
        }
    }
    public void delete(int id){
        if(id>0){
            SQLiteDatabase db=helper.getWritableDatabase();
            String sql="delete from contact where _id = ?";
            String[] params = new String[]{String.valueOf(id)};
            db.execSQL(sql,params);
            db.close();
        }
    }
}
