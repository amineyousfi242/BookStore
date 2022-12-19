package com.app.notespro;

import android.content.Intent;

import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//-------------------------------
import android.os.AsyncTask;
import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;

//--------------------------------------
public class MainActivity extends AppCompatActivity {

    ImageButton menuBtn;
    FloatingActionButton addBookBtn;
    static EditText e1, e2;
    Button b1, b2;

    private static String msg, NumTable;

    static MyThread thread = new MyThread();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBookBtn = findViewById(R.id.add_book_btn);

        addBookBtn.setOnClickListener((v)->startActivity(new Intent(MainActivity.this, BookDetailsActivity.class)));

        //-------------------------
        menuBtn = findViewById(R.id.menu_btn);
        menuBtn.setOnClickListener((v)->showMenu());
        //-------------------------

        e1 = (EditText) findViewById(R.id.edit1);
        e2 = (EditText) findViewById(R.id.edit2);
        b1 = (Button) findViewById(R.id.button1);

        class myTask extends AsyncTask<Void, Void, Void> {
            @SuppressLint("SetTextI18n")
            @Override
            protected Void doInBackground(Void... params) {
                thread.sendMessage(msg);
                System.out.println(thread.getMessage());
                if (thread.getMessage() != "") {
                    e2.setText(thread.getMessage());
                }
                return null;
            }
        }
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NumTable = e1.getText().toString();
                msg = e1.getText().toString();
                thread.sendMessage(msg);
                e2.setText(thread.getMessage());
                myTask mt = new myTask();
                mt.execute();
                if (thread.getMessage() != "") {
                    e2.setText(thread.getMessage());
                }
            }
        });

    }
    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,menuBtn);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}