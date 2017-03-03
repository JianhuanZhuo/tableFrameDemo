package cn.keepfight.frame.controller;

import java.util.Observable;

import cn.keepfight.frame.menu.MenuItemType;
import cn.keepfight.frame.model.OperatorModel;
import cn.keepfight.utils.ImageLoadUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * �˵����������. �߶�<br/>
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
public class MenuItemController extends Observable{

	@FXML
	private ImageView pic;

	@FXML
	private Button btn;

	/**
	 * �˵���ť����
	 */
	private MenuItemType itemType = MenuItemType.UNKNOW;

	/**
	 * �˵�������ʾ������
	 */
	private OperatorModel operator = OperatorModel.demoOperator();

	public MenuItemController() {
	}

	public MenuItemController(MenuItemType itemType, OperatorModel operator) {
		this.itemType = itemType;
		this.operator = operator;
	}

	@FXML
	public void initialize() {
		setBtnText(operator.getLabel());
		setPic(operator.getIcon());
		setBtnText(operator.getTips());
	}

	/**
	 * ��ÿ���������ʾ������ģ�Ͷ���
	 * @return ����ģ�Ͷ���
	 */
	public OperatorModel getOperator() {
		return operator;
	}

	/**
	 * Ϊ�ò˵���ť��������
	 *
	 * @param btnText
	 *            �����õ�����
	 */
	public void setBtnText(String btnText) {
		if (btnText != null) {
			if (itemType.hasText()) {
				btn.setText(btnText);
			}
		}
	}

	/**
	 * ��ð�ť������
	 *
	 * @return ���ò˵��ް�ť���򷵻�null�����򷵻�һ���ַ�����ע������Ǹ����ַ�����
	 */
	public String getBtnText() {
		return itemType.hasText() ? btn.getText() : null;
	}

	/**
	 * ʹ��ͼ�����������á�<br/>
	 * �÷�����ʹ��{@link cn.keepfight.utils.ImageLoaderUtil.load(String, int)}
	 * ���м���<br/>
	 *
	 * @param icon
	 *            ͼ����
	 * @see cn.keepfight.utils.ImageLoadUtil
	 */
	public void setPic(String icon) {
		setPic(ImageLoadUtil.load(icon, itemType.picSize()));
		;
	}

	/**
	 * ָ��ͼƬ��Ϊ�ò˵���ťͼ�ꡣ
	 *
	 * @param image
	 *            ָ��ͼƬ
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
	 * ������ʾ��Ϣ
	 *
	 * @param tip
	 *            �����õ���ʾ��Ϣ
	 */
	public void setTipText(String tip) {
		if (tip != null) {
			btn.setTooltip(new Tooltip(tip));
		}
	}

	@FXML
	public void onMenuItemClick() {
		// @TODO ������Ҫ���Ͻ�һ��
		notifyObservers(this);
	}

}
