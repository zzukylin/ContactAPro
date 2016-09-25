package com.ruanko.dao;

import android.content.Context;

import com.ruanko.model.Contact;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzuitachi on 2016/5/26.
 */
public abstract class FileImp implements ContactDAO{
    private Context context=null;

    private static  final String FILEPATH="contact.txt";

    public FileImp(Context context){
        this.context=context;
    }

    public boolean save(Contact contact){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new OutputStreamWriter(context.openFileOutput(FILEPATH,Context.MODE_APPEND)));

            bw.write(contact.getNumber()+"##"+contact.getName()+"##"
            +contact.getPhone()+"##"+contact.getEmail()+"##"
            +contact.getAddress()+"##"+contact.getGender()+"##"
            +contact.getRelationship()+"##"+contact.getRemark());
            bw.newLine();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }finally {
            try {
                bw.close();
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
        }
    }
    public List getByName(String queryName){
        List contacts=null;
        if(queryName==null || queryName == ""){
            return getAll();
        }
        BufferedReader br=null;
        try {
            br=new BufferedReader(new InputStreamReader(context.openFileInput(FILEPATH)));
            String data=null;
            contacts=new ArrayList();
            while ((data=br.readLine())!=null){
                String[] infor=data.split("##");
                if(infor[1].contains(queryName)){
                    Contact contact=new Contact();
                    contact.setNumber(infor[0]);
                    contact.setName(infor[1]);
                    contact.setPhone(infor[2]);
                    contact.setEmail(infor[3]);
                    contact.setAddress(infor[4]);
                    contact.setGender(infor[5]);
                    contact.setRelationship(infor[6]);
                    contact.setRemark(infor[7]);
                    contacts.add(contact);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            return contacts;
        }finally {
            try {
                br.close();
            }catch (IOException e){
                e.printStackTrace();
                return contacts;
            }
        }
        return contacts;
    }
    public List getAll(){
        List contacts=null;
        BufferedReader br=null;
        try {
            br=new BufferedReader(new InputStreamReader(context.openFileInput(FILEPATH)));
            String data=null;
            contacts=new ArrayList();
            while ((data=br.readLine())!=null){
                String[] infor=data.split("##");
                    Contact contact=new Contact();
                    contact.setNumber(infor[0]);
                    contact.setName(infor[1]);
                    contact.setPhone(infor[2]);
                    contact.setEmail(infor[3]);
                    contact.setAddress(infor[4]);
                    contact.setGender(infor[5]);
                    contact.setRelationship(infor[6]);
                    contact.setRemark(infor[7]);
                    contacts.add(contact);
            }
        }catch (IOException e){
            e.printStackTrace();
            return contacts;
        }finally {
            try {
                br.close();
            }catch (IOException e){
                e.printStackTrace();
                return contacts;
            }
        }
        return contacts;
    }
}
