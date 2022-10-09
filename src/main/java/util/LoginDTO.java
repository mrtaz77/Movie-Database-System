package util;

import system.moviedatabase.Movie;

import java.io.Serializable;
import java.util.List;

public class LoginDTO implements Serializable{
    private String productionCompanyName;
    private List<Movie> movieList;
    private boolean status;

    public List<Movie> getMovieList() {
        System.out.println("In get list of loginDTO of "+productionCompanyName);
        movieList.forEach(Movie::showMovieDetails);
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        System.out.println("In set list of loginDTO of "+productionCompanyName);
        movieList.forEach(Movie::showMovieDetails);
        this.movieList = movieList;
    }


    public String getProductionCompanyName() {
        System.out.println("In get name of loginDTO of "+productionCompanyName);
        return productionCompanyName;
    }

    public void setProductionCompanyName(String productionCompanyName) {
        System.out.println("In set name of loginDTO of "+productionCompanyName);
        this.productionCompanyName = productionCompanyName;
    }

    public boolean isStatus() {
        System.out.println("isStatus of loginDTO of "+productionCompanyName);
        return status;
    }

    public void setStatus(boolean status) {
        System.out.println("setStatus of loginDTO of "+productionCompanyName);
        this.status = status;
    }
}
