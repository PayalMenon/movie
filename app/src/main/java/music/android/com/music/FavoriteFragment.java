package music.android.com.music;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by payal.menon on 6/18/16.
 */
public class FavoriteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorite_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        HashMap<String, MapInfo> map = ((MainActivity) getActivity()).getFavMap();
        List<MapInfo> list = new ArrayList<MapInfo>(map.values());
        TextView favNote = (TextView) getActivity().findViewById(R.id.no_fav_text);
        if(list.size() > 0) {
            RecyclerView favList = (RecyclerView) getActivity().findViewById(R.id.favoriteList);
            favList.setHasFixedSize(true);
            favList.setAdapter(new FavListAdapter(getActivity()));
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            favList.setLayoutManager(llm);
            favNote.setVisibility(View.GONE);
        }
        else
        {
            favNote.setVisibility(View.VISIBLE);
        }
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView posterImage;
        public TextView title;
        public TextView director;
        public TextView year;
        public TextView note;
        public CheckBox favButton;

        public FavViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.card_fragment, parent, false));
            posterImage = (ImageView) itemView.findViewById(R.id.movie_poster);
            title = (TextView) itemView.findViewById(R.id.movie_title);
            director = (TextView) itemView.findViewById(R.id.movie_director);
            year = (TextView) itemView.findViewById(R.id.movie_year);
            note = (TextView) itemView.findViewById(R.id.movie_desc);
            favButton = (CheckBox) itemView.findViewById(R.id.favorite_button);
        }
    }

    public static class FavListAdapter extends RecyclerView.Adapter<FavViewHolder>
    {
        private List<MapInfo> favList;
        private Activity adapterActivity;

        public FavListAdapter(Activity activity) {
            HashMap<String, MapInfo> map = ((MainActivity) activity).getFavMap();
            adapterActivity = activity;
            favList = new ArrayList<MapInfo>(map.values());
        }

        @Override
        public FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FavViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(FavViewHolder holder, int position) {
            MapInfo mapInfo = favList.get(position);
            MovieInfo info = mapInfo.info;
            holder.posterImage.setImageBitmap(mapInfo.image);
            holder.title.setText(info.getMovieName());
            holder.director.setText(info.getDirector());
            holder.note.setText(info.getPlotSummary());
            holder.year.setText(info.getYear());
            holder.favButton.setBackground(adapterActivity.getDrawable(R.drawable.fav));
            holder.favButton.setEnabled(false);
        }

        @Override
        public int getItemCount() {
            return favList.size();
        }
    }
}
