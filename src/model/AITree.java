package model;

import controller.MainController;

import java.util.List;

public class AITree {

    MainController mainController;

    public static AINode generateTree(Move move, MainController mainController, int depth){
        Move rootMove = new Move(move);
        AINode root = new AINode(rootMove);
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

    public static int getValue(AINode root, PlayerState player, int depth){
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
                int value = getValue(node, player, depth);
                sum += value;
            }
            int ownValue = 0;

           List<PlayerState> list = root.getCurrentMove().getPlayers();

           int actDepth = root.getDepth();;

            for(PlayerState tplayer : list){
                if(player.getPlayer().equals(tplayer.getPlayer())){
                    ownValue += (depth - actDepth) * 10 * tplayer.getVictoryPoints();
                }
            }
            return ownValue + sum;
        }
    }
}
