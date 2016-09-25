package com.ruanko.activity.contactapro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ruanko.model.Contact;
import com.ruanko.service.Service;

/**
 * Created by zzuitachi on 2016/5/28.
 */
public class ModifyActivity extends AppCompatActivity {

    private EditText number=null;
    private EditText name=null;
    private EditText phone=null;
    private EditText email=null;
    private EditText address=null;
    private EditText remark=null;

    private Button save=null;
    private Button back=null;

    private RadioGroup group=null;
    private ImageButton image=null;

    private Spinner spinner=null;
    private static final String[] relationship={"同事","同学","亲戚","朋友"};

    private com.ruanko.service.Service service=null;
    private Contact contact=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);
        service = new Service(this);
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
    private void init() {
        number = (EditText) findViewById(R.id.number);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        remark = (EditText) findViewById(R.id.remark);

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new ButtonListener());

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new ButtonListener());

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, relationship);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        image = (ImageButton) findViewById(R.id.image_button);
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new GroupListner());
    }
    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.save:
                    service.update(getContent());
                    Intent intent=new Intent(ModifyActivity.this,MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.back:
                    finish();
                    break;
            }
        }
    }

    class GroupListner implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group,int checkedId){
            if(group.getCheckedRadioButtonId()==R.id.male){
                image.setImageResource(R.drawable.image1);
            }
            else
            {
                image.setImageResource(R.drawable.image2);
            }
        }
    }
    private Contact getContent(){
        RadioButton rb=(RadioButton) findViewById(group.getCheckedRadioButtonId());
        contact.setNumber(number.getText().toString());
        contact.setName(name.getText().toString());
        contact.setPhone(phone.getText().toString());
        contact.setEmail(email.getText().toString());
        contact.setAddress(address.getText().toString());
        contact.setRemark(remark.getText().toString());
        contact.setGender(rb.getText().toString());
        contact.setRelationship(spinner.getSelectedItem().toString());
        return contact;
    }
    private void load(){
        if(contact.getGender()=="男"){
            image.setImageResource(R.drawable.image1);
            RadioButton rb1=(RadioButton)findViewById(R.id.male);
            rb1.setChecked(true);
        }else{
            image.setImageResource(R.drawable.image2);
            RadioButton rb2=(RadioButton)findViewById(R.id.female);
            rb2.setChecked(true);
        }
        number.setText(contact.getNumber());
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        email.setText(contact.getEmail());
        address.setText(contact.getAddress());
        remark.setText(contact.getRemark());
        for(int i=0;i<relationship.length;i++){
            if(relationship[i].equals(contact.getRelationship())){
                spinner.setSelection(i);
                break;
            }
        }
    }
}

