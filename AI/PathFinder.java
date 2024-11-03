package AI;

import java.util.ArrayList;

import Main.MainPanel;

public class PathFinder {
    MainPanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(MainPanel gp) {
        this.gp = gp;
        initiateNodes();
    }

    public void initiateNodes() {
        node = new Node[gp.MAXWORLDCOLUMNS][gp.MAXWORLDROWS];

        for(int col = 0; col< gp.MAXWORLDCOLUMNS; col++) {
            for(int row = 0; row < gp.MAXWORLDROWS; row++) {
                node[col][row] = new Node(col, row);
            }
        }
    }

    public void resetNodes() {
        for(int col = 0; col< gp.MAXWORLDCOLUMNS; col++) {
            for(int row = 0; row < gp.MAXWORLDROWS; row++) {
                node[col][row].open = false;
                node[col][row].checked = false;
                node[col][row].solid = false;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        for(int col = 0; col< gp.MAXWORLDCOLUMNS; col++) {
            for(int row = 0; row < gp.MAXWORLDROWS; row++) {
                // check tiles
                int tileNum = gp.tileMan.mapTileNum[gp.currentMap][col][row];
                if(gp.tileMan.tile[tileNum].collision) {
                    node[col][row].solid = true;
                }
                // set cost
                getCost(node[col][row]);
            }
        }
    }

    public void getCost(Node node) {

        // G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        // H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        // F cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while (!goalReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            // check the current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // open the adjacent nodes
            if (row - 1 >= 0) openNode(node[col][row - 1]); // up
            if (col - 1 >= 0) openNode(node[col - 1][row]); // left
            if (row + 1 < gp.MAXWORLDROWS) openNode(node[col][row + 1]); // down
            if (col + 1 < gp.MAXWORLDCOLUMNS) openNode(node[col + 1][row]); // right

            // Scan openList to find best path
            int bestNodeIndex = 0;
            int bestNodefCost = Integer.MAX_VALUE;

            for (int i = 0; i < openList.size(); i++) {
                Node node = openList.get(i);
                if (node.fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = node.fCost;
                } else if (node.fCost == bestNodefCost) {
                    if (node.gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            if (openList.size() == 0) {
                break;
            }

            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }

    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath() {
        Node current = goalNode;
        while(current != startNode)  {
            pathList.add(0, current);
            current = current.parent;
        }   
    }
}
