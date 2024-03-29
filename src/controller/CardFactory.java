package controller;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.Colour.*;
import static model.PersonType.*;

/**
 * Zum Erstellen von karten.
 */
public class CardFactory {


    /**
     *
     * generate blue ships - 10pcs
     * @return CardStack obj
     */

    public List<Card> generateBlueShips () {

        List<Card> cards = new ArrayList<>();

        //blue, 1 coin, 1 force. 3pcs
        for (int i = 0; i < 3; i++) {
            cards.add(new Ship(BLUE, 1, 1));

        }


        cards.add(new Ship(BLUE, 2, 1));  // 1pc
        cards.add(new Ship(BLUE, 2, 2));  // 2pcs
        cards.add(new Ship(BLUE, 2, 2));
        cards.add(new Ship(BLUE, 3, 2));// 1pc

        cards.add(new Ship(Colour.BLUE, 3, 5)); // 2pcs
        cards.add(new Ship(Colour.BLUE, 3, 5));
        cards.add(new Ship(Colour.BLUE, 4, 5)); // 1pc


        return cards;
    }
    /**
     *
     * generate red ships - 10pcs
     * @return CardStack obj
     */

    public List<Card> generateRedShips () {

        List<Card> cards = new ArrayList<>();


        //red ships with force 1, 1 coin, 3pcs
        //red ships with force 3, 2 coin, 3pcs

        for (int i = 0; i < 3; i++) {
            cards.add(new Ship(Colour.RED, 1, 1));
            cards.add(new Ship(Colour.RED, 2, 3));
        }

        cards.add(new Ship(Colour.RED, 3, 6)); // 2pcs
        cards.add(new Ship(Colour.RED, 3, 6));
        cards.add(new Ship(Colour.RED, 3, 100));// with skull, 1pc
        cards.add(new Ship(Colour.RED, 4, 100));// with skull, 1pc

        return cards;
    }

    /**
     *
     * generate black ships - 10pcs
     * @return CardStack obj
     */

    public List<Card> generateBlackShips () {

        List<Card> cards = new ArrayList<>();


//black ships with force 2,coin 1, 3pcs
 //black ships with force 4,coin 2, 3pcs
        for (int i = 0; i < 3; i++) {

            cards.add(new Ship(Colour.BLACK, 1, 2));
            cards.add(new Ship(Colour.BLACK, 2, 4));
        }

        cards.add(new Ship(Colour.BLACK, 3, 7)); // 2pcs
        cards.add(new Ship(Colour.BLACK, 3, 7));
        cards.add(new Ship(Colour.BLACK, 3, 100)); // with skull, 1pc
        cards.add(new Ship(Colour.BLACK, 4, 100)); // with skull, 1pc

        return cards;
    }
    /**
     *
     * generate yellow ships - 10pcs
     * @return CardStack obj
     */

    public List<Card> generateYellowShips (){

        List<Card> cards = new ArrayList<>();

// 1 force, 1 coin, 3pcs
        for (int i = 0; i < 3; i++) {
            cards.add(new Ship(YELLOW, 1, 1));

        }

// 2 force, 2 coin, 2pcs,  4 force, 3 coin, 2pcs

        for (int i = 0; i < 2; i++) {
            cards.add(new Ship(YELLOW, 2, 2));
            cards.add(new Ship(YELLOW, 3, 4));
        }
            cards.add(new Ship(YELLOW, 2, 1));
            cards.add(new Ship(YELLOW, 3, 2));
            cards.add(new Ship(YELLOW, 4, 4));

        return cards;
    }

    /**
     *
     * generate yellow ships - 10pcs
     * @return CardStack obj
     */

    public List<Card> generateGreenShips(){

        List<Card> cards = new ArrayList<>();

// 1 force, 1 coin, 3pcs
        for (int i = 0; i < 3; i++) {
            cards.add(new Ship(GREEN, 1, 1));

        }

// 3 force, 2 coin, 2pcs,  5 force, 3 coin, 2pcs

        for (int i = 0; i < 2; i++) {
            cards.add(new Ship(GREEN, 2, 3));
            cards.add(new Ship(GREEN, 3, 5));
        }
        cards.add(new Ship(GREEN, 2, 1)); // 1pc
        cards.add(new Ship(GREEN, 3, 3)); // 1pc
        cards.add(new Ship(GREEN, 4, 5)); // 1pc

        return cards;

    }

