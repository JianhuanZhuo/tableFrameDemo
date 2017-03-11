package cn.keepfight.frame.chain.drag;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Node;

/**
 * 选择模型类，用于记录当前已选择的
 *
 */
public class SelectionManager {

    Set<Node> selection = new HashSet<>();

    public void add(Node node) {
        if( !node.getStyleClass().contains("highlight")) {
            node.getStyleClass().add( "highlight");
        }
        selection.add( node);
    }

    public void remove(Node node) {
        node.getStyleClass().remove( "highlight");
        selection.remove( node);
    }

    public void clear() {
        while( !selection.isEmpty()) {
            remove( selection.iterator().next());
        }
    }

    public boolean contains( Node node) {
        return selection.contains(node);
    }

    public int size() {
        return selection.size();
    }

    /**
     * 获得当前的已选择节点
     * @return 已选择节点集合
     */
    public Set<Node> getSelection() {
    	Set<Node> result = new HashSet<>();
    	for (Node node : selection) {
			if (node.getStyleClass().contains("highlight")) {
				result.add(node);
			}
		}
		return result;
	}

    public void log() {
//        System.out.println( "Items in model: " + Arrays.asList( selection.toArray()));
    }

}
