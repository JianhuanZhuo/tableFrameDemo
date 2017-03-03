package cn.keepfight.frame.chain.drag;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

/**
 * ѡ�����.
 * ����һ���ѡ��
 */
public class RubberBandSelection {

	/**
	 * ѡ��Ⱥ
	 */
    SelectionModel selectionModel = SelectionModel.getInstance();

    /**
     * final �����ṩ�ɱ仯���첽����
     */
    final DragContext dragContext = new DragContext();
    private final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
    }

    /**
     * ѡ�񷽿�
     */
    Rectangle rect;

    /**
     * �����
     */
    Pane group;

    /**
     * ����������־
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

            //�����Ѵ�������϶�ʱ�Ҽ�����쳣���򵥵Ĵ������
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

            //�߽���ײ�Ƚ����ṩѡ�񣬺�Shift��Ctrl��ѡ����
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

            //���ط���
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
     * �ṩ����϶��ı�ѡ�����
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
