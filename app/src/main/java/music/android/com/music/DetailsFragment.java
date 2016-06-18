package music.android.com.music;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by payal.menon on 6/18/16.
 */
public class DetailsFragment extends Fragment {

    private ImageView imageView;
    private TextView title;
    private TextView cast;
    private TextView year;
    private TextView note;
    private TextView rating;
    private TextView runtime;
    private TextView genre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        imageView = (ImageView)getActivity().findViewById(R.id.details_image);
        Bitmap bitmap = ((MainActivity)getActivity()).getPosterImage();
        if (null != bitmap) {
            imageView.setImageBitmap(((MainActivity) getActivity()).getPosterImage());
        }
        else
        {
            imageView.setBackground(getResources().getDrawable(R.drawable.logo));
        }
        title = (TextView) getActivity().findViewById(R.id.details_title);
        cast = (TextView) getActivity().findViewById(R.id.details_actors);
        note = (TextView) getActivity().findViewById(R.id.details_note);
        rating = (TextView) getActivity().findViewById(R.id.details_rating);
        year = (TextView) getActivity().findViewById(R.id.details_for_year);
        runtime = (TextView) getActivity().findViewById(R.id.details_runtime);
        genre = (TextView) getActivity().findViewById(R.id.details_genre);

        setValuesForMovie();


    }

    public void setValuesForMovie()
    {
        MovieInfo info = ((MainActivity)getActivity()).getResuts();
        title.setText(info.getMovieName());
        rating.setText(getString(R.string.rating, info.getRating()));
        year.setText(getString(R.string.year, info.getYear()));
        note.setText(getString(R.string.note, info.getPlotSummary()));
        cast.setText(getString(R.string.cast, info.getActors()));
        runtime.setText(getString(R.string.runtime, info.getRunTime()));
        genre.setText(getString(R.string.genre, info.getGenre()));
    }
}
