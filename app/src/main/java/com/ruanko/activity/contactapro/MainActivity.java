package com.ruanko.activity.contactapro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ruanko.model.Contact;
import com.ruanko.service.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button add=null;

    private ListView contactList=null;

    private EditText search=null;

    private com.ruanko.service.Service service=null;

    private List contacts=null;

    private Contact contact=null;

    public static final int OPTION_DIALOG=1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service=new Service(this);
        init();
    }

    private void init(){

        add=(Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
        search=(EditText)findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getContent();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getContent();
            }
            @Override
            public void afterTextChanged(Editable s) {
                getContent();
            }
        });
        getListView();
    }

    private void getContent(){
            List myList=new ArrayList();
            String queryName=search.getText().toString();
            contacts=service.getByName(queryName);
            if(contacts!=null){
                for(int i=0;i<contacts.size();i++){
                    Contact contact =(Contact) contacts.get(i);
                    HashMap map=new HashMap();
                    map.put("tv_name",contact.getName());
                    map.put("tv_mobilephone",contact.getPhone());
                    myList.add(map);
                }
            }

        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,myList,R.layout.item_contact,new String[]{
                "tv_name","tv_mobilephone"},new int[]{R.id.tv_name,R.id.tv_mobilephone});
        contactList.setAdapter(adapter);

    }
    private void getListView(){
        contactList=(ListView)findViewById(R.id.lv_contact_list);
        contactList.setCacheColorHint(Color.TRANSPARENT);
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contact = (Contact) contacts.get(position);
                showDialog(OPTION_DIALOG);
            }
        });
    }
    class ImageButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ib_call:
                if(contact.getPhone().equals("")){
                    Toast.makeText(MainActivity.this,"没有手机号码!",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+contact.getPhone()));
                    intent.addCategory("android.intent.category.DEFAULT");
                    startActivity(intent);
                }
                    dismissDialog(OPTION_DIALOG);
                    break;
                case R.id.ib_chat:
                    if(contact.getPhone().equals("")){
                        Toast.makeText(MainActivity.this,"没有手机号码!",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("smsto:"+contact.getPhone()));
                        intent.addCategory("android.intent.category.DEFAULT");
                        startActivity(intent);
                    }
                    dismissDialog(OPTION_DIALOG);
                    break;
                case R.id.ib_view:
                    Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                    intent.putExtra("id",contact.getId());
                    startActivity(intent);
                    dismissDialog(OPTION_DIALOG);
                    break;
            }
        }
    }

   protected Dialog onCreateDialog(int id){
       Dialog dialog;
       switch (id){
           case OPTION_DIALOG:{
               dialog = onCreateOptionsDialog();
               break;
           }
           default:{
               dialog = null;
           }
       }
       return dialog;
   }
    private Dialog onCreateOptionsDialog(){
        final Dialog optionDialog;
        View optionDialogView = null;
        LayoutInflater li = LayoutInflater.from(this);
        optionDialogView = li.inflate(R.layout.option_dialog,null);
        optionDialog = new AlertDialog.Builder(this).setView(optionDialogView).create();
        ImageButton ibCall = (ImageButton) optionDialogView.findViewById(R.id.ib_call);
        ImageButton ibView = (ImageButton) optionDialogView.findViewById(R.id.ib_view);
        ImageButton ibChat = (ImageButton) optionDialogView.findViewById(R.id.ib_chat);

        ibCall.setOnClickListener(new ImageButtonListener());
        ibView.setOnClickListener(new ImageButtonListener());
        ibChat.setOnClickListener(new ImageButtonListener());
        return optionDialog;

    }
}
