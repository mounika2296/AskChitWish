package com.m2ench.askchitwish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ProgressDialog dialog;
    GridView cate_list;
    Context context=this;
    String  share_data;
    ImageButton imageButton;
    EditText inputtext;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        share_data="my app";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         inputtext=(EditText)findViewById(R.id.editText2);

        dialog=new ProgressDialog(this);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        cate_list=(GridView) findViewById(R.id.gridview);
        imageButton=(ImageButton)findViewById(R.id.imageButton2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputtext.equals("")){
                    Toast.makeText(MainActivity.this,"please enter a KEYWORD",Toast.LENGTH_SHORT).show();
                }else{
                    str=inputtext.getText().toString();
                    Intent i1=new Intent(MainActivity.this,Search.class);
                    i1.putExtra("data2",str);
                    startActivity(i1);
                }

            }
        });


        cate_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                startActivity(intent);
            }
        });
        String url="http://gettalentsapp.com/askchitvish/androadmin/catog.php";
        new JSONTask().execute(url);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    public class JSONTask extends AsyncTask<String,String, List<Categorieslist> > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }
        @Override
        protected List<Categorieslist> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("result");
                List<Categorieslist> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    if (finalObject.getString("active").contains("Y")) {
                        Categorieslist categorieslist = gson.fromJson(finalObject.toString(), Categorieslist.class);
                        movieModelList.add(categorieslist);
                    }
                }
                return movieModelList;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;
        }
        @Override
        protected void onPostExecute(final List<Categorieslist> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();
            if(movieModelList != null) {
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.list_categorie, movieModelList);
                cate_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                cate_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Categorieslist category=movieModelList.get(position);
                        Intent i1=new Intent(MainActivity.this,SecondActivity.class);
                        i1.putExtra("data",category.getDisplay_name());
                        i1.putExtra("data1",Integer.toString(category.getId()));
                        startActivity(i1);
                    }
                });
            }
            else {
            }

        }
    }
    public class MovieAdapter extends ArrayAdapter {

        private List<Categorieslist> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdapter(Context context, int resource, List<Categorieslist> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context =context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getViewTypeCount() {

            return 1;
        }
        @Override
        public int getItemViewType(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder  ;
            if(convertView == null){
                convertView = inflater.inflate(resource,null);
                holder = new ViewHolder();
                holder.menuimage = (ImageView)convertView.findViewById(R.id.cato_ima);
                holder.menuname=(TextView) convertView.findViewById(R.id.cat_txt);
                holder.idt=(TextView) convertView.findViewById(R.id.idtt);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Categorieslist categorieslist= movieModelList.get(position);
            Picasso.with(context).load(categorieslist.getImage()).fit().error(R.drawable.backnom).fit().into(holder.menuimage);
            holder.menuname.setText(categorieslist.getDisplay_name());
            holder.idt.setText(Integer.toString(categorieslist.getId()));
            return convertView;
        }
        class ViewHolder{
            private ImageView menuimage;
            private TextView menuname,idt;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("EXIT");
            builder.setMessage("Do you want to exit?");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.cancel();

                }
            });
            AlertDialog alert=builder.create();
            alert.show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent trend=new Intent(MainActivity.this,Trend.class);
            startActivity(trend);


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            Intent recipe=new Intent(MainActivity.this,Recipe.class);
            startActivity(recipe);

        } else if (id == R.id.nav_share) {
            Intent sendIntent=new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra("android.intent.extra.SUBJECT",share_data);
            sendIntent.putExtra(Intent.EXTRA_TEXT,"i am happy with this app.please click the link to download https://play.google.com/store/apps/details? id=com.askchitvish.activity.prem&hl=en");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }else if(id== R.id.nav_facebook) {
            String YourPageURL = "https://www.facebook.com/n/?your page";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));
            startActivity(browserIntent);
        }

        else if (id == R.id.nav_send) {
            Intent sendIntent=new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra("android.intent.extra.SUBJECT",share_data);
            sendIntent.putExtra(Intent.EXTRA_TEXT,"i am happy with this app.please click the link to download https://play.google.com/store/apps/details? id=com.askchitvish.activity.prem&hl=en");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        }
        else if(id== R.id.nav_aboutus){
            Intent about=new Intent(MainActivity.this,aboutus.class);
            startActivity(about);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
