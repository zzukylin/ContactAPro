package com.ruanko.activity.contactapro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ruanko.model.Contact;
import com.ruanko.service.Service;

/**
 * Created by zzuitachi on 2016/5/28.
 */
public class DetailActivity extends AppCompatActivity {

    private EditText number=null;
    private EditText name=null;
    private EditText phone=null;
    private EditText email=null;
    private EditText address=null;
    private EditText remark=null;
    private TextView gender=null;
    private TextView relationship=null;

    private Contact contact=null;
    private Service service = null;

    private Button telephone = null;
    private Button sms = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        contact = new Contact();
        init();
        Intent intent = getIntent();
        int id=intent.getIntExtra("id",-1);
        if(id==-1){
            finish();
        }else {
            service = new Service(this);
            contact = service.get(id);
            if (contact!=null){
                load();
            }
        }
    }
    private void init(){
        number=(EditText)findViewById(R.id.number);
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        address=(EditText)findViewById(R.id.address);
        remark=(EditText)findViewById(R.id.remark);

        gender=(TextView)findViewById(R.id.gender);
        relationship=(TextView)findViewById(R.id.relationship);

        telephone = (Button) findViewById(R.id.telephone);
        telephone.setOnClickListener(new ButtonListener());

        sms = (Button) findViewById(R.id.sms);
        sms.setOnClickListener(new ButtonListener());
    }
    private void load(){
        number.setText(contact.getNumber());
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        email.setText(contact.getEmail());
        address.setText(contact.getAddress());
        remark.setText(contact.getRemark());
        gender.setText(contact.getGender());
        relationship.setText(contact.getRelationship());
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.modify: {
                Intent intent = new Intent(DetailActivity.this,ModifyActivity.class);
                intent.putExtra("id",contact.getId());
                startActivity(intent);
                break;
            }
            case R.id.delete: {
                dialog();
                break;
            }
        }
        return true;
    }
    private void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setMessage("确认删除吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                service.delete(contact.getId());
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    class ButtonListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.telephone: {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + contact.getPhone()));
                    startActivity(intent);
                    break;
                }
                case R.id.sms: {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:" + contact.getPhone()));
                    startActivity(intent);
                    break;
                }
            }
        }
    }
}
