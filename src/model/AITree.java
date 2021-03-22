package model;

import controller.MainController;

import java.util.List;

public class AITree {

    MainController mainController;

    public static AINode generateTree(Move move, MainController mainController, int depth){
        AINode root = new AINode(move);
        generateNodes(root,mainController, depth);

        return root;
    }

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

    public static Action getBestAction(AINode root, PlayerState player){

        List<AINode> children = root.getChildren();

        int maxValue = -1;
        AINode maxNode = null;

        for(AINode node : children){
            int value = getValue(node, player);

            if(value > maxValue){
                maxValue = value;
                maxNode = node;
            }
        }

        return maxNode.getCurrentMove().getAction();
    }

    public static int getValue(AINode root, PlayerState player){
        List<AINode> children = root.getChildren();

        if(root.isLeaf()){
            List<PlayerState> players = root.getCurrentMove().getPlayers();

            for(PlayerState tplayer : players){
                if(player.getPlayer().equals(tplayer.getPlayer())){
                    return tplayer.getVictoryPoints();
                }
            }

            return 0;
        }

        else {

            int sum = 0;

            for (AINode node : children) {
                int value = getValue(node, player);
                sum += value;
            }

            return sum;
        }
    }
}
