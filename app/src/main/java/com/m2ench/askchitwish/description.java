package com.m2ench.askchitwish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.m2ench.askchitwish.R.id.imageButton;
import static com.m2ench.askchitwish.R.id.t3;

public class description extends AppCompatActivity {
    TextView textView,textView1,textView2;
    private ProgressDialog dialog;
    Context context=this;
    float count=15;
    float count1=count;
    ImageButton img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        textView=(TextView)findViewById(R.id.t2);
        Intent intent = getIntent();
        String topic_name=getIntent().getStringExtra("topic_name");
        String ing=getIntent().getStringExtra("ingredients1");
        String method=getIntent().getStringExtra("methods");
        String desc=getIntent().getStringExtra("desc");
        String tit = intent.getStringExtra("data");
        textView1=(TextView)findViewById(t3);
        textView2=(TextView)findViewById(R.id.t4);
        textView1.setText(ing);
        textView2.setText(method);
        setTitle(topic_name);
        textView.setText(tit);
       img=(ImageButton)findViewById(imageButton);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_description, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        if (item.getItemId()==R.id.zoomin) {
            textView1.setTextSize(Float.parseFloat("" + count));
            textView2.setTextSize(Float.parseFloat("" + count1));
            count++;
            count1++;

        }
        else if (item.getItemId()==R.id.zoomout) {
            textView1.setTextSize(Float.parseFloat("" + count));
            textView2.setTextSize(Float.parseFloat("" + count1));
            count--;
            count1--;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
