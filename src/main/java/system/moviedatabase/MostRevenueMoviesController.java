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

import java.util.List;



public class MostRevenueMoviesController {
    private Main main;
    private String productionCompany;
    private List<Movie>movieList;
    @FXML private Label message;
    @FXML private Button returnbutton;
    @FXML private TableView<Movie> tableView;
    @FXML private TableColumn<Movie, SimpleStringProperty> titleColumn;
    @FXML private TableColumn<Movie,SimpleIntegerProperty> releaseYearColumn;
    @FXML private TableColumn<Movie, SimpleStringProperty> genreColumn;
    @FXML private TableColumn<Movie,SimpleIntegerProperty> runningTimeColumn;
    @FXML private TableColumn<Movie, SimpleLongProperty> budgetColumn;
    @FXML private TableColumn<Movie,SimpleLongProperty> revenueColumn;


    public void init(String msg) {
        productionCompany= StringRectifier.rectify(msg);
        message.setText(productionCompany);
        this.movieList=Movie.getMaxRevenue(productionCompany);
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
    public void returnAction(ActionEvent event) {
        try {
            main.showMainMenu(productionCompany,Movie.getMovieByProductionCompany(productionCompany));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main){
        this.main = main;
    }
}
