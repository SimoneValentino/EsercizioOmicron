/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.omicron.esercizioomicron;

import java.util.List;

/**
 *
 * @author Valeinix
 */
public class MenuContent {

    private String version;
    private List<MenuNode> nodes;
    private int maxDepth ;
    public MenuContent() {
       
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "MenuContent{" + "version=" + version + ", nodes=" + nodes + '}' + "\n";
    }

    public List<MenuNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<MenuNode> nodes) {
        this.nodes = nodes;
    }
    
    public void setMaxDepth(int maxDepth) {
    this.maxDepth=maxDepth;
    
    }

    public int getMaxDepth() {
        return maxDepth;
    }
    

    public static int depthCount(List<MenuNode> nodes, int profondita) {
        int j = profondita;
        for (MenuNode node : nodes) {

            if (node.getNodes() != null) {
                int temp = depthCount(node.getNodes(), profondita + 1);
                j = j > temp ? j : temp;
            }
        }
        return j;
    }



}
