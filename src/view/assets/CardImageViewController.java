package view.assets;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;

import java.util.Map;
import java.util.Set;

public class CardImageViewController extends ImageView {

    private Image cardImage;

    public CardImageViewController(Card card){
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
            fileName = ""+((Ship) card).getCoins();
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
            cardImage = new Image("view.resources." +fileName + ".png");
        }
        else
            System.out.println("No Card");

    }

    public Image getCardImage() {
        return cardImage;
    }

    public void setCardImage(Image cardImage) {
        this.cardImage = cardImage;
    }
}
