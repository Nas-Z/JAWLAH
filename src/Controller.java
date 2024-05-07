
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private AnchorPane parentPane;

    @FXML
    private Button abhaBtn;

    @FXML
    private Button dammamBtn;

    @FXML
    private Button jeddahBtn;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button riyadhBtn;

    @FXML
    private Button tabukBtn;

    @FXML
    private Button ulaBtn;

    @FXML
    private AnchorPane jeddahPane;

    @FXML
    private AnchorPane abhaPane;

    @FXML
    private AnchorPane riyadhPane;

    @FXML
    private AnchorPane ulaPane;

    @FXML
    private AnchorPane tabukPane;

    @FXML
    private AnchorPane dammamPane;

    @FXML
    private Button backBtn;

    private Pane currentPane;

    @FXML
    private Button logOutBtn;

    DropShadow glow;

    @FXML
    private AnchorPane commentsVidPane;

    @FXML
    private ListView<String> listViewJed;

     @FXML
    private MediaView mediaViewJed;
    

    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;

    private ArrayList<String> comments = new ArrayList<>();
    

      @FXML
    private TextField commentTxtField;

    @FXML
    private Button addCommentBtn;

    @FXML
    private Button closeCommentVidPaneBtn;

    private String currentPlaceSelected; // TO TRACK WHICH PLACE THE USER SELECTED TO DISPALY ITS VIDEO AND COMMENTS, TO HELP WITH ADDING COMMENTS TO THE RIGHT PLACE IN DATABASE
    private String currentCitySelected; // TO TRACK WHICH city THE USER SELECTED, TO HELP WITH INSERTING THE RIGHT CITY WHEN ADDING COMMENTS IN DATABASE

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private Button userCommentsBtn;

    @FXML
    private AnchorPane userCommentsPane;

    @FXML
    private ListView<String> userCommentsLV;

    @FXML
    private ComboBox<String> cityCBox;
    @FXML
    private ComboBox<String> placeCBox;
    private String[] cities = {"Riyadh", "Jeddah", "Ula", "Tabuk", "Abha", "Dammam"};
    private String[] jedPlaces = {"Balad", "Fakieh Aquarium", "Jeddah Yacht Club", "Jeddah Art Promenade"};
    private String[] ruhPlaces = {"Edge Of The World", "Al-Masmak", "Boulevard", "Murabba Palace"};
    private String[] ulaPlaces = {"Hijaz Railway", "Ula Old Town", "Jabal Al-Fil", "Maraya"};
    private String[] tPlaces = {"Murooj Park", "Mud House", "Hokair Mall", "Boulevard"};
    private String[] abhaPlaces = {"Rijal Almaa", "Fatimah Museum", "High City", "Bin Hamsan"};
    private String[] dmPlaces = {"Marjan Island", "Takyt Bahar", "City Bike", "Scitech Center"};


    public void diaplayPanes(Pane pane){
        
        jeddahPane.setVisible(false);
        mainPane.setVisible(false);
        riyadhPane.setVisible(false);
        ulaPane.setVisible(false);
        tabukPane.setVisible(false);
        abhaPane.setVisible(false);
        dammamPane.setVisible(false);

        pane.setVisible(true);
    }

    @FXML
    void jeddah(ActionEvent event) {

        currentCitySelected = "Jeddah";
        currentPane = jeddahPane;
        diaplayPanes(jeddahPane);
        backBtn.setVisible(true);
    }

    @FXML
    void riyadh(ActionEvent event) {

        currentCitySelected = "Riyadh";
        currentPane = riyadhPane;
        diaplayPanes(riyadhPane);
        backBtn.setVisible(true);
    }

    @FXML
    void ula(ActionEvent event) {

        currentCitySelected = "Ula";
        currentPane = ulaPane;
        diaplayPanes(ulaPane);
        backBtn.setVisible(true);
    }

    @FXML
    void tabuk(ActionEvent event) {

        currentCitySelected = "Tabuk";
        currentPane = tabukPane;
        diaplayPanes(tabukPane);
        backBtn.setVisible(true);
    }

    @FXML
    void abha(ActionEvent event) {

        currentCitySelected = "Abha";
        currentPane = abhaPane;
        diaplayPanes(abhaPane);
        backBtn.setVisible(true);
    }

    @FXML
    void dammam(ActionEvent event) {

        currentCitySelected = "Dammam";
        currentPane = dammamPane;
        diaplayPanes(dammamPane);
        backBtn.setVisible(true);
    }

    @FXML
    void back(ActionEvent event) {
        currentCitySelected = "";
        currentPane.setVisible(false);
        mainPane.setVisible(true);
        backBtn.setVisible(false);
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("JAWLAH - Saudi Tour");
        stage.setScene(scene);
        stage.show();

        logOutBtn.getScene().getWindow().hide();
    }

    @FXML
    void glowEnter(MouseEvent event) {

        glow = new DropShadow();
        glow.setWidth(10);
        glow.setHeight(10);
        glow.setRadius(10);
        glow.setColor(Color.LIGHTGREEN);
        backBtn.setEffect(glow);
    }

    @FXML
    void glowExit(MouseEvent event) {

        backBtn.setEffect(null);
    }

    void commentsVidDisplay(String currentPlace, String vidPath){
        file = new File(vidPath);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaViewJed.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();

        String selectData = "SELECT comment FROM comment WHERE place = '" + currentPlace + "'";
        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()){
                String comment = result.getString("comment");
                comments.add(comment);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        listViewJed.getItems().addAll(comments);
        commentsVidPane.setVisible(true);
    }
    


    @FXML
    void addComment(ActionEvent event) {

        if (!commentTxtField.getText().isEmpty()){
        String insertData = "INSERT INTO comment (username, city, place, comment) VALUES(?,?,?,?)";
        connect = Database.connectDB();
        
        try {

            prepare = connect.prepareStatement(insertData);

            prepare.setString(1, LoginController.currentUsername);
            prepare.setString(2, currentCitySelected);
            prepare.setString(3, currentPlaceSelected);
            prepare.setString(4, commentTxtField.getText());

            prepare.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        listViewJed.getItems().clear();
        comments.add(commentTxtField.getText());
        listViewJed.getItems().addAll(comments);
        commentTxtField.clear();
    }
    }

    @FXML
    void closeCommebtVidPane(ActionEvent event) {
        listViewJed.getItems().clear();
        mediaPlayer.stop();
        commentTxtField.clear();
        comments.clear();
        currentPlaceSelected = "";
        commentsVidPane.setVisible(false);
    }

    //JEDDAH PLACES
    @FXML
    void baladDisplay(MouseEvent event) {
        currentPlaceSelected = "Balad";
        commentsVidDisplay(currentPlaceSelected, "Videos/Al-Balad_JED.mp4");
    
    }

    @FXML
    void fakiehAquariumDisplay(MouseEvent event) {
        currentPlaceSelected = "Fakieh Aquarium";
        commentsVidDisplay(currentPlaceSelected, "Videos/FakiehAquarium_JED.mp4");
    }

    @FXML
    void yachtClubDisplay(MouseEvent event) {
        currentPlaceSelected = "Jeddah Yacht Club";
        commentsVidDisplay(currentPlaceSelected, "Videos/YachtClub_JED.mp4");
    }

    @FXML
    void artPromenadeDisplay(MouseEvent event) {
        currentPlaceSelected = "Jeddah Art Promenade";
        commentsVidDisplay(currentPlaceSelected, "Videos/ArtPromenade_JED.mp4");
    }

    //RIYADH PLACES
    @FXML
    void edgeWorldDisplay(MouseEvent event) {
        currentPlaceSelected = "Edge Of The World";
        commentsVidDisplay(currentPlaceSelected, "Videos/EdgeOfTheWorld_RUH.mp4");
    }

    @FXML
    void masmakDisplay(MouseEvent event) {
        currentPlaceSelected = "Al-Masmak";
        commentsVidDisplay(currentPlaceSelected, "Videos/Masmak_RUH.mp4");
    }

    @FXML
    void boulevardDisplay(MouseEvent event) {
        currentPlaceSelected = "Boulevard";
        commentsVidDisplay(currentPlaceSelected, "Videos/Boulevard_RUH.mp4");
    }

    @FXML
    void murabbaDisplay(MouseEvent event) {
        currentPlaceSelected = "Murabba Palace";
        commentsVidDisplay(currentPlaceSelected, "Videos/Murabba_RUH.mp4");
    }

    //AL-ULA PLACES
    @FXML
    void hijazRailwayDisplay(MouseEvent event) {
        currentPlaceSelected = "Hijaz Railway";
        commentsVidDisplay(currentPlaceSelected, "Videos/HijazRailway_ULA.mp4");
    }

    @FXML
    void oldTownDisplay(MouseEvent event) {
        currentPlaceSelected = "Ula Old Town";
        commentsVidDisplay(currentPlaceSelected, "Videos/OldTown_ULA.mp4");
    }

    @FXML
    void jabalFilDisplay(MouseEvent event) {
        currentPlaceSelected = "Jabal Al-Fil";
        commentsVidDisplay(currentPlaceSelected, "Videos/JabalFil_ULA.mp4");
    }

    @FXML
    void marayaDisplay(MouseEvent event) {
        currentPlaceSelected = "Maraya";
        commentsVidDisplay(currentPlaceSelected, "Videos/Maraya_ULA.mp4");
    }

    //TABUK PLACES
    @FXML
    void muroojDisplay(MouseEvent event) {
        currentPlaceSelected = "Murooj Park";
        commentsVidDisplay(currentPlaceSelected, "Videos/MuroojPark_T.mp4");
    }

    @FXML
    void mudHouseDisplay(MouseEvent event) {
        currentPlaceSelected = "Mud House";
        commentsVidDisplay(currentPlaceSelected, "Videos/MuroojPark_T.mp4");
    }

    @FXML
    void hokairMallDisplay(MouseEvent event) {
        currentPlaceSelected = "Hokair Mall";
        commentsVidDisplay(currentPlaceSelected, "Videos/HukairMall_T.mp4");
    }

    @FXML
    void boulevardTabukDisplay(MouseEvent event) {

    }

    //ABHA PLACES
    @FXML
    void rijalAlmaa_AB(MouseEvent event) {
        currentPlaceSelected = "Rijal Almaa";
        commentsVidDisplay(currentPlaceSelected, "Videos/RijalAlmaa_AB.mp4");
    }

    @FXML
    void fatimahMuseumDisplay(MouseEvent event) {
        currentPlaceSelected = "Fatimah Museum";
        commentsVidDisplay(currentPlaceSelected, "Videos/FatimahMueseum_AB.mp4");
    }

    @FXML
    void highCityDisplay(MouseEvent event) {
        currentPlaceSelected = "High City";
        commentsVidDisplay(currentPlaceSelected, "Videos/HighCity_AB.mp4");
    }

    @FXML
    void binHamsanDisplay(MouseEvent event) {
        currentPlaceSelected = "Bin Hamsan";
        commentsVidDisplay(currentPlaceSelected, "Videos/BinHamsan_AB.mp4");
    }

    //DAMMAM PLACES
    @FXML
    void marjanIslandDisplay(MouseEvent event) {
        currentPlaceSelected = "Marjan Island";
        commentsVidDisplay(currentPlaceSelected, "Videos/marjan_DM.mp4");
    }

    @FXML
    void takytBaharDisplay(MouseEvent event) {
        currentPlaceSelected = "Takyt Bahar";
        commentsVidDisplay(currentPlaceSelected, "Videos/TakytBahar_DM.mp4");
    }

    @FXML
    void cityBikeDisplay(MouseEvent event) {
        currentPlaceSelected = "City Bike";
        commentsVidDisplay(currentPlaceSelected, "Videos/CityBike_DM.mp4");
    }

    @FXML
    void scitechDisplay(MouseEvent event) {
        currentPlaceSelected = "Scitech Center";
        commentsVidDisplay(currentPlaceSelected, "Videos/Scitech_DM.mp4");
    }





    //DISPLAY USER COMMENTS WHEN PRESSING ON YOUR COMMENTS BUTTON
    void cityListDisplayed(ComboBox cb, String[] arr){
        List<String> listC = new ArrayList<>();

        for (String element : arr) {
            listC.add(element);
        }

        ObservableList listData = FXCollections.observableArrayList(listC);
        cb.setItems(listData);
    }

    @FXML
    void displayUserComments(ActionEvent event) {
        cityListDisplayed(cityCBox,cities);
        userCommentsPane.setVisible(true);
    }

    @FXML
    void closeUserComments(ActionEvent event) {
        placeCBox.getSelectionModel().select("Select a Place");
        cityCBox.getSelectionModel().select("Select a City");
        placeCBox.setDisable(true);
        userCommentsPane.setVisible(false);
    }

    @FXML
    void displayPlaces(ActionEvent event) {
        String city = cityCBox.getSelectionModel().getSelectedItem();

        switch (city) {
            case "Jeddah":
                cityListDisplayed(placeCBox,jedPlaces);
                break;
            case "Riyadh":
                cityListDisplayed(placeCBox,ruhPlaces);
                break;
            case "Ula":
                cityListDisplayed(placeCBox,ulaPlaces);
                break;
            case "Tabuk":
                cityListDisplayed(placeCBox,tPlaces);
                break;
            case "Abha":
                cityListDisplayed(placeCBox,abhaPlaces);
                break;
            case "Dammam":
                cityListDisplayed(placeCBox,dmPlaces);
                break;
            default:
                break;
        }

        placeCBox.setDisable(false);
    }

    @FXML
    void displayPlaceUserComments(ActionEvent event) {
        userCommentsLV.getItems().clear();
        String place = placeCBox.getSelectionModel().getSelectedItem();

        comments.clear();

        String selectData = "SELECT comment FROM comment WHERE place ='" + place +"' AND username ='" + LoginController.currentUsername + "'";
        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()){
                String comment = result.getString("comment");
                comments.add(comment);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        userCommentsLV.getItems().addAll(comments);
    }

    @FXML
    void editComment(ActionEvent event) {
        String selectedComment = userCommentsLV.getSelectionModel().getSelectedItem();
        if (selectedComment!= null){
            TextInputDialog dialog = new TextInputDialog(selectedComment);
            dialog.setTitle("Edit Comment");
            dialog.setHeaderText("Edit the selected comment:");
            dialog.setContentText("New Comment:");
            
            dialog.showAndWait().ifPresent(newComment -> {

                if (!newComment.trim().isEmpty()){
                // Update the comment in the list
                int selectedIndex = userCommentsLV.getSelectionModel().getSelectedIndex();
                comments.set(selectedIndex, newComment);
    
                // You may also need to update the comment in the database if necessary
    
                // Refresh the ListView
                userCommentsLV.getItems().setAll(comments);

                String updateData = "UPDATE comment SET comment ='" + newComment + "' WHERE username ='" + LoginController.currentUsername +"' AND comment ='" + selectedComment +"'";
                connect = Database.connectDB();

                try {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            });
        }
    }

    @FXML
    void deleteComment(ActionEvent event) {
        String selectedComment = userCommentsLV.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Comment");
        alert.setContentText("Are you sure you want to delete this comment?");

        String deleteData = "DELETE FROM comment WHERE username ='" + LoginController.currentUsername + "' AND comment ='" + selectedComment + "'";
        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(deleteData);
            prepare.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        comments.remove(selectedComment);
        userCommentsLV.getItems().setAll(comments);


        
    }



    

}
