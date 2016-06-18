package music.android.com.music;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mngr;
    private static String SEARCH_FRGMENT_ID = "search_fragment";
    private static String CARD_FRGMENT_ID = "card_fragment";
    private static String DETAILS_FRAGMENT_ID = "details_fragment";
    private static String FAVORITE_FRAGMENT_ID = "favorite_fragment";
    private MovieInfo information;
    private Bitmap movieImage;
    private FloatingActionButton backBtn;
    private HashMap<String, MapInfo> favMap = new HashMap<String, MapInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        tabs.addTab(tabs.newTab().setText(getApplicationContext().getResources().getString(R.string.tab_home)));
        tabs.addTab(tabs.newTab().setText(getApplicationContext().getResources().getString(R.string.tab_favorite)));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String title = tab.getText().toString();
                FragmentTransaction trns = mngr.beginTransaction();
                Fragment searchFragment = mngr.findFragmentByTag(SEARCH_FRGMENT_ID);
                Fragment favFragment = mngr.findFragmentByTag(FAVORITE_FRAGMENT_ID);
                if(title.equals(getApplicationContext().getResources().getString(R.string.tab_home)))
                {
                    if(favFragment != null) {
                        //trns.addToBackStack(FAVORITE_FRAGMENT_ID);
                        trns.hide(favFragment);
                    }
                    if (null != searchFragment) {
                        trns.show(searchFragment);
                    } else {
                        searchFragment = new SearchFragment();
                        trns.replace(R.id.fragment_container, searchFragment, SEARCH_FRGMENT_ID);
                        trns.commit();
                    }
                }
                else if(title.equals(getApplicationContext().getResources().getString(R.string.tab_favorite)))
                {
                    if(searchFragment != null) {
                        //trns.addToBackStack(SEARCH_FRGMENT_ID);
                        trns.hide(searchFragment);
                    }
                    if (null != favFragment) {
                        trns.show(favFragment);
                    } else {
                        favFragment = new FavoriteFragment();
                        trns.replace(R.id.fragment_container, favFragment, FAVORITE_FRAGMENT_ID);
                        trns.commit();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        backBtn = (FloatingActionButton) findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFragment = getCurrentFragment();
                mngr.popBackStack (currentFragment.getTag(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        addSearchFragment();
    }

    public void showBackButton()
    {
        backBtn.setVisibility(View.VISIBLE);
    }

    public void hideBackButton()
    {
        backBtn.setVisibility(View.GONE);
    }

    private void addSearchFragment()
    {
        mngr = getFragmentManager();
        FragmentTransaction trns = mngr.beginTransaction();
        SearchFragment frg = new SearchFragment();
        trns.add(R.id.fragment_container, frg, SEARCH_FRGMENT_ID);
        trns.commit();
    }

    public MovieInfo getResuts()
    {
        return information;
    }

    public HashMap<String, MapInfo> getFavMap()
    {
        return favMap;
    }

    public Bitmap getPosterImage()
    {
        return movieImage;
    }

    public void updateResultsView(MovieInfo info)
    {
        information = info;
        FragmentTransaction trns = mngr.beginTransaction();
        Fragment searchFragment = mngr.findFragmentByTag(SEARCH_FRGMENT_ID);
        Fragment cardFragment = mngr.findFragmentByTag(CARD_FRGMENT_ID);
        //mngr.popBackStackImmediate();
        if(searchFragment != null) {
            trns.addToBackStack(SEARCH_FRGMENT_ID);
            trns.hide(searchFragment);
        }
        if (null != cardFragment) {
            trns.show(cardFragment);
        } else {
            cardFragment = new CardFragment();
            trns.replace(R.id.fragment_container, cardFragment, CARD_FRGMENT_ID);
            trns.commit();
        }
    }

    public void updatePosterImage(Bitmap image)
    {
            movieImage = image;
    }

    public void updateIOError()
    {
        Toast.makeText(getApplicationContext(), "Sorry your movie is not found", Toast.LENGTH_LONG).show();
    }

    private Fragment getCurrentFragment(){

        String fragmentTag = mngr.getBackStackEntryAt(mngr.getBackStackEntryCount() - 1).getName();
        Fragment currentFragment = mngr.findFragmentByTag(fragmentTag);
        return currentFragment;
    }

    public void launchDetailsFragment() {
        FragmentTransaction trns = mngr.beginTransaction();
        Fragment detailsFragment = mngr.findFragmentByTag(DETAILS_FRAGMENT_ID);
        Fragment cardFragment = mngr.findFragmentByTag(CARD_FRGMENT_ID);
        //mngr.popBackStackImmediate();
        if (cardFragment != null) {
            trns.addToBackStack(DETAILS_FRAGMENT_ID);
            trns.hide(cardFragment);
        }
        if (null != detailsFragment) {
            trns.show(detailsFragment);
        } else {
            detailsFragment = new DetailsFragment();
            trns.replace(R.id.fragment_container, detailsFragment, DETAILS_FRAGMENT_ID);
            trns.commit();
        }
    }

}
