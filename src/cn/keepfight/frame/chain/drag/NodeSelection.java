package cn.keepfight.frame.chain.drag;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NodeSelection extends Application {

    DragMouseGestures dragMouseGestures = new DragMouseGestures();
    static Random rnd = new Random();

    static Label xLabel ;
    static Label yLabel ;

    @Override
    public void start(Stage primaryStage) {

        Pane pane = new Pane();
//        pane.setStyle("-fx-background-color:grey");

        new RubberBandSelection(pane);

        double width = 32;
        double height = 32;

        double padding = 20;
        for( int row=0; row < 4; row++) {
            for( int col=0; col < 4; col++) {

            	String[] images = {"filter.png", "tag.png", "sum.png", "sort.png"};

                Selectable selectable = new Selectable( width, height, images[col]);
                selectable.relocate( padding * (col+1) + width * col, padding * (row + 1) + height * row);

                pane.getChildren().add(selectable);

                dragMouseGestures.makeDraggable(selectable);

            }
        }

        Label infoLabel = new Label( "Drag on scene for Rubberband Selection. Shift+Click to add to selection, CTRL+Click to toggle selection. Drag selected nodes for multi-dragging.");
        pane.getChildren().add( infoLabel);

        Label xL = new Label( "x:");
        xL.relocate(20, 20);
        pane.getChildren().add( xL);
        Label x = new Label( "00");
        x.relocate(20, 40);
        pane.getChildren().add( x);
        xLabel = x;

        Label yL = new Label( "y:");
        yL.relocate(20, 60);
        pane.getChildren().add( yL);
        Label y = new Label( "00");
        y.relocate(20, 80);
        pane.getChildren().add( y);
        yLabel = y;

        ScrollPane scrollPane = new ScrollPane(pane);
        Scene scene = new Scene( scrollPane, 1600, 900);
        scene.getStylesheets().add( getClass().getResource("highlight.css").toExternalForm());

        primaryStage.setScene( scene);
        primaryStage.show();

    }





    public static void main(String[] args) {
        launch(args);
    }

}
