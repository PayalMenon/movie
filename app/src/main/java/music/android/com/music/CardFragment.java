package music.android.com.music;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by payal.menon on 6/17/16.
 */
public class CardFragment extends Fragment{

    private ImageView imageView;
    private TextView title;
    private TextView director;
    private TextView year;
    private TextView note;
    private CheckBox favButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_fragment, container, false);
    }

   @Override
    public void onResume() {
        super.onResume();

        CardView cardView = (CardView)getActivity().findViewById(R.id.card_view);
        imageView = (ImageView)getActivity().findViewById(R.id.movie_poster);
        Bitmap bitmap = ((MainActivity)getActivity()).getPosterImage();
        if (null != bitmap) {
            imageView.setImageBitmap(((MainActivity) getActivity()).getPosterImage());
        }
       else
        {
            imageView.setBackground(getResources().getDrawable(R.drawable.logo));
        }
        title = (TextView) getActivity().findViewById(R.id.movie_title);
        director = (TextView) getActivity().findViewById(R.id.movie_director);
        year = (TextView) getActivity().findViewById(R.id.movie_year);
        note = (TextView) getActivity().findViewById(R.id.movie_desc);
        favButton = (CheckBox) getActivity().findViewById(R.id.favorite_button);
        favButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    HashMap<String, MapInfo> map = ((MainActivity) getActivity()).getFavMap();
                    MovieInfo info = ((MainActivity)getActivity()).getResuts();
                    MapInfo mapInfo = new MapInfo();
                    if(null != ((MainActivity)getActivity()).getPosterImage())
                    {
                        mapInfo.image = ((MainActivity)getActivity()).getPosterImage();
                    }
                    else
                    {
                        mapInfo.image = BitmapFactory.decodeResource(getActivity().getResources(),
                                R.drawable.logo);
                    }
                    mapInfo.info = info;
                    map.put(info.getMovieName(), mapInfo);
                }
            }
        });
       setValuesForMovie();
       ((MainActivity)getActivity()).showBackButton();

       cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ((MainActivity)getActivity()).launchDetailsFragment();
           }
       });
    }

    public void setValuesForMovie()
    {
        MovieInfo info = ((MainActivity)getActivity()).getResuts();
        title.setText(getString(R.string.title, info.getMovieName()));
        director.setText(getString(R.string.director, info.getDirector()));
        year.setText(getString(R.string.year, info.getYear()));
        note.setText(getString(R.string.note, info.getPlotSummary()));
    }
}
