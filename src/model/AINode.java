package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Knoten einer KI.
 */
public class AINode {
    Move currentMove;
    AINode parent;
    Map<Action,AINode> branches;

    /**
     * Konstruktor.
     * @param move Bekommt einen Zug übergeben.
     */
    public AINode(Move move){
        this.currentMove = move;
        this.branches = new HashMap<Action, AINode>();

    }

    /**
     *
     * @return Gibt zurück ob der Knoten ein Blatt ist (true) oder nicht (false).
     */
    public boolean isLeaf(){
        return this.branches.isEmpty();
    }

    /**
     *
     * @return Gibt den aktuellen Zug zurück.
     */
    public Move getCurrentMove(){
        return this.currentMove;
    }

    /**
     *
     * @return Gibt die Tiefe zurück.
     */
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

    /**
     * Fügt dem Knoten ein Kind hinzu.
     * @param action Bekommt eine Aktion übergeben.
     * @param move Bekommt einen Zug übergeben.
     */
    public void addChild(Action action, Move move){
        this.branches.put(action, new AINode(move));
    }

    /**
     *
     * @return Gibt eine Liste aller Kinder des Knoten wieder.
     */
    public List<AINode> getChildren(){
        return new ArrayList<>(branches.values());
    }

    /**
     *
     * @return Gibt eine Liste aller Aktionen des Knoten wieder.
     */
    public List<Action> getActions(){
        return new ArrayList<>(branches.keySet());
    }

    /**
     *
     * @param action Bekommt eine Aktion übergeben.
     * @return Gibt das entsprechende Kind des Knoten zurück.
     */
    public AINode getChild(Action action){
        return this.branches.get(action);
    }
}
