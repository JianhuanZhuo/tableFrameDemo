package cn.keepfight.frame.chain.drag;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
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
    SelectionManager selectionModel;

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
    final Pane group;

//    final Label x = new Label();
//    final Label y = new Label();

    /**
     * ����������־
     */
    boolean enabled = false;

    public RubberBandSelection(Pane group, SelectionManager selectionModel) {

        this.group = group;
        this.selectionModel = selectionModel;

        rect = new Rectangle( 0,0,0,0);
        rect.setStroke(Color.BLUE);
        rect.setStrokeWidth(1);
        rect.setStrokeLineCap(StrokeLineCap.ROUND);
        rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));

        group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
//        group.addEventHandler(MouseEvent.MOUSE_MOVED, move);
    }

//    EventHandler<MouseEvent> move = new EventHandler<MouseEvent>(){
//		@Override
//		public void handle(MouseEvent event) {
//			x.setText("x"+(event.getSceneX()-group.localToScene(0, 0).getX()));
//			y.setText("y"+(event.getSceneY()-group.localToScene(0, 0).getY()));
//		}
//    };


    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

        	if (!event.getButton().equals(MouseButton.PRIMARY)) {
        		return;
			}

            dragContext.mouseAnchorX = (event.getSceneX()-group.localToScene(0, 0).getX());
            dragContext.mouseAnchorY = (event.getSceneY()-group.localToScene(0, 0).getY());

            rect.setX(dragContext.mouseAnchorX);
            rect.setY(dragContext.mouseAnchorY);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().add(rect);

            event.consume();
        }
    };

    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

        	if (!event.getButton().equals(MouseButton.PRIMARY)) {
        		return;
			}

//        	System.out.println("�����ͷ�");

            if( !event.isShiftDown() && !event.isControlDown()) {
                selectionModel.clear();
            }

            //�߽���ײ�Ƚ����ṩѡ�񣬺�Shift��Ctrl��ѡ����
            for( Node node: group.getChildren()) {
                if( node instanceof Dragable) {
                    if( node.getBoundsInParent().intersects( rect.getBoundsInParent())) {
                        if( event.isShiftDown()) {
                            selectionModel.add( node);
                        } else if( event.isControlDown()) {
                            if( selectionModel.contains(node)) {
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

//            enabled = false;
        }
    };

    /**
     * �ṩ����϶��ı�ѡ�����
     */
    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

        	if (!event.getButton().equals(MouseButton.PRIMARY)) {
        		return;
			}

            double offsetX = (event.getSceneX()-group.localToScene(0, 0).getX()) - dragContext.mouseAnchorX;
            double offsetY = (event.getSceneY()-group.localToScene(0, 0).getY()) - dragContext.mouseAnchorY;
//            x.setText("event.getSceneX():"+event.getSceneX()+
//            		"\tevent.getX()"+event.getX()+
//            		"\tgroup.getLayoutX():"+group.getLayoutX());
//
//            y.setText("event.getSceneY():"+event.getSceneY()+
//            		"\tevent.getY()"+event.getY()+
//            		"\tevent.getY()"+group.localToScene(0, 0)+
//            		"\tgroup.getLayoutY():"+group.localToParent(group.getBoundsInLocal()));


            if( offsetX > 0)
                rect.setWidth(offsetX);
            else {
                rect.setX((event.getSceneX()-group.localToScene(0, 0).getX()));
                rect.setWidth(dragContext.mouseAnchorX - rect.getX());
            }

            if( offsetY > 0) {
                rect.setHeight( offsetY);
            } else {
                rect.setY((event.getSceneY()-group.localToScene(0, 0).getY()));
                rect.setHeight(dragContext.mouseAnchorY - rect.getY());
            }

            event.consume();
        }
    };
}