    /**
     * generate admiral - 6 cards
     * @return CardStack obj
     */
    public List<Card> generateAdmiral(){

        List<Card> cards = new ArrayList<>();

   // 1pc with 5 coins
        Person person1 = new Person();
        person1.setMetaData(ADMIRAL.name(),null, ADMIRAL);
        person1.setValues(5,1,0);
        cards.add(person1);
 // 3pc with 7 coins

        for (int i = 0; i < 3; i++) {
            Person person2 = new Person();
            person2.setMetaData(ADMIRAL.name(),null, ADMIRAL);
            person2.setValues(7,2,0);

            cards.add(person2);
        }
// 2pc with 9 coins
        for (int i = 0; i < 2; i++) {
        Person person3 = new Person();
        person3.setMetaData(ADMIRAL.name(),null, ADMIRAL);
        person3.setValues(9,3,0);
        cards.add(person3);

        }

        return cards;

    }
    /**
     * generate trader - 10 cards
     * @return CardStack obj
     */

    public List<Card> generateTrader(){

        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 2; i++) {

            Person trader = new Person();
            trader.setMetaData(TRADER.name(),GREEN,TRADER);  //2pcs green, 3 coins
            trader.setValues(3,1,0);
            cards.add(trader);

            Person black = new Person();
            black.setMetaData(TRADER.name(),BLACK,TRADER); //2pcs black, 3 coins
            black.setValues(3,1,0);
            cards.add(black);

            Person red = new Person();
            red.setMetaData(TRADER.name(),RED,TRADER); //2pcs red, 3 coins
            red.setValues(3,1,0);
            cards.add(red);
        }



        Person trader1 = new Person();
        trader1.setMetaData(TRADER.name(),BLUE,TRADER); //1pc blue, 3 coins
        cards.add(trader1);

        Person trader2 = new Person();
        trader2.setMetaData(TRADER.name(), YELLOW,TRADER); //1pc yellow, 3 coins
        cards.add(trader2);

        Person trader3 = new Person();
        trader3.setValues(5,2,0); // generate trader3 with 5 coins, 2 victory points
        trader3.setMetaData(TRADER.name(), YELLOW,TRADER);  //1pcs blue, 5 coins
        cards.add(trader3);

        Person trader4 = new Person();
        trader4.setMetaData(TRADER.name(),BLUE,TRADER); //1pcs blue, 5 coins
        cards.add(trader4);

