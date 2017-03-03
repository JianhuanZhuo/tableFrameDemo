package cn.keepfight.utils;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * 图片加载工具类.
 * @author Tom
 *
 */
public class ImageLoadUtil {

	public static final int IMG_SIZE_16 = 16;
	public static final int IMG_SIZE_32 = 32;

	/**
	 * 图像缓存
	 */
	public static Map<String, Image> reusableImageMap = new HashMap<>();

	/**
	 * 加载指定尺寸的图片，这种加载机制会缓存已加载完成的图片，<br/>
	 * 对于第二次请求相同的图片，会直接使用之前加载的图片文件而不是从磁盘中再加载一次
	 *
	 * @param imageName
	 *            指定名字
	 * @param size
	 *            指定尺寸，仅支持 {@link #reusableImage16Map} 和
	 *            {@link #reusableImage32Map}
	 * @return 图片对象，若对象不存在返回null。
	 */
	public static Image load(String imageName, int size) {
		if (size == IMG_SIZE_16 || size == IMG_SIZE_32) {
			if (reusableImageMap.containsKey("g" + size + "/" + imageName)) {
				return reusableImageMap.get("g" + size + "/" + imageName);
			} else {
				// @TODO 做成配置
				String imageUrl = "cn/keepfight/resources/g" + size + "/" + imageName;
				Image resImage = null;
				try {
					resImage = new Image(imageUrl, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				reusableImageMap.put("g" + size + "/" + imageName, resImage);
				return resImage;
			}
		}
		return null;
	}
}
