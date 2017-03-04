package cn.keepfight.frame.chain.drag;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * 鼠标可拽客体拖动实现类.
 * 该类写的比较过程化，欲修改建议质询前任
 */
public class DragMouseGestures {

	/**
	 * 选择群
	 */
    SelectionModel selectionModel = SelectionModel.getInstance();

    /**
     * 拖拽坐标辅助类，提供可变化的异步访问
     */
    final DragContext dragContext = new DragContext();
    private class DragContext {
        double x;
        double y;
    }

    /**
     * 防止多点击冲突标志位
     */
    private boolean enabled = false;

    /**
     * 注册指定客体
     * @param node 欲实现拖拽的客体Node
     */
    public void makeDraggable(final Node node) {
        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnMouseDragged(onMouseDraggedEventHandler);
        node.setOnMouseReleased(onMouseReleasedEventHandler);
    }

    /**
     * 这个是点击事件，也就是点一下
     */
    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            // don't do anything if the user is in the process of adding to the selection model
            // 本身就事选择的意思
        	if( event.isControlDown() || event.isShiftDown())
                return;

            // 取出源对象
            Node node = (Node) event.getSource();

            dragContext.x = node.getTranslateX() - event.getSceneX();
            dragContext.y = node.getTranslateY() - event.getSceneY();

            // 在已经有选择拖动对象的时候单击，表示放弃之前的重新选择
            if( !selectionModel.contains(node)) {
                selectionModel.clear();
                selectionModel.add( node);
            }

            // flag that the mouse released handler should consume the event,
            // so it won't bubble up to the pane which has a rubberband selection mouse released handler
            // 标志鼠标释放的时候应消费事件，以至于不会涨到pane的方框鼠标释放事件，
            enabled = true;

            // 保护方框选择事件
            event.consume();
        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            if(!enabled){
                return;
            }

            // 移动已选择的对象
            for( Node node: selectionModel.selection) {
                node.setTranslateX( dragContext.x + event.getSceneX());
                node.setTranslateY( dragContext.y + event.getSceneY());
                NodeSelection.xLabel.setText(
                		"LayoutX:"+node.getLayoutX()+
                		" node.getLayoutBounds().getWidth()"+node.getLayoutBounds().getWidth()+
                		" TranslateX:"+node.getTranslateX()+
                		" eventSceneX:"+event.getSceneX()+
                		" node.getScene().getX():"+node.getScene().getX()+
                		" node.getScene().getHeight():"+node.getScene().getHeight());
                NodeSelection.yLabel.setText(
                		"LayoutY:"+node.getLayoutY()+
                		" node.getLayoutBounds().getHeight()"+node.getLayoutBounds().getHeight()+
                		" TranslateY:"+node.getTranslateY()+
                		" eventSceneX:"+event.getSceneY()+
                		" node.getScene().getY():"+node.getScene().getY()+
                		" node.getScene().getWidth():"+node.getScene().getWidth());
            }
        }
    };

    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
        	System.out.println("释放响应");
            // prevent rubberband selection handler
            if(enabled) {
            	System.out.println("释放响应2");
                // set node's layout position to current position,remove translate coordinates
                for( Node node: selectionModel.selection) {
                    fixPosition(node);
                }

                enabled = false;

                event.consume();
            }
        }
    };

    /**
     * 重定位指定客体Node
     * @param node 指定客体Node
     */
    private void fixPosition( Node node) {

        double x = node.getTranslateX();
        double y = node.getTranslateY();

        node.relocate(node.getLayoutX() + x, node.getLayoutY() + y);

        NodeSelection.xLabel.setText("LayoutX:"+node.getLayoutX()+" TranslateX:"+x);
        NodeSelection.yLabel.setText("LayoutY:"+node.getLayoutY()+" TranslateY:"+y);

        node.setTranslateX(0);
        node.setTranslateY(0);
    }

}

