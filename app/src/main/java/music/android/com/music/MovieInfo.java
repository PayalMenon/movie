package music.android.com.music;

/**
 * Created by payal.menon on 6/17/16.
 */
public class MovieInfo {

    private String movieName;
    private String director;
    private String year;
    private String plotSummary;
    private String posterURL;
    private String actors;
    private String runTime;
    private String rating;
    private String genre;
    private String response;

    public void setMovieName(String name)
    {
        this.movieName = name;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public void setPlotSummary(String shortSummary)
    {
        this.plotSummary = shortSummary;
    }

    public void setPoster(String posterURL)
    {
        this.posterURL = posterURL;
    }

    public void setActors(String actors)
    {
        this.actors = actors;
    }

    public void setRunTime(String runTime)
    {
        this.runTime = runTime;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public void setResponse(String response)
    {
        this.response = response;
    }

    public String getMovieName()
    {
        return this.movieName;
    }

    public String getResponse()
    {
        return this.response;
    }

    public String getDirector()
    {
        return this.director;
    }

    public String getYear()
    {
        return this.year;
    }

    public String getPlotSummary()
    {
        return this.plotSummary;
    }

    public String getPosterURL()
    {
        return this.posterURL;
    }

    public String getActors()
    {
        return this.actors;
    }

    public String getRunTime()
    {
        return this.runTime;
    }

    public String getRating()
    {
        return this.rating;
    }

    public String getGenre()
    {
        return this.genre;
    }

    public String toString()
    {
        return (this.movieName + this.director);
    }
}
