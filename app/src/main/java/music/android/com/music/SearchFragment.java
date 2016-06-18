package music.android.com.music;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by payal.menon on 6/17/16.
 */
public class SearchFragment extends Fragment {

    private static int UPDATE_WITH_IO_ERROR = 120;
    private static int UPDATE_IMAGE = 122;
    private EditText searchBox;
    private Handler handler;
    private MovieInfo info;
    private Bitmap posterBitmap;
    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        searchBox = (EditText)getActivity().findViewById(R.id.search_text);
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchBox.clearFocus();
                    InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
                    searchForMovie();
                    progress = new ProgressDialog(getActivity());
                    progress.setMessage("Searching for your movie...");
                    progress.setIndeterminate(false);
                    progress.setCancelable(false);
                    progress.show();
                    return true;
                }
                return false;
            }
        });
        ((MainActivity)getActivity()).hideBackButton();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == UPDATE_IMAGE)
                {
                    progress.dismiss();
                    ((MainActivity)getActivity()).updateResultsView(info);
                    ((MainActivity)getActivity()).updatePosterImage(posterBitmap);
                }
                else if (msg.what == UPDATE_WITH_IO_ERROR)
                {
                    progress.dismiss();
                    ((MainActivity)getActivity()).updateIOError();
                }
                super.handleMessage(msg);
            }
        };
    }

    private void searchForMovie()
    {
        MovieThread thread = new MovieThread();
        thread.start();
    }

    private class MovieThread extends Thread
    {
        @Override
        public void run() {
            getMovie();
            getPosterImage(info);
        }
    }
    private void getMovie()
    {
        String movieName = searchBox.getText().toString();
        String movie = movieName.replace(" ", "%20");
        Uri uri = Uri.parse(Common_Util.MOVIE_SEARCH_URL);
        StringBuilder builder = new StringBuilder();
        builder.append(Common_Util.MOVIE_SEARCH_URL);
        builder.append("?t=");
        builder.append(movie);

        StringBuilder responseBuilder = new StringBuilder();
        HttpClient httpclient = new DefaultHttpClient();
        InputStream input = null;

        JSONObject jsonObject = null;
        try {
            HttpGet httpGet = new HttpGet(builder.toString());
            HttpResponse response = httpclient.execute(httpGet);
            input = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while((line = reader.readLine()) != null){
                responseBuilder.append(line);
            }
            System.out.println(responseBuilder.toString());
            jsonObject = new JSONObject(responseBuilder.toString());

            boolean bolResponse =checkResponseForError(jsonObject);
            if(false == bolResponse)
            {
                Message msg = handler.obtainMessage();
                msg.what = UPDATE_WITH_IO_ERROR;
                handler.sendMessage(msg);
            }
            else {
                info = parseJsonObject(jsonObject);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getPosterImage(MovieInfo info)
    {
        if (null != info)
        {
            if(null != info.getPosterURL() && (false == info.getPosterURL().equals("N/A")))
            {
                try {
                    System.out.println("poster url " + info.getPosterURL());
                    posterBitmap = BitmapFactory.decodeStream((InputStream) new URL(info.getPosterURL()).getContent());
                    System.out.println(posterBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Message msg = handler.obtainMessage();
            msg.what = UPDATE_IMAGE;
            handler.sendMessage(msg);
        }
    }

    private boolean checkResponseForError(JSONObject obj)
    {
        boolean bolResponse = false;
        try {
            String response = obj.getString(Common_Util.RESPONSE);
            bolResponse = Boolean.valueOf(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bolResponse;
    }
    private MovieInfo parseJsonObject(JSONObject obj)
    {
        MovieInfo info = new MovieInfo();
        try {
            info.setDirector(obj.getString(Common_Util.DIRECTOR));
            info.setMovieName(obj.getString(Common_Util.TITLE));
            info.setPlotSummary(obj.getString(Common_Util.PLOT));
            info.setYear(obj.getString(Common_Util.YEAR));
            info.setPoster(obj.getString(Common_Util.POSTER));
            info.setActors(obj.getString(Common_Util.CAST));
            info.setRating(obj.getString(Common_Util.RATING));
            info.setRunTime(obj.getString(Common_Util.RUNTIME));
            info.setGenre(obj.getString(Common_Util.GENRE));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return info;
    }
}
