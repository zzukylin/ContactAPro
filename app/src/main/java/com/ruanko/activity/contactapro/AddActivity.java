package com.ruanko.activity.contactapro;

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
import android.widget.Toast;

import com.ruanko.model.Contact;
import com.ruanko.service.Service;

public class AddActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        service=new Service(this);
        init();
    }
    private void init(){
        number=(EditText)findViewById(R.id.number);
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        address=(EditText)findViewById(R.id.address);
        remark=(EditText)findViewById(R.id.remark);

        save=(Button)findViewById(R.id.save);
        back=(Button)findViewById(R.id.back);

        save.setOnClickListener(new ButtonListener());
        back.setOnClickListener(new ButtonListener());

        spinner=(Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,relationship);
        adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        image=(ImageButton) findViewById(R.id.image_button);
        group=(RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new GroupListner());
    }
    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.save:
                    boolean flag=service.save(getContent());
                    if(flag){
                        Toast.makeText(AddActivity.this, "用户添加成功!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(AddActivity.this, "用户添加失败!", Toast.LENGTH_LONG).show();
                    }
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
        Contact contact=new Contact();
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
}
