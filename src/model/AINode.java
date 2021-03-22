package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AINode {
    Move currentMove;
    AINode parent;
    Map<Action,AINode> branches;

    public AINode(Move move){
        this.currentMove = move;
        this.branches = new HashMap<Action, AINode>();

    }

    public boolean isLeaf(){
        return this.branches.isEmpty();
    }

    public Move getCurrentMove(){
        return this.currentMove;
    }

    public int getDepth(){

        if(this.isLeaf()){
            return 0;
        }
        else{
            List<AINode> children = this.getChildren();

            int maxDepth = -1;

            for(AINode child : children){
                int childDepth = child.getDepth();
                if(childDepth > maxDepth){
                    maxDepth = childDepth;
                }
            }

            return 1 + maxDepth;
        }
    }

    public void addChild(Action action, Move move){
        this.branches.put(action, new AINode(move));
    }

    public List<AINode> getChildren(){
        return new ArrayList<>(branches.values());
    }

    public List<Action> getActions(){
        return new ArrayList<>(branches.keySet());
    }

    public AINode getChild(Action action){
        return this.branches.get(action);
    }
}
