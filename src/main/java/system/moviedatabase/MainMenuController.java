package system.moviedatabase;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.LoginDTO;

import java.io.IOException;
import java.util.List;

public class MainMenuController {
    private Main main;
    private String productionCompany;
    private List<Movie>movieList;
    @FXML private Label message;
    @FXML private Button logOutButton;
    @FXML private Button refreshbutton;
    @FXML private TableView<Movie> tableView;
    @FXML private TableColumn<Movie, SimpleStringProperty> titleColumn;
    @FXML private TableColumn<Movie,SimpleIntegerProperty> releaseYearColumn;
    @FXML private TableColumn<Movie, SimpleStringProperty> genreColumn;
    @FXML private TableColumn<Movie,SimpleIntegerProperty> runningTimeColumn;
    @FXML private TableColumn<Movie, SimpleLongProperty> budgetColumn;
    @FXML private TableColumn<Movie,SimpleLongProperty> revenueColumn;


    public void init(String msg, List<Movie> movieList) {
        productionCompany = StringRectifier.rectify(msg);
        message.setText(productionCompany);
        if(Movie.getMovieList().isEmpty())for(Movie movie:movieList)new Movie(movie);
        this.movieList = movieList;
        movieList.sort(new MovieComparator());
        //set up column
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        releaseYearColumn.setCellValueFactory(new  PropertyValueFactory<>("releaseYear"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        runningTimeColumn.setCellValueFactory(new PropertyValueFactory<>("runningTime"));
        budgetColumn.setCellValueFactory(new PropertyValueFactory<>("budget"));
        revenueColumn.setCellValueFactory(new PropertyValueFactory<>("revenue"));

        tableView.setItems(getObservableMovieList());

    }

    public ObservableList<Movie> getObservableMovieList() {
        ObservableList<Movie> observableMovieList=FXCollections.observableArrayList();
        observableMovieList.addAll(movieList);
        return observableMovieList;
    }


    @FXML
    public void logOutAction(ActionEvent event) {
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void viewTotalProfitAction(ActionEvent event){
        try{
            main.showTotalProfitPage(productionCompany,movieList);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void viewMostRecentMoviesAction(ActionEvent event){
        try{
            main.showMostRecentMoviesPage(productionCompany,movieList);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void viewMostRevenueMoviesAction(ActionEvent event){
        try{
            main.showMostRevenueMoviesPage(productionCompany,movieList);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void addMovieAction(ActionEvent event){
        try{
            main.showAddMoviePage(productionCompany,movieList);
        }catch (Exception e){
            System.out.println("Exception "+e+" in addMovieAction");
            e.printStackTrace();
        }
    }

    @FXML
    public void transferMovieAction(ActionEvent event){
        try{
            main.showTransferMoviePage(productionCompany,movieList);
        }catch (Exception e){
            System.out.println("Exception "+e+" in transferMovieAction");
            e.printStackTrace();
        }
    }

    @FXML
    public void refreshAction(ActionEvent event){
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setProductionCompanyName(main.getProductionCompany());
        try {
            main.getNetworkUtil().write(loginDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setMain(Main main) {
        this.main = main;
    }
}
