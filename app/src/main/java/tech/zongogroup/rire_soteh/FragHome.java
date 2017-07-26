package tech.zongogroup.rire_soteh;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragHome extends Fragment {

    RecyclerView pop,recent,downloaded;

    public FragHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_home, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pop = (RecyclerView) view.findViewById(R.id.most_popular_list);
        recent = (RecyclerView) view.findViewById(R.id.most_recent_list);
        downloaded = (RecyclerView) view.findViewById(R.id.most_downloaded_list);

        pop.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        pop.setAdapter(new PopAdapter(getContext()));

        recent.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        recent.setAdapter(new PopAdapter(getContext()));

        downloaded.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        downloaded.setAdapter(new PopAdapter(getContext()));

    }
    public class PopAdapter extends RecyclerView.Adapter<PopHolder>{

        int[] images = {R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5,
                R.drawable.image6,R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,
                R.drawable.image5, R.drawable.image6};

        Context c;

        public PopAdapter(Context c) {
            this.c = c;
        }

        @Override
        public PopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_preview,parent,false);
            return new PopHolder(v);
        }

        @Override
        public void onBindViewHolder(PopHolder holder, int position) {
            holder.imageView.setImageResource(images[position]);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(c,VideoDetail.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return images.length;
        }
    }
    public static class PopHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageView imageView;
        TextView title,duration;
        public PopHolder(View itemView) {
            super(itemView);
            mView = itemView;

            imageView = (ImageView)itemView.findViewById(R.id.frag_home_image);
        }
    }
}
