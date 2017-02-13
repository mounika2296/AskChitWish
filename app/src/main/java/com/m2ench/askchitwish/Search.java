package com.m2ench.askchitwish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class Search extends AppCompatActivity {
    private List<item> listItems;
    String name=null;
    TextView s;
    EditText editText;
    private ProgressDialog dialog;
    Context context=this;
    ListView cate_list;
    ImageButton n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        s=(TextView)findViewById(R.id.text);
        String method=getIntent().getStringExtra("data2");
        setTitle(method);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        editText=(EditText) findViewById(R.id.editText);
        String method1 = getIntent().getStringExtra("data2");

        editText.setText(method1);
        editText.setEnabled(true);
        // editText.setText(method1, EditText.BufferType.UNEDITABLE);
        cate_list=(ListView) findViewById(R.id.t7);
        Intent intent=getIntent();
        name=getIntent().getStringExtra("data2");
        String URL_DATA="http://kusuma0543.tk/search.php?id="+name;
        new Search.JSONTask().execute(URL_DATA);
        n=(ImageButton) findViewById(R.id.imageButton2);
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u=editText.getText().toString();
                String url="http://kusuma0543.tk/search.php?id="+u;
                new JSONTask().execute(url);
            }
        });

        listItems=new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public class JSONTask extends AsyncTask<String,String, List<item> > {
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
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("success");
                List<item> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    if (finalObject.getString("active").contains("Y")) {
                        item item = gson.fromJson(finalObject.toString(), item.class);
                        movieModelList.add(item);
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
        protected void onPostExecute(final List<item> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();
            if(movieModelList != null) {
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.list_iten, movieModelList);
                cate_list.setAdapter(adapter);
                cate_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        item item = movieModelList.get(position);
                        Intent intent = new Intent(Search.this, description.class);
                        intent.putExtra("topic_name",item.getTopic_name());
                        intent.putExtra("short_desc",item.getShort_desc());
                        intent.putExtra("ingredients1",item.getIngredients1());
                        intent.putExtra("methods",item.getMethod());
                        intent.putExtra("id",Integer.toString( item.getId()));
                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_SHORT).show();
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
            final MovieAdapter.ViewHolder holder  ;
            if(convertView == null){
                convertView = inflater.inflate(resource,null);
                holder = new MovieAdapter.ViewHolder();

                holder.menuname=(TextView) convertView.findViewById(R.id.Textviewhead);
                holder.menu=(TextView) convertView.findViewById(R.id.textViewDesc);
                convertView.setTag(holder);
            }
            else {
                holder = (MovieAdapter.ViewHolder) convertView.getTag();
            }
            item item= movieModelList.get(position);

            holder.menuname.setText(item.getTopic_name());
            holder.menu.setText(item.getShort_desc());
            //holder.idt.setText(Integer.toString(item.getId()));
            return convertView;
        }
        class ViewHolder{

            private TextView menuname,idt,menu;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}