        return cards;

    }
    /**
     * generate Settler, Captain - 5 cards each
     * @return CardStack obj
     */

    public List<Card> generateSettlerCaptain() {

        List<Card> cards = new ArrayList<>();

 // add both with 5 cards
        for(int i=0; i<5; i++){
// add SETTLERS
            Person settler = new Person();
            settler.setValues(4,1,0);
            settler.setMetaData(SETTLER.name(),null,SETTLER);
            cards.add(settler);
// add Captain
            Person captain = new Person();
            captain.setValues(4,1,0);
            captain.setMetaData(CAPTAIN.name(), null,CAPTAIN);
            cards.add(captain);
        }

        return cards;
    }

    /**
     * generate Priest
     * @return CardStack obj
     */

    public List<Card> generatePriest() {

        List<Card> cards = new ArrayList<>();

 // add Priest - 5pcs

        for(int i=0; i<5; i++){

            Person priest = new Person();
            priest.setValues(4,1,0);
            priest.setMetaData(PRIEST.name(), null,PRIEST);

            cards.add(priest);
        }

        return cards;
    }

    /**
     *
     * generate JackOfAllTrader
     * @return CardStack obj
     */
    public List<Card> generateJackOfAllTrader() {

        List<Card> cards = new ArrayList<>();

// add JackOfAllTrader  - 3 cards

        for(int i=0; i<3; i++){
            Person jack = new Person();
            jack.setValues(6,1,0);
            jack.setMetaData(PersonType.JACK_OF_ALL_TRADES.name(), null,PersonType.JACK_OF_ALL_TRADES);
            cards.add(jack);
        }

        return cards;

    }

    /**
     *
     * generate pirate
     * @return  CardStack obj
     */
    public List<Card> generatePirate() {

        List<Card> cards = new ArrayList<>();

// add Pirates - 3 cards

        for(int i=0; i<2; i++){
            Person pirate = new Person();
            pirate.setMetaData(PIRATE.name(), null, PIRATE);
            pirate.setValues(7,2,2);
            cards.add(pirate);
        }

        Person pirate2 = new Person();
        pirate2.setMetaData(PIRATE.name(), null, PIRATE);
        pirate2.setValues(9,3,2);
        cards.add(pirate2);

        return cards;
    }

    /**
     *
     * generate jester
     * @return  CardStack obj
     */
    public List<Card> generateJester() {

        List<Card> cards = new ArrayList<>();

        Person jester = new Person();
// add Jester - 5 cards
        jester.setMetaData(JESTER.name(), null,JESTER);
        jester.setValues(5,1,0);
        cards.add(jester);

        for(int i=0; i<3; i++){

            Person jester1 = new Person();
            jester1.setMetaData(JESTER.name(), null,JESTER);
            jester1.setValues(7,2,0);
            cards.add(jester1);

        }
        Person jester2 = new Person();
        jester2.setMetaData(JESTER.name(), null,JESTER);
        jester2.setValues(9,3,0);
        cards.add(jester2);


        return cards;
    }
    /**
     *
     * generate sailor
     * @return  CardStack obj
     */
    public List<Card> generateSailor() {

        List<Card> cards = new ArrayList<>();
// add Sailor - 10 cards

 // 7pcs - 3 coins, 1 victoryPoint
        for(int i=0; i<7; i++){
            Person sailor = new Person();
            sailor.setMetaData(SAILOR.name(), null, SAILOR);
            sailor.setValues(3,1,1);
            cards.add(sailor);
        }
// 2pcs - 5 coins, 2 victoryPoint
        for(int i=0; i<2; i++){
            Person sailor2 = new Person();
            sailor2.setMetaData(SAILOR.name(), null, SAILOR);
            sailor2.setValues(5,2,1);
            cards.add(sailor2);
        }



 // 1pc - 7 coins, 3 victoryPoint

        Person sailor3 = new Person();
        sailor3.setMetaData(SAILOR.name(), null, SAILOR);
        sailor3.setValues(7,3,1);
        cards.add(sailor3);


        return cards;
    }

    /**
     *
     * generate mademoiselles
     * @return  CardStack obj
     */
    public List<Card> generateMademoiselles() {

        List<Card> cards = new ArrayList<>();

// add	Mademoiselles - 4 cards

        for(int i=0; i<2; i++){
            Person mademoiselles = new Person();
            mademoiselles.setMetaData(PersonType.MADEMOISELLE.name(), null,PersonType.MADEMOISELLE);
            mademoiselles.setValues(7,2,0);
            cards.add(mademoiselles);
        }

        for(int i=0; i<2; i++) {
            Person mademoiselles2 = new Person();
            mademoiselles2.setMetaData(PersonType.MADEMOISELLE.name(), null, PersonType.MADEMOISELLE);
            mademoiselles2.setValues(9, 3, 0);
            cards.add(mademoiselles2);
        }

        return cards;
    }

    /**
     *
     * generate governor
     * @return  CardStack obj
     */
    public List<Card> generateGovernor() {

        List<Card> cards = new ArrayList<>();

        // add Governors 4 cards

        for(int i=0; i<4; i++){

            Person governor = new Person();
            governor.setMetaData(PersonType.GOVERNOR.name(), null,PersonType.GOVERNOR);
            governor.setValues(8,0,0);
            cards.add(governor);
        }
        return cards;
    }

    /**
     *
     * generate taxIncrease
     * @return  CardStack obj
     */
    public List<Card> generateTaxIncrease() {

        List<Card> cards = new ArrayList<>();

//add taxIncrease - 4 cards

        cards.add(new TaxIncrease(false));
        cards.add(new TaxIncrease(false));

        cards.add(new TaxIncrease(true));
        cards.add(new TaxIncrease(true));

        return cards;
    }

        /**
         *
         * generate Expedition
         * @return  CardStack obj
         */
    public List<Card> generateExpedition(boolean withSpecial) {

        List<Card> cards = new ArrayList<>();

        Map<PersonType, Integer> requirements = new HashMap<>();

 // add expedition with 2 anchor
        requirements.put(CAPTAIN, 2);
        requirements.put(PRIEST, 0);
        requirements.put(SETTLER, 0);
        requirements.put(JACK_OF_ALL_TRADES, 0);
        Expedition anchor = new Expedition(requirements,2,4);
        cards.add(anchor);

// add expedition with 2 houses
        Map<PersonType, Integer> requirements1 = new HashMap<>();
        requirements1.put(SETTLER, 2);
        requirements1.put(CAPTAIN, 0);
        requirements1.put(PRIEST, 0);
        requirements1.put(JACK_OF_ALL_TRADES, 0);
        Expedition house = new Expedition(requirements1,2,4);
        cards.add(house);

 // add expedition with 2 houses
        Map<PersonType, Integer> requirements2 = new HashMap<>();
        requirements2.put(PRIEST, 2);
        requirements2.put(SETTLER, 0);
        requirements2.put(CAPTAIN, 0);
        requirements2.put(JACK_OF_ALL_TRADES, 0);
        Expedition cross = new Expedition(requirements2,2,4);
        cards.add(cross);

// add expedition with 2 crosses + 1 house
        Map<PersonType, Integer> requirements3 = new HashMap<>();
        requirements3.put(PRIEST, 2);
        requirements3.put(SETTLER, 1);
        requirements3.put(CAPTAIN, 0);
        requirements3.put(JACK_OF_ALL_TRADES, 0);
        Expedition crossHouse = new Expedition(requirements3,3,6);
        cards.add(crossHouse);

// add expedition with 2 anchors + 1 house
        Map<PersonType, Integer> requirements4 = new HashMap<>();
        requirements4.put(CAPTAIN, 2);
        requirements4.put(SETTLER, 1);
        requirements4.put(PRIEST, 0);
        requirements4.put(JACK_OF_ALL_TRADES, 0);
        Expedition anchorHouse = new Expedition(requirements4,3,6);
        cards.add(anchorHouse);

// add expedition with 2 anchors + 1 house

        if(withSpecial){
            Map<PersonType, Integer> requirements5 = new HashMap<>();
            requirements5.put(CAPTAIN, 1);
            requirements5.put(SETTLER, 1);
            requirements5.put(PRIEST, 1);
            requirements5.put(JACK_OF_ALL_TRADES, 0);
            Expedition special = new Expedition(requirements5,3,5);
            cards.add(special);
        }

        return cards;
    }

    /**
     *
     *
      * @return a CardStack with all 120 cards
     */
    public static CardStack newCardsWithoutSpecial(){

            CardStack stack = new CardStack();
            List<Card> cards = stack.getCards();

            CardFactory newList = new CardFactory();

            cards.addAll(newList.generateBlueShips());
            cards.addAll(newList.generateRedShips());
            cards.addAll(newList.generateBlackShips());
            cards.addAll(newList.generateYellowShips());
            cards.addAll(newList.generateGreenShips());

            cards.addAll(newList.generateAdmiral());
            cards.addAll(newList.generateGovernor());
            cards.addAll(newList.generateTrader());
            cards.addAll(newList.generateJackOfAllTrader());
            cards.addAll(newList.generateJester());
            cards.addAll(newList.generateMademoiselles());
            cards.addAll(newList.generatePriest());
            cards.addAll(newList.generatePirate());
            cards.addAll(newList.generateSailor());
            cards.addAll(newList.generateSettlerCaptain());
            cards.addAll(newList.generateTaxIncrease());
            cards.addAll(newList.generateExpedition(false));


        return stack;

    }

    /**
     * Erstellt einen Kartenstapel mit 6 Expeditionskarten.
     * @return Gibt einen Kartenstapel zurück.
     */
    public static CardStack newCardsWithSpecial(){

        CardStack stack = new CardStack();
        List<Card> cards = stack.getCards();

        CardFactory newList = new CardFactory();

        cards.addAll(newList.generateBlueShips());
        cards.addAll(newList.generateRedShips());
        cards.addAll(newList.generateBlackShips());
        cards.addAll(newList.generateYellowShips());
        cards.addAll(newList.generateGreenShips());

        cards.addAll(newList.generateAdmiral());
        cards.addAll(newList.generateGovernor());
        cards.addAll(newList.generateTrader());
        cards.addAll(newList.generateJackOfAllTrader());
        cards.addAll(newList.generateJester());
        cards.addAll(newList.generateMademoiselles());
        cards.addAll(newList.generatePriest());
        cards.addAll(newList.generatePirate());
        cards.addAll(newList.generateSailor());
        cards.addAll(newList.generateSettlerCaptain());
        cards.addAll(newList.generateTaxIncrease());
        cards.addAll(newList.generateExpedition(true));

        return stack;

    }

}
