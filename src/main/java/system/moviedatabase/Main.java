package system.moviedatabase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Stage stage;
    private String productionCompany;
    private List<Movie>movieList = new ArrayList<>();
    private NetworkUtil networkUtil;

    public Stage getStage() {
        return stage;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }
    public String getProductionCompany() {
        return productionCompany;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        showLoginPage();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ReadThread(this);
    }

    public void showLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Loading the controller
        LoginController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 400, 250));
        stage.show();

    }

    public void setProductionCompany(String productionCompany){
        this.productionCompany=StringRectifier.rectify(productionCompany);
    }


    public void showMainMenu(String productionCompany, List<Movie> movieList) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainmenu.fxml"));
        Parent root = loader.load();

        //setting clientName
        setProductionCompany(productionCompany);

        // Loading the controller
        MainMenuController controller = loader.getController();
        controller.init(productionCompany,movieList);
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Main menu");
        stage.setScene(new Scene(root, 850, 600));
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            exit();
        });
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Login");
        alert.setHeaderText("Invalid Login");
        alert.setContentText("Production Company not found.");
        alert.showAndWait();
    }

    public void showTotalProfitPage(String productionCompany, List<Movie> movieList) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("totalprofit.fxml"));
        Parent root = loader.load();

        // Loading the controller
        TotalProfitController controller = loader.getController();
        controller.init(productionCompany,movieList);
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Total Profit");
        stage.setScene(new Scene(root, 400, 200));
        stage.show();
    }

    public void showMostRecentMoviesPage(String productionCompany,List<Movie> movieList)throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mostrecentmovies.fxml"));
        Parent root = loader.load();

        // Loading the controller
        MostRecentMoviesController controller = loader.getController();
        controller.init(productionCompany,movieList);
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Most Recent Movies");
        stage.setScene(new Scene(root, 850, 400));
        stage.show();
    }

    public void showMostRevenueMoviesPage(String productionCompany,List<Movie> movieList)throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mostrevenuemovies.fxml"));
        Parent root = loader.load();

        // Loading the controller
        MostRevenueMoviesController controller = loader.getController();
        controller.init(productionCompany);
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Most Revenue Movies");
        stage.setScene(new Scene(root, 850, 400));
        stage.show();
    }

    public void showAddMoviePage(String productionCompany,List<Movie> movieList)throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addmovie.fxml"));
        Parent root = loader.load();

        // Loading the controller
        AddMovieController controller = loader.getController();
        controller.init(productionCompany,movieList);
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Add movie");
        stage.setScene(new Scene(root, 600, 650));
        stage.show();
    }

    public void showTransferMoviePage(String productionCompany,List<Movie> movieList)throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("transfermovie.fxml"));
        Parent root = loader.load();

        // Loading the controller
        TransferMovieController controller = loader.getController();
        controller.init(productionCompany,movieList);
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Transfer Movie");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
    public void exit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Closing Database");
        alert.setHeaderText("Are you sure you want to exit?");

        if(alert.showAndWait().get() == ButtonType.OK ) {
            stage.close();
        }
    }


    public static void main(String[] args) {
        // This will launch the JavaFX application
        launch(args);
    }
}
