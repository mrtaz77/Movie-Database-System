package server;

import system.moviedatabase.Movie;
import util.LoginDTO;
import util.NetworkUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReadThreadServer implements Runnable {
    private static final String OUTPUT_FILE_NAME = "movies.txt";
    private final Thread thr;
    private final NetworkUtil networkUtil;
    public List<Movie>movieList;

    public ReadThreadServer(NetworkUtil networkUtil, List<Movie> movieList) {
        this.networkUtil = networkUtil;
        this.movieList=movieList;
        this.thr=new Thread(this);
        thr.start();
    }
    @Override
    public void run(){
        System.out.println("In run of read thread server");
        try{
            while(true){
                Object o=networkUtil.read();
                if(o instanceof LoginDTO){
                    LoginDTO loginDTO = (LoginDTO) o;
                    String productionCompanyName=loginDTO.getProductionCompanyName();
                    loginDTO.setMovieList(Movie.getMovieByProductionCompany(productionCompanyName));
                    loginDTO.setStatus(Movie.isProductionCompany(productionCompanyName));
                    networkUtil.write(loginDTO);
                }
                else if(o instanceof Movie){
                    Movie movie = (Movie) o;
                    movieList.removeIf(temp -> temp.getName().equalsIgnoreCase(movie.getName()));
                    movieList.add(movie);
                    write(movieList);
                    networkUtil.write(movie);
                }
            }

        }catch(Exception e){
            System.out.println(e);
        }finally{
            try{
                networkUtil.closeConnection();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public void write(List<Movie> movieList)  {
        try{
            BufferedWriter fw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
            for(Movie temp:movieList){
                String movie=temp.getName()+","+temp.getReleaseYear()+","+temp.getGenre1()+","+temp.getGenre2()+","+temp.getGenre3()+","+temp.getRunningTime()+","+temp.getProductionCompany()+","+temp.getBudget()+","+temp.getRevenue();
                fw.write(movie);
                fw.write(System.lineSeparator());
            }
            fw.close();
        }catch (IOException e){
            System.out.println("File cannot be opened");
            e.printStackTrace();
        }
    }

}
