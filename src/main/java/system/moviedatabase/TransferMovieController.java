package system.moviedatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class TransferMovieController {
    private Main main;
    private String productionCompany;
    private List<Movie> movieList;

    @FXML
    private TextField productionCompanyName;
    @FXML
    private ChoiceBox<String> movieListMenu;

    @FXML
    private Button resetButton;
    @FXML
    private Button transferButton;
    @FXML
    private Button returnButton;

    public void init(String productionCompany, List<Movie> movieList) {
        this.movieList = movieList;
        this.productionCompany = StringRectifier.rectify(productionCompany);
        List<String>movieName = new ArrayList<>();
        for(Movie movie:movieList)movieName.add(movie.getName());
        movieListMenu.getItems().addAll(movieName);
    }

    @FXML
    public void transferAction(ActionEvent event) {
        if(!isChoiceNull() && !isProductionCompanyNull()){
            Movie movie = Movie.getMovieByName(movieListMenu.getValue());
            if (movie != null) {
                movie.setProductionCompany(productionCompanyName.getText());
                try{
                    main.getNetworkUtil().write(movie);
                }catch(Exception e){
                    System.out.println("Exception in transferMovieAction");
                    e.printStackTrace();
                }
            }
            showSuccess();
            movieList.remove(movie);
        }
    }

    private boolean isChoiceNull() {
        if(movieListMenu.getValue().length()==0){
            showAlert("Transfer Movie","Invalid Transfer","A movie must be chosen");
            return true;
        }
        return false;
    }

    private boolean isProductionCompanyNull() {
        if(productionCompanyName.getText().equalsIgnoreCase("")){
            showAlert("Transfer Movie","Invalid Transfer","A Production Company must be given");
            return true;
        }
        return false;
    }



    public void showSuccess(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Transfer Movie");
        alert.setHeaderText("Successful Movie Transfer!");
        alert.setContentText("Movie transferred successfully.");
        alert.showAndWait();
    }

    public void showAlert(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    public void returnAction(){
        resetAction();
        try{
            main.showMainMenu(productionCompany,movieList);
        }catch(Exception e){
            System.out.println("Caught exception on return to main");
            e.printStackTrace();
        }
    }

    @FXML
    public void resetAction() {
        productionCompanyName.setText(null);
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
