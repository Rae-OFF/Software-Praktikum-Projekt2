package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;

import static model.Colour.*;
import static model.PersonType.*;

public class CardFactory {

    private List<Card> cards = new ArrayList<>();

    /**
     *
     * generate blue ships - 10pcs
     * @return CardStack obj
     */

    public List<Card> generateBlueShips () {


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

        CardStack stack = new CardStack();
        List<Card> cards = stack.getCards();

        Person person = new Person();
        person.setMetaData(ADMIRAL.name(),null, ADMIRAL);

   // 1pc with 5 coins
        person.setValues(5,1,0);
        cards.add(person);
 // 3pc with 7 coins
        person.setValues(7,2,0);
        for (int i = 0; i < 3; i++) {
            cards.add(person); cards.add(person); cards.add(person);
        }


// 2pc with 9 coins
        person.setValues(9,3,0);
        cards.add(person); cards.add(person);

        return cards;

    }
    /**
     * generate trader - 10 cards
     * @return CardStack obj
     */

    public List<Card> generateTrader(){

        CardStack stack = new CardStack();
        List<Card> cards = stack.getCards();

        Person trader = new Person();

        trader.setValues(3,1,0);

        for (int i = 0; i < 2; i++) {

            trader.setMetaData(TRADER.name(),GREEN,TRADER);  //2pcs green, 3 coins
            cards.add(trader);

            trader.setMetaData(TRADER.name(),BLACK,TRADER); //2pcs black, 3 coins
            cards.add(trader);

            trader.setMetaData(TRADER.name(),RED,TRADER); //2pcs red, 3 coins
            cards.add(trader); cards.add(trader);

        }

        trader.setMetaData(TRADER.name(),BLUE,TRADER); //1pc blue, 3 coins
        cards.add(trader);

        trader.setMetaData(TRADER.name(), YELLOW,TRADER); //1pc yellow, 3 coins
        cards.add(trader);

        trader.setValues(5,2,0); // generate trader with 5 coins, 2 victory points
        trader.setMetaData(TRADER.name(), YELLOW,TRADER);  //1pcs blue, 5 coins
        cards.add(trader);

        trader.setMetaData(TRADER.name(),BLUE,TRADER); //1pcs blue, 5 coins
        cards.add(trader);

        return cards;

    }
    /**
     * generate Settler, Captain - 5 cards each
     * @return CardStack obj
     */

    public List<Card> generateSettlerCaptain() {

        CardStack stack = new CardStack();
        List<Card> cards = stack.getCards();
// add SETTLERS
        Person settler = new Person();

        settler.setValues(4,1,0);
        settler.setMetaData(SETTLER.name(),null,SETTLER);

// add Captain
        Person captain = new Person();

        captain.setValues(4,1,0);
        captain.setMetaData(CAPTAIN.name(), null,CAPTAIN);

 // add both with 5 cards
        for(int i=0; i<5; i++){
            cards.add(captain);
            cards.add(settler);
        }

        return cards;
    }

    /**
     * generate Priest
     * @return CardStack obj
     */

    public List<Card> generatePriest() {

 // add Priest - 5pcs
        Person priest = new Person();

        priest.setValues(4,1,0);
        priest.setMetaData(PRIEST.name(), null,PRIEST);

        for(int i=0; i<5; i++){
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


        Person jack = new Person();

// add JackOfAllTrader  - 3 cards
        jack.setValues(6,1,0);
        jack.setMetaData(PersonType.JACK_OF_ALL_TRADES.name(), null,PersonType.JACK_OF_ALL_TRADES);
        cards.add(jack);
        cards.add(jack);
        cards.add(jack);

        return cards;

    }

    /**
     *
     * generate pirate
     * @return  CardStack obj
     */
    public List<Card> generatePirate() {

// add Pirates - 3 cards
        Person pirate = new Person();

        pirate.setMetaData(PIRATE.name(), null, PIRATE);


        pirate.setValues(7,2,2);
        cards.add(pirate);
        cards.add(pirate);

        pirate.setValues(9,3,2);
        cards.add(pirate);

        return cards;
    }

    /**
     *
     * generate jester
     * @return  CardStack obj
     */
    public List<Card> generateJester() {

        Person jester = new Person();
// add Jester - 5 cards
        jester.setMetaData(JESTER.name(), null,JESTER);
        jester.setValues(5,1,0);
        cards.add(jester);

        jester.setValues(7,2,0);
        cards.add(jester);
        cards.add(jester);
        cards.add(jester);

        jester.setValues(9,3,0);
        cards.add(jester);


        return cards;
    }
    /**
     *
     * generate sailor
     * @return  CardStack obj
     */
    public List<Card> generateSailor() {

        CardStack stack = new CardStack();
        List<Card> cards = stack.getCards();
// add Sailor - 10 cards (1 LOST)
        Person sailor = new Person();

 // 7pcs - 3 coins, 1 victoryPoint
        sailor.setMetaData(SAILOR.name(), null, SAILOR);
        sailor.setValues(3,1,1);
        for(int i=0; i<7; i++){

            cards.add(sailor);
        }

// 2pcs - 5 coins, 2 victoryPoint
        sailor.setValues(5,2,1);
        cards.add(sailor);cards.add(sailor);

 // 1pc - 7 coins, 3 victoryPoint
        sailor.setValues(7,3,1);
        cards.add(sailor);


        return cards;
    }

    /**
     *
     * generate mademoiselles
     * @return  CardStack obj
     */
    public List<Card> generateMademoiselles() {

        CardStack stack = new CardStack();
        List<Card> cards = stack.getCards();
        Person mademoiselles = new Person();
// add	Mademoiselles - 4 cards
        mademoiselles.setMetaData(PersonType.MADEMOISELLE.name(), null,PersonType.MADEMOISELLE);
        mademoiselles.setValues(7,2,0);
        cards.add(mademoiselles);
        cards.add(mademoiselles);
        mademoiselles.setValues(9,3,0);
        cards.add(mademoiselles);
        cards.add(mademoiselles);

        return cards;
    }

    /**
     *
     * generate governor
     * @return  CardStack obj
     */
    public List<Card> generateGovernor() {

        CardStack stack = new CardStack();
        List<Card> cards = stack.getCards();

// add Governors 4 cards

        Person governor = new Person();

        governor.setMetaData(PersonType.GOVERNOR.name(), null,PersonType.GOVERNOR);
        governor.setValues(8,0,0);
        for(int i=0; i<4; i++){

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

        CardStack stack = new CardStack();
        List<Card> cards = stack.getCards();

//add taxIncrease - 4 cards

        TaxIncrease taxIncrease = new TaxIncrease(false);
        cards.add(taxIncrease);
        cards.add(taxIncrease);

        taxIncrease.setTypeSwords(true);
        cards.add(taxIncrease);
        cards.add(taxIncrease);

        return cards;
    }


        /**
         *
         * generate Expedition
         * @return  CardStack obj
         */
   /*  public List<Card> generateExpedition() {


        Expedition expedition = new Expedition(PRIEST,2, 4);
        cards.add(expedition);
        return cards;
    }
 */

        public CardStack newCards(){

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
            cards.addAll(newList.generateJackOfAllTrader());
            cards.addAll(newList.generateJester());
            cards.addAll(newList.generateMademoiselles());
            cards.addAll(newList.generatePriest());
            cards.addAll(newList.generatePirate());
            cards.addAll(newList.generateSailor());
            cards.addAll(newList.generateSettlerCaptain());
            cards.addAll(newList.generateTaxIncrease());



        return stack;

    }



}
