package util;

import system.moviedatabase.Movie;

import java.io.Serializable;
import java.util.List;

public class LoginDTO implements Serializable{
    private String productionCompanyName;
    private List<Movie> movieList;
    private boolean status;

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }


    public String getProductionCompanyName() {
        return productionCompanyName;
    }

    public void setProductionCompanyName(String productionCompanyName) {
        this.productionCompanyName = productionCompanyName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
