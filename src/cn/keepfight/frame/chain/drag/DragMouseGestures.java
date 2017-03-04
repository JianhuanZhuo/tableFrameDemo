package cn.keepfight.frame.chain.drag;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * ����ק�����϶�ʵ����.
 * ����д�ıȽϹ��̻������޸Ľ�����ѯǰ��
 */
public class DragMouseGestures {

	/**
	 * ѡ��Ⱥ
	 */
    SelectionModel selectionModel = SelectionModel.getInstance();

    /**
     * ��ק���긨���࣬�ṩ�ɱ仯���첽����
     */
    final DragContext dragContext = new DragContext();
    private class DragContext {
        double x;
        double y;
    }

    /**
     * ��ֹ������ͻ��־λ
     */
    private boolean enabled = false;

    /**
     * ע��ָ������
     * @param node ��ʵ����ק�Ŀ���Node
     */
    public void makeDraggable(final Node node) {
        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnMouseDragged(onMouseDraggedEventHandler);
        node.setOnMouseReleased(onMouseReleasedEventHandler);
    }

    /**
     * ����ǵ���¼���Ҳ���ǵ�һ��
     */
    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            // don't do anything if the user is in the process of adding to the selection model
            // �������ѡ�����˼
        	if( event.isControlDown() || event.isShiftDown())
                return;

            // ȡ��Դ����
            Node node = (Node) event.getSource();

            dragContext.x = node.getTranslateX() - event.getSceneX();
            dragContext.y = node.getTranslateY() - event.getSceneY();

            // ���Ѿ���ѡ���϶������ʱ�򵥻�����ʾ����֮ǰ������ѡ��
            if( !selectionModel.contains(node)) {
                selectionModel.clear();
                selectionModel.add( node);
            }

            // flag that the mouse released handler should consume the event,
            // so it won't bubble up to the pane which has a rubberband selection mouse released handler
            // ��־����ͷŵ�ʱ��Ӧ�����¼��������ڲ����ǵ�pane�ķ�������ͷ��¼���
            enabled = true;

            // ��������ѡ���¼�
            event.consume();
        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            if(!enabled){
                return;
            }

            // �ƶ���ѡ��Ķ���
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
        	System.out.println("�ͷ���Ӧ");
            // prevent rubberband selection handler
            if(enabled) {
            	System.out.println("�ͷ���Ӧ2");
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
     * �ض�λָ������Node
     * @param node ָ������Node
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

