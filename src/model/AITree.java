package model;

import controller.MainController;

import java.util.List;

/**
 * Baumstruktur für die AI.
 */
public class AITree {

    MainController mainController;

    /**
     * Erstellt einen Baum.
     * @param move Bekommt einen Zug übergeben.
     * @param mainController Bekommt den MainController übergeben.
     * @param depth Bekommt die Tiefe zurück gegeben.
     * @return Gibt die Wurzel zurück.
     */
    public static AINode generateTree(Move move, MainController mainController, int depth){
        Move rootMove = new Move(move);
        AINode root = new AINode(rootMove);
        generateNodes(root,mainController, depth);

        return root;
    }

    /**
     * Erstellt einen Knoten des Baumes.
     * @param node Bekommt einen Knoten übergeben.
     * @param mainController Bekommt den MainController übergeben.
     * @param depth Bekommt die Tiefe übergeben.
     */
    public static void generateNodes(AINode node, MainController mainController, int depth){

        List<Action> actions = mainController.getGameController().getPossibleActions(node.getCurrentMove());

        if(depth > 0){
            for(Action action : actions){
                Move nextMove = mainController.getGameController().generateMove(node.getCurrentMove(), action);
                node.addChild(action, nextMove);

                generateNodes(node.getChild(action), mainController, depth-1);
            }
        }
    }

    /**
     * Sucht die beste Aktion heraus.
     * @param root Bekommt den Wurzelknoten übergeben.
     * @param player Bekommt einen Spielerzustand übergeben.
     * @return Gibt den besten möglichen Zug zurück.
     */
    public static Action getBestAction(AINode root, PlayerState player){

        List<AINode> children = root.getChildren();

        int maxValue = -1;
        AINode maxNode = null;

        if(children.size() > 1){
            AINode removeNode = null;
            for(AINode child : children){
                if(child.getCurrentMove().getAction().getActionType().equals(ActionType.SKIP)){
                    removeNode = child;
                }
            }

            if(removeNode != null){
                children.remove(removeNode);
            }
        }

        for(AINode node : children){
            int value = getValue(node, player, root.getDepth());

            if(value > maxValue){
                maxValue = value;
                maxNode = node;
            }
        }

        return maxNode.getCurrentMove().getAction();
    }

    /**
     * Berechnet den Wert eines Knoten.
     * @param root Bekommt die Wurzel übergeben.
     * @param player Bekommt einen Spielerzustand übergeben.
     * @param depth Bekommt die Tiefe übergeben.
     * @return Gibt den Wert zurück.
     */
    public static int getValue(AINode root, PlayerState player, int depth){
        List<AINode> children = root.getChildren();
        int secondBest=0;
        int currentPlayervalue=0;
        if(root.isLeaf()){
            List<PlayerState> players = root.getCurrentMove().getPlayers();

            for(PlayerState tplayer : players){
                int evaluatePlayer = evaluateMove(tplayer);
                if(player.getPlayer().equals(tplayer.getPlayer())){
                    currentPlayervalue=evaluatePlayer;
                    //return tplayer.getVictoryPoints();
                }else if(evaluatePlayer>secondBest){
                    secondBest=evaluatePlayer;
                }
            }

            return currentPlayervalue-secondBest;
        }

        else {

            int sum = 0;

            for (AINode node : children) {
                int value = getValue(node, player, depth);
                sum += value;
            }
            int ownValue = 0;

           List<PlayerState> list = root.getCurrentMove().getPlayers();

           int actDepth = root.getDepth();

            for(PlayerState tplayer : list){
                if(player.getPlayer().equals(tplayer.getPlayer())){
                    ownValue += (depth - actDepth) * 10 * tplayer.getVictoryPoints();
                }
            }
            return ownValue + sum;
        }
    }

    /**
     *
     * @return
     */
    public static int evaluateMove(PlayerState player){
        int coins = player.getCoins().getSize();
        int victoryPoins = player.getVictoryPoints();
        int specialCards=0;
        for(Card card:player.getCards().getCards()){
            if(card instanceof Person){
                switch(((Person) card).getPersonType()){
                    case PIRATE:
                    case SAILOR:
                    case JESTER:
                        specialCards+=1;
                        break;
                    case MADEMOISELLE:
                    case GOVERNOR:
                    case JACK_OF_ALL_TRADES:
                        specialCards+=2;
                        break;
                }
            }
        }

        int value = (coins)+victoryPoins+specialCards;
        return value;
    }
}
