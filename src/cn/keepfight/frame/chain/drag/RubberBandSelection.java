package cn.keepfight.frame.chain.drag;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

/**
 * 选择框类.
 * 处理一般的选择
 */
public class RubberBandSelection {

	/**
	 * 选择群
	 */
    SelectionModel selectionModel = SelectionModel.getInstance();

    /**
     * final 类以提供可变化的异步访问
     */
    final DragContext dragContext = new DragContext();
    private final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
    }

    /**
     * 选择方框
     */
    Rectangle rect;

    /**
     * 父面板
     */
    Pane group;

    /**
     * 多点击保护标志
     */
    boolean enabled = false;

    public RubberBandSelection(Pane group) {

        this.group = group;

        rect = new Rectangle( 0,0,0,0);
        rect.setStroke(Color.BLUE);
        rect.setStrokeWidth(1);
        rect.setStrokeLineCap(StrokeLineCap.ROUND);
        rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));

        group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

    }

    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

        	System.out.println("kuangdianji!");

            //对于已处于左击拖动时右键点击异常，简单的处理机制
            if(enabled)
                return;

            dragContext.mouseAnchorX = event.getSceneX();
            dragContext.mouseAnchorY = event.getSceneY();

            rect.setX(dragContext.mouseAnchorX);
            rect.setY(dragContext.mouseAnchorY);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().add(rect);

            event.consume();

            enabled = true;
        }
    };

    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            if( !event.isShiftDown() && !event.isControlDown()) {
                selectionModel.clear();
            }

            //边界碰撞比较以提供选择，和Shift、Ctrl多选择处理
            for( Node node: group.getChildren()) {
                if( node instanceof Selectable) {
                    if( node.getBoundsInParent().intersects( rect.getBoundsInParent())) {
                        if( event.isShiftDown()) {
                            selectionModel.add( node);
                        } else if( event.isControlDown()) {
                            if( selectionModel.contains( node)) {
                                selectionModel.remove( node);
                            } else {
                                selectionModel.add( node);
                            }
                        } else {
                            selectionModel.add( node);
                        }
                    }
                }
            }

            selectionModel.log();

            //隐藏方块
            rect.setX(0);
            rect.setY(0);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().remove( rect);

            event.consume();

            enabled = false;
        }
    };

    /**
     * 提供鼠标拖动改变选择框处理
     */
    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            double offsetX = event.getSceneX() - dragContext.mouseAnchorX;
            double offsetY = event.getSceneY() - dragContext.mouseAnchorY;

            if( offsetX > 0)
                rect.setWidth( offsetX);
            else {
                rect.setX(event.getSceneX());
                rect.setWidth(dragContext.mouseAnchorX - rect.getX());
            }

            if( offsetY > 0) {
                rect.setHeight( offsetY);
            } else {
                rect.setY(event.getSceneY());
                rect.setHeight(dragContext.mouseAnchorY - rect.getY());
            }

            event.consume();
        }
    };
}
