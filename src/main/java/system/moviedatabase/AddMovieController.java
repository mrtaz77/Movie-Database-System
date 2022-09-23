package system.moviedatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;


public class AddMovieController {
    private Main main;
    private String productionCompany;
    private List<Movie>movieList;

    @FXML
    private Label productionCompanyName;
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<Integer> genreCount;
    @FXML
    private TextField releaseYear;
    @FXML
    private TextField genres;
    @FXML
    private TextField runningTime;
    @FXML
    private TextField budget;
    @FXML
    private TextField revenue;

    @FXML
    private Button resetButton;
    @FXML
    private Button addMovieButton;
    @FXML
    private Button returnButton;

    public void init(String productionCompany, List<Movie> movieList) {
        this.movieList = movieList;
        this.productionCompany = StringRectifier.rectify(productionCompany);
        productionCompanyName.setText(productionCompany);
        Integer []num = {1,2,3};
        genreCount.getItems().addAll(num);
    }

    @FXML
    public void addMovieAction(ActionEvent event) {
        if(checkInput()) {
            Movie movie = getNewMovie();
            movie.showMovieDetails();
            try{
                main.getNetworkUtil().write(movie);
            }catch(Exception e){
                System.out.println("Exception in addMovieAction");
                e.printStackTrace();
            }
            showSuccess();
        }
    }

    public Movie getNewMovie() {
        String genre1,genre2,genre3;
        String []genre = genres.getText().split(",");
        if(genre.length == 3){
            genre1 = genre[0];
            genre2 = genre[1];
            genre3 = genre[2];
        }
        else if (genre.length == 2){
            genre1 = genre[0];
            genre2 = genre[1];
            genre3 = "";
        }
        else {
            genre1 = genre[0];
            genre2 = "";
            genre3 = "";
        }
        return new Movie(name.getText(),Integer.parseInt(releaseYear.getText()),genre1,genre2,genre3,Integer.parseInt(runningTime.getText()),productionCompany,Long.parseLong(budget.getText()),Long.parseLong(revenue.getText()));
    }

    public boolean checkInput(){
        if(checkNull("Title", name.getText())){
            System.out.println(name.getText());
            return false;
        }
        if(checkNull("Release Year", releaseYear.getText()) || !checkInteger("Release Year", releaseYear.getText())){
            System.out.println(releaseYear.getText());
            return false;
        }
        if(checkNull("Running Time", releaseYear.getText()) || !checkInteger("Running Time", releaseYear.getText())){
            System.out.println(runningTime.getText());
            return false;
        }
        if(checkNull("Budget", budget.getText()) || !checkLong("Budget", budget.getText())){
            System.out.println(budget.getText());
            return false;
        }
        if(checkNull("Revenue", revenue.getText()) || !checkLong("Revenue", revenue.getText())){
            System.out.println(revenue.getText());
            return false;
        }
        return checkValidGenre("Genre", genres.getText());
    }

    public boolean checkValidGenre(String headerText,String message) {
        String []genre = message.split(",");
        if(genreCount.getValue() == 0){
            showAlert("Invalid Movie Details","Invalid "+headerText,"Number of genre is not set");
            return false;
        }
        else if(genre.length != genreCount.getValue()){
            showAlert("Invalid Movie Details","Invalid "+headerText,"Number of genre must be "+genreCount.getValue());
            return false;
        }
        return true;
    }

    public boolean checkInteger(String headerText,String message) {
        try{
            int n = Integer.parseInt(message,10);
        }catch(NumberFormatException e){
            showAlert("Invalid Movie details","Invalid "+headerText,headerText+" is not a number");
            return false;
        }
        return true;
    }

    public boolean checkLong(String headerText,String message) {
        try{
            Long n = Long.parseLong(message,10);
        }catch(NumberFormatException e){
            showAlert("Invalid Movie details","Invalid "+headerText,headerText+" is not a number");
            return false;
        }
        return true;
    }

    public boolean checkNull(String headerText,String message) {
        try{
            int n = message.length();
            if(n == 0 )throw new NullPointerException();
        }catch(NullPointerException e){
            showAlert("Invalid Movie details","Invalid "+headerText,headerText+" cannot be blank");
            return true;
        }
        return false;
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
        name.setText(null);
        releaseYear.setText(null);
        genres.setText(null);
        runningTime.setText(null);
        budget.setText(null);
        revenue.setText(null);
    }

    public void showSuccess(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Movie");
        alert.setHeaderText("Successful Movie Addition!");
        alert.setContentText("Movie added successfully.");
        alert.showAndWait();
    }



    public void showAlert(String title,String headerText,String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    public void setMain(Main main) {
        this.main = main;
    }
}