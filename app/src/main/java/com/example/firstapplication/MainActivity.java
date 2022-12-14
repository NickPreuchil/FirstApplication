package com.example.firstapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private UserDao userdb;

    private CurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;

        currentUser = new CurrentUser(context);
        AppDatabase db = App.getInstance().getDatabase();
        userdb = db.userDao();
//-------------------------Prepopulate-----------------------------------
//        User temp = new User();
//        temp.setLogin("temp1");
//        temp.setPassword("111");
//        userdb.insert(temp);
//        temp = new User();
//        temp.setLogin("temp2");
//        temp.setPassword("222");
//        userdb.insert(temp);
//        temp = new User();
//        temp.setLogin("temp3");
//        temp.setPassword("333");
//        userdb.insert(temp);
//------------------------------------------------------------

    }

    public void onMyTestClick(View view) {
//        Toast.makeText(this, "Неверный пароль или e-Mail", Toast.LENGTH_LONG).show();

        Context context = this;
//

//        TextView text = findViewById(R.id.textView);
//        text.setTextColor(Color.parseColor("#FF0000"));
//        text.setOutlineAmbientShadowColor(Color.parseColor("#FF0000"));
//        text.getT

        String userLogin = getEmailPool().getText().toString();
        String userPass = getPasswordPool().getText().toString();

        if (userLogin.isEmpty() || userPass.isEmpty()) {
            Toast.makeText(this, "Empty login or password",
                    Toast.LENGTH_LONG).show();
            return;
        }

        try {
//
            long id = userdb.getUserByLoginData(userLogin, userPass);
            if (id == 0) {
                Toast.makeText(this, "Wrong login or password",
                        Toast.LENGTH_LONG).show();
                return;
            }
            currentUser.setuserid(id);
            Intent myIntent = new Intent(context, MainActivity2.class);
            startActivity(myIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Wrong login or password",
                    Toast.LENGTH_LONG).show();
        }
    }

    private EditText getEmailPool() {
        return findViewById(R.id.editTextTextEmailAddress);
    }

    private EditText getPasswordPool() {
        return findViewById(R.id.editTextTextPassword);
    }

    private String getInsertedData() {
        EditText EmailPool = getEmailPool();
        EditText PassPool = getPasswordPool();
        return EmailPool.getText().toString() + " - " + PassPool.getText().toString();
    }
}
