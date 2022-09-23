module myjfx {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    opens system.moviedatabase to javafx.graphics, javafx.fxml,javafx.base;
}