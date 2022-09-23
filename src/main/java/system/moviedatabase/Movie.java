package system.moviedatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {
    //members
    private String name,productionCompany;
    private String genre1,genre2,genre3,genre;
    private int releaseYear,runningTime;
    private long budget,revenue;

    //static members
    private static List<Movie>movielist = new ArrayList<>();

    //methods

    //constructor
    public Movie(String name,int releaseYear,String genre1,String genre2,String genre3,int runningTime, String productionCompany,long budget,long revenue){
        this.name=name;
        this.productionCompany=productionCompany;
        this.genre1=genre1;
        this.genre2=genre2;
        this.genre3=genre3;
        this.genre=genre1;
        if(genre2.length()!=0)this.genre=this.genre+","+genre2;
        if(genre3.length()!=0)this.genre=this.genre+","+genre3;
        this.releaseYear=releaseYear;
        this.runningTime=runningTime;
        this.budget=budget;
        this.revenue=revenue;
        movielist.add(this);
    }

    public Movie(Movie m){
        movielist.add(m);
    }

    //set-methods
    public void setName(String name){this.name=name;}
    public void setProductionCompany(String productionCompany){this.productionCompany=productionCompany;}
    public void setReleaseYear(int releaseYear){this.releaseYear=releaseYear;}
    public void setRunningTime(int runningTime){this.runningTime=runningTime;}
    public void setBudget(long budget){this.budget=budget;}
    public void setRevenue(long revenue){this.revenue=revenue;}
    public void setGenre1(String genre){this.genre1=genre;}
    public void setGenre2(String genre){this.genre2=genre;}
    public void setGenre3(String genre){this.genre3=genre;}
    
    //get-methods
    public String getName(){return name;}
    public String getProductionCompany(){return productionCompany;}
    public int getReleaseYear(){return releaseYear;}
    public int getRunningTime(){return runningTime;}
    public long getBudget(){return budget;}
    public long getRevenue (){return revenue;}
    public String getGenre1(){return genre1;}
    public String getGenre2(){return genre2;}
    public String getGenre3(){return genre3;}
    public String getGenre(){return genre;}
    public static List<Movie> getMovieList(){return movielist;}

    public void showMovieDetails(){
        System.out.println("Title : "+name);
        System.out.println("ReleaseYear : "+releaseYear);
        System.out.print("Genre   : "+genre1);
        if(!genre2.equals(""))System.out.print(", "+genre2);
        if(!genre3.equals(""))System.out.print(", "+genre3);
        System.out.println(".");
        System.out.println("Running Time : "+runningTime+" minutes");
        System.out.println("Production Company : "+productionCompany);
        System.out.println("Budget : "+budget);
        System.out.println("Revenue : "+revenue);
    }

    public long getProfit(){return revenue-budget;}

    public static boolean isName(String name){
        for(Movie m : movielist){
            if(m.getName().equalsIgnoreCase(name))return true;
        }
        return false;
    }

    public static boolean isGenre(String genre){
        for(Movie m : movielist){
            if(m.getGenre1().equalsIgnoreCase(genre))return true;
            else if(m.getGenre2().equalsIgnoreCase(genre))return true;
            else if(m.getGenre3().equalsIgnoreCase(genre))return true;
        }
        return false;
    }

    public static boolean isReleaseYear(int year){
        for(Movie m : movielist){
            if(m.getReleaseYear()==year )return true;
        }
        return false;
    }

    public static boolean isProductionCompany(String productionCompany){
        for(Movie m : movielist){
            if(m.getProductionCompany().equalsIgnoreCase(productionCompany))return true;
        }
        return false;
    }

    public static boolean isRunningTimeInRange(int low,int high){
        for(Movie m : movielist){
            if(m.getRunningTime()>=low && m.getRunningTime()<=high )return true;
        }
        return false;
    }

    public static Movie getMovieByName(String name){
    for(Movie m:movielist){
        if(m.getName().equalsIgnoreCase(name))return m;
    }    
    return null;
    }

    public static List<Movie> getMovieByYear(int year){
        List<Movie>movies = new ArrayList<>();
        for(Movie m:movielist){
            if(m.getReleaseYear()==year)movies.add(m);
        }    
        return movies;
    }

    public static List<Movie> getMovieByGenre(String genre){
        List<Movie>movies = new ArrayList<>();
        for(Movie m:movielist){
            if(m.genre1.equalsIgnoreCase(genre) || m.genre2.equalsIgnoreCase(genre) || m.genre3.equalsIgnoreCase(genre))movies.add(m);
        }    
        return movies;
    }

    public static List<Movie> getMovieByProductionCompany(String productionCompany){
        List<Movie>movies = new ArrayList<>();
        for(Movie m:movielist){
            if(m.productionCompany.equalsIgnoreCase(productionCompany))movies.add(m);
        }
        return movies;
    }

    public static List<Movie> getMovieByRunningTime(int low,int high){
        List<Movie>movies = new ArrayList<>();
        for(Movie m:movielist){
            if(m.runningTime>=low && m.runningTime<=high)movies.add(m);
        }    
        return movies;
    }

    public static List<Movie> top10Movies(){
        List<Movie>movies = new ArrayList<>();
        for(int i=0;i<10;i++){
            for(int j=movielist.size()-1;j>i;j--){
                if(movielist.get(j).getProfit()>movielist.get(j-1).getProfit()){
                    Movie temp=movielist.get(j);
                    movielist.set(j,movielist.get(j-1));
                    movielist.set(j-1,temp);
                }
            }
        }
        for(int i=0;i<10;i++)movies.add(movielist.get(i));
        return movies;
    }

    public static List<Movie> getMostRecentMovies(String productionCompany){
        List<Movie> mostRecentMovies = new ArrayList<Movie>();
        List<Movie> movies=Movie.getMovieByProductionCompany(productionCompany);
        int latestYear=0;
        for(int i=0;i<movies.size();i++){
            if(latestYear<movies.get(i).getReleaseYear())latestYear=movies.get(i).getReleaseYear();    
        }
        for(Movie m:movies){
            if(m.getReleaseYear()==latestYear)mostRecentMovies.add(m);
        }
        return mostRecentMovies;   
    }

    public static List<Movie> getMaxRevenue(String productionCompany){
        List<Movie> movies = Movie.getMovieByProductionCompany(productionCompany);
        List<Movie> maxRevenueMovies = new ArrayList<Movie>();
        long maxRevenue=0;
        for(int i=0;i<movies.size();i++){
            if(maxRevenue<movies.get(i).getRevenue())maxRevenue=movies.get(i).getRevenue();
        }
        for(Movie m:movies){
            if(m.getRevenue()==maxRevenue)maxRevenueMovies.add(m);
        }
        return maxRevenueMovies;
    }

    public static long getTotalProfit(String productionCompany){
        long totalProfit=0;
        List<Movie> movies = Movie.getMovieByProductionCompany(productionCompany);
        for(Movie m:movies){
            totalProfit+=m.getProfit();
        }
        return totalProfit;
    }

    public static List<String> getProductionCompanies(){
        List<String> companies = new ArrayList<>();
        for(Movie m:movielist){
            int i;
            for(i=0;i<companies.size() && !m.getProductionCompany().equalsIgnoreCase(companies.get(i));i++);
            if(i==companies.size())companies.add(m.getProductionCompany());
        }
        return companies;
    }

    public static int getMovieCount(String productionCompany){
        return Movie.getMovieByProductionCompany(productionCompany).size();
    }
}   