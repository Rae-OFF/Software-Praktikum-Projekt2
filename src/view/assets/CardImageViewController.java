package view.assets;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.*;

import java.util.Set;

public class CardImageViewController extends StackPane {

    private ImageView cardImage;

    public CardImageViewController(Card card){
        super();
        //this.setAlignment(Pos.TOP_LEFT);
        //Rectangle rect = new Rectangle(50, 50, Color.RED);
        //getChildren().add(rect);
        String fileName = "";
        //Personenkarten
        if(card instanceof Person){
            fileName = ((Person) card).getName() + ((Person) card).getPrice();

        }
        //Expeditionskarten
        else if(card instanceof Expedition) {
            Set<PersonType> requs = ((Expedition) card).getRequirements().keySet();
            fileName = "EXPEDITION_";
            for(PersonType person : requs){
                if(person.equals(PersonType.CAPTAIN)){
                    fileName = fileName + "C";
                } else if(person.equals(PersonType.PRIEST)){
                    fileName = fileName + "P";
                } else if(person.equals(PersonType.SETTLER))
                    fileName = fileName + "S";
            }
        }
        //Schiffskarten
        else if(card instanceof Ship){
            fileName = "SHIP_"+((Ship) card).getColour().toString()+((Ship) card).getCoins();
        }
        //Steuererhöhungskarten
        else if(card instanceof TaxIncrease){
            if(((TaxIncrease) card).isTypeSwords()){
                fileName = "TAX_INCREASE_S";
            } else {
                fileName = "TAX_INCREASE_VP";
            }

        }
        if(!fileName.equals("")){
            System.out.println(fileName);
            cardImage = new ImageView("view/cards/" +fileName + ".png");
            cardImage.setFitWidth(80);
            cardImage.setFitHeight(120);
            getChildren().add(cardImage);
        }
        else {

            cardImage = new ImageView("view/cards/EXPEDITION-CC.png");
            cardImage.setVisible(false);
            cardImage.setFitWidth(80);
            cardImage.setFitHeight(120);
            getChildren().add(cardImage);
            System.out.println("No Card");
        }

    }

    public ImageView getCardImage() {
        return cardImage;
    }

    public void setCardImage(ImageView cardImage) {
        this.cardImage = cardImage;
    }
}
