package cn.keepfight.frame.menu;

import cn.keepfight.frame.TStage;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.utils.ImageLoadUtil;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * 菜单项控制器类. 高度<br/>
 * <ol>
 * <li>p32=40</li>
 * <li>tp16=30</li>
 * <li>tp32-top=59
 * <li>
 * <li>tp32=44
 * <li>
 * </ol>
 *
 * @author Tom
 *
 */
public class MenuItemController{

	@FXML
	private ImageView pic;

	@FXML
	private Button btn;

	@SuppressWarnings("rawtypes")
	private TStage tStage;

	/**
	 * 菜单按钮类型
	 */
	private MenuItemType itemType = MenuItemType.UNKNOW;

	/**
	 * 菜单项所表示的算子
	 */
	private AbstractOperator operator;


	public MenuItemController() {
	}

	@SuppressWarnings("rawtypes")
	public void attachTStage(TStage tStage){
		this.tStage = tStage;
	}
	public void setMenuItemType(MenuItemType itemType){
		this.itemType = itemType;
	}

	public void setOperator(AbstractOperator operator){
		this.operator = operator;
		setBtnText(operator.getLabel());
		setPic(operator.getIcon());
		setTipText(operator.getTips());
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ActionResult actionResult;
				try {
					actionResult = operator.onAction();
				} catch (Exception e) {
					e.printStackTrace();
					return ;
				}
				if (actionResult==null) {
					//本地算子，不生成结果
					return;
				}
				if (!actionResult.localAction) {
					if (actionResult.resList.size()==1) {
						tStage.getContextMaster()
						.doOperate(tStage, operator.generateResource(), actionResult.resList.get(0));
					}else {
						//@TODO 做多输出的
						tStage.getContextMaster()
						.doOperate(tStage, operator.generateResource(), actionResult.resList);
					}
				}
			}
		});
	}

	@FXML
	public void initialize() {
	}

	/**
	 * 获得控制器所表示的算子模型对象
	 * @return 算子模型对象
	 */
	public AbstractOperator getOperator() {
		return operator;
	}

	/**
	 * 为该菜单按钮设置文字
	 *
	 * @param btnText
	 *            欲设置的文字
	 */
	public void setBtnText(String btnText) {
		if (btnText != null) {
			if (itemType.hasText()) {
				btn.setText(btnText);
			}
		}
	}

	/**
	 * 获得按钮的文字
	 *
	 * @return 若该菜单无按钮，则返回null，否则返回一个字符串，注意可能是个空字符串。
	 */
	public String getBtnText() {
		return itemType.hasText() ? btn.getText() : null;
	}

	/**
	 * 使用图标名进行设置。<br/>
	 * 该方法将使用{@link cn.keepfight.utils.ImageLoaderUtil.load(String, int)}
	 * 进行加载<br/>
	 *
	 * @param icon
	 *            图标名
	 * @see cn.keepfight.utils.ImageLoadUtil
	 */
	public void setPic(String icon) {
		setPic(ImageLoadUtil.load(icon, itemType.picSize()));
		;
	}

	/**
	 * 指定图片作为该菜单按钮图标。
	 *
	 * @param image
	 *            指定图片
	 */
	public void setPic(Image image) {
		if (hasPic()) {
			pic.setImage(image);
		}
	}

	public boolean hasPic() {
		return itemType.hasPicture();
	}

	/**
	 * 设置提示信息
	 *
	 * @param tip
	 *            欲设置的提示信息
	 */
	public void setTipText(String tip) {
		if (tip != null) {
			btn.setTooltip(new Tooltip(tip));
		}
	}
}
