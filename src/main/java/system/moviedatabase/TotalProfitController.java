package system.moviedatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.List;

public class TotalProfitController {

    private Main main;

    private String productionCompany;

    private List<Movie>movieList;

    @FXML
    private Label totalProfit;

    @FXML
    private Label productionCompanyName;

    @FXML
    private Button returnButton;

    public void init(String productionCompany, List<Movie>movieList)throws Exception{
        String str;
        this.productionCompany = StringRectifier.rectify(productionCompany);
        productionCompanyName.setText(productionCompany);
        this.movieList = movieList;
        long profit = Movie.getTotalProfit(productionCompany);
        if(profit <0)str="Total Loss : "+(-profit);
        else str="Total Profit : "+ profit;
        totalProfit.setText(str);
    }

    public void returnAction(ActionEvent event){
        try{
            main.showMainMenu(productionCompany,movieList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMain(Main main){
        this.main = main;
    }
}
