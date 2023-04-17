package com.example.javaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ItemsController {
    @FXML
    private VBox choosenCarCard;

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;


    @FXML
    private void click(MouseEvent mouseEvent)
    {
        myListener.onClickListener(voiture);
    }

    private Voiture voiture;
    private  MyListener myListener;

    public void setData(Voiture voiture, MyListener myListener)
    {
        this.voiture = voiture;
        this.myListener = myListener;
        nameLabel.setText(voiture.getMarque());
        priceLabel.setText(voiture.getPrixParjr() + Main.CURRENCY);
        Image image = new Image(voiture.getImageV());
        img.setImage(image);

    }
    public void setFilter(Voiture voiture)
    {
        this.voiture = voiture;
        nameLabel.setText(voiture.getMarque());
        priceLabel.setText(voiture.getPrixParjr() + Main.CURRENCY);
        Image image = new Image(voiture.getImageV());
        img.setImage(image);

    }
}
