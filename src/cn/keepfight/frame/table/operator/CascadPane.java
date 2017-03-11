package cn.keepfight.frame.table.operator;

import cn.keepfight.utils.ImageLoadUtil;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class CascadPane extends GridPane{

	public static final String BRANCH_URL="";

	public static Image branch = ImageLoadUtil.loadViewImage(BRANCH_URL);
	ImageView branchImageView;
	Node headNode;
	Node upNode;
	Node downNode;
	public CascadPane(Node head, Node up, Node down) {
		super();
		setHgap(3);
		setVgap(3);
		setAlignment(Pos.CENTER);

		headNode = head;
		branchImageView = new ImageView(branch);
		upNode = up;
		downNode = down;

		add(headNode, 0, 0, 0, 1);
		add(branchImageView, 1, 0, 1, 1);
		add(up, 2, 0);
		add(down, 2, 1);
	}
}
