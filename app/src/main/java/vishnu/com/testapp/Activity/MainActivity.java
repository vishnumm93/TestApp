package vishnu.com.testapp.Activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vishnu.com.testapp.R;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private String TAG = "TestApp";
    private SearchView simpleSearchView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<PictureModel> picture;
    private ProgressDialog pDialog;

    private static String url1 = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3da7a090077feafdb28e143783024b89&format=json&nojsoncallback=1&text=";
    private static String url2 = "&extras=url_o";
    private static String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleSearchView=(SearchView)findViewById(R.id.simpleSearchView);
        simpleSearchView.setOnQueryTextListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        picture = new ArrayList<>();

    }


    @Override
    public boolean onQueryTextSubmit(String query) {


       // Toast.makeText(getApplicationContext(),"Querry"+query,Toast.LENGTH_SHORT).show();

        url = url1+query+url2;
        new GetPictures().execute();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    private class GetPictures extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e("Json Response:-", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONObject jsonObj1 = jsonObj.getJSONObject("photos");

                    // Getting JSON Array node
                    JSONArray pics = jsonObj1.getJSONArray("photo");

                    // looping through All Contacts
                    for (int i = 0; i < pics.length(); i++) {
                        JSONObject c = null;
                        try {
                            c = pics.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        PictureModel pic = new PictureModel();

                        pic.setId(c.getString("id"));
                        pic.setTitle(c.getString("title"));
                        pic.setSecret(c.getString("secret"));
                        pic.setServer(c.getString("server"));
                        pic.setFarm(c.getString("farm"));

                        if (c.has("url_o"))
                        {
                            pic.setUrl(c.getString("url_o"));

                        }

                        else{
                            pic.setUrl("");
                        }

                        picture.add(pic);


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            adapter = new Adapter_Picture(getApplicationContext(),picture);

            //Adding adapter to recyclerview
            recyclerView.setAdapter(adapter);
        }

    }

}
