package vishnu.com.testapp.Activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import vishnu.com.testapp.R;


public class Adapter_Picture extends RecyclerView.Adapter<Adapter_Picture.ViewHolder> {
    Context mcontext;
    private ArrayList<PictureModel> mDataset;
    String formurl;



    public Adapter_Picture(Context context, ArrayList<PictureModel> myDataset) {
        mDataset = myDataset;
        mcontext = context;

    }


    @Override
    public Adapter_Picture.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.title.setText(mDataset.get(position).getTitle());

        if (mDataset.get(position).getUrl() == "")
        {
            formurl = "http://farm"+mDataset.get(position).getFarm()+".staticflickr.com/"+mDataset.get(position).getServer()+"/"+mDataset.get(position).getId()+"_"+mDataset.get(position).getSecret()+".jpg";

            Picasso
                    .with(mcontext)
                    .load(formurl)
                    .into(holder.image);

        }

     else {
            formurl = mDataset.get(position).getUrl();
            Picasso
                    .with(mcontext)
                    .load(mDataset.get(position).getUrl())
                    .into(holder.image);

        }


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Pic_Detail_Activity.class);
                intent.putExtra("title",mDataset.get(position).getTitle());
                intent.putExtra("id",mDataset.get(position).getId());
                intent.putExtra("farm",mDataset.get(position).getFarm());
                intent.putExtra("url",formurl);
                mcontext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView title;
        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            view = v;
            title = (TextView) v.findViewById(R.id.title);
            image = (ImageView) v.findViewById(R.id.cardimage);


        }
    }
}
