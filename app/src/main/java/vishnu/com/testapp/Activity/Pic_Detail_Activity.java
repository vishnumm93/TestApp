package vishnu.com.testapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vishnu.com.testapp.R;


public class Pic_Detail_Activity extends AppCompatActivity {

    String title,id,url,farm;

    TextView title1,id1,farm1;

    ImageView image1;


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_detail);

        title = getIntent().getExtras().getString("title");
        id= getIntent().getExtras().getString("id");
        farm = getIntent().getExtras().getString("farm");
        url = getIntent().getExtras().getString("url");

        title1 = (TextView)findViewById(R.id.title);
        id1 = (TextView)findViewById(R.id.id);
        farm1 = (TextView)findViewById(R.id.farm);
        image1 = (ImageView) findViewById(R.id.image);

        Picasso
                .with(this)
                .load(url)
                .placeholder(R.drawable.dummy_image)
                .into(image1);

        title1.setText(title);
        id1.setText(id);
        farm1.setText(farm);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


}
