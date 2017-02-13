package com.m2ench.askchitwish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

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

public class SecondActivity extends AppCompatActivity {



    private ArrayList<item> listItems;
    ListView list;
    String name = null;
    private ProgressDialog dialog;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        list = (ListView) findViewById(R.id.view1);
        Intent intent = getIntent();
        //String val=getIntent().getStringExtra("data");
        name = getIntent().getStringExtra("data1");
        String url = "http://kiran0407.tk/kkk.php?catg_id="+name;
/*
        Toast.makeText(SecondActivity.this, "data is" + url, Toast.LENGTH_LONG).show();
*/
        new SecondActivity.JSONTask().execute(url);

        listItems = new ArrayList<>();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public class JSONTask extends AsyncTask<String, String, List<item>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<item> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("success");
                List<item> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
/*
                    if (finalObject.getString("active").contains("Y")) {
*/
                        item listItems = gson.fromJson(finalObject.toString(), item.class);
                        movieModelList.add(listItems);


                }

                return movieModelList;

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(final List<item> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();
            if (movieModelList != null) {
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.list_iten, movieModelList);
                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        item category = movieModelList.get(position);
                        Intent i1 = new Intent(SecondActivity.this, description.class);
                        i1.putExtra("topic_name",category.getTopic_name());
                        i1.putExtra("desc", category.getShort_desc());
                        i1.putExtra("id", Integer.toString(category.getId()));
                        i1.putExtra("methods",category.getMethod1());
                        i1.putExtra("ingredients1",category.getIngredients1());
                        startActivity(i1);
                    }
                });
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(SecondActivity.this, "error please try again...", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public class MovieAdapter extends ArrayAdapter {

        private List<item> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        MovieAdapter(Context context, int resource, List<item> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context = context;
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

            final ViewHolder holder;

            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
                holder = new ViewHolder();
                holder.menuname = (TextView) convertView.findViewById(R.id.Textviewhead);
                holder.idt=(TextView) convertView.findViewById(R.id.textViewDesc);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            item listItem = movieModelList.get(position);
            //Picasso.with(context).load(listItem.getImage()).fit().error(R.drawable.backnom).fit().into(holder.menuimage);
            holder.menuname.setText(listItem.getTopic_name());
            holder.idt.setText(listItem.getShort_desc());
            return convertView;

        }

        class ViewHolder {

            private TextView menuname, idt;


        }
    }
}
