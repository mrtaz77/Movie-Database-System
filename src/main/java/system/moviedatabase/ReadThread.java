package system.moviedatabase;

import javafx.application.Platform;
import util.LoginDTO;

import java.io.IOException;
import java.util.List;

public class ReadThread implements Runnable {
    private final Thread thr;
    private final Main main;

    public ReadThread(Main main) {
        this.thr = new Thread(this);
        this.main = main;
        thr.start();
    }

    @Override
    public void run(){
        try{
            while(true){
                Object o=main.getNetworkUtil().read();
                if(o!=null){
                    if(o instanceof LoginDTO){
                        LoginDTO loginDTO = (LoginDTO) o;
                        System.out.println(loginDTO.getProductionCompanyName());
                        System.out.println(loginDTO.isStatus());
                        Platform.runLater(() -> {
                            if (loginDTO.isStatus()) {
                                try {
                                    main.showMainMenu (loginDTO.getProductionCompanyName(),loginDTO.getMovieList());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                main.showAlert();
                            }
                        });
                    }
                    if(o instanceof Movie){
                        Movie movie = (Movie) o;
                        List<Movie> movieList = Movie.getMovieByProductionCompany(movie.getProductionCompany());
                        if(movie.getProductionCompany().equalsIgnoreCase(main.getProductionCompany())){
                            movieList.sort(new MovieComparator());
                            Platform.runLater(() -> {
                               try{
                                   System.out.println(main.getProductionCompany()+" "+main.getMovieList().size());
                                   main.showAddMoviePage(StringRectifier.rectify(main.getProductionCompany()),Movie.getMovieByProductionCompany(main.getProductionCompany()));
                               } catch (Exception e) {
                                   System.out.println("Exception in run of readThread of addMovie");
                                   e.printStackTrace();
                               }
                            });
                        }
                        else {
                            movieList.sort(new MovieComparator());
                            System.out.println(movieList.size());
                            Platform.runLater(() -> {
                                try{
                                    main.showTransferMoviePage(StringRectifier.rectify(main.getProductionCompany()),Movie.getMovieByProductionCompany(main.getProductionCompany()));
                                } catch (Exception e) {
                                    System.out.println("Exception in run of readThread of transferMovie");
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                main.getNetworkUtil().closeConnection();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
