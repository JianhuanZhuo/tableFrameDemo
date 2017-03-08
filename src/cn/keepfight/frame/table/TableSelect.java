package cn.keepfight.frame.table;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.util.Pair;

public class TableSelect {

	public static final String SELECT_HIGHLIGHT = "cloumn_selection";

	private TableSelectState stateNow = TableSelectState.NONE;
	private List<Pair<Integer, String>> columnIndexSelect = new ArrayList<>();
	private List<TableColumn> columnSelects = new ArrayList<>();
	/**
	 * 表格选择状态
	 * @author Tom
	 *
	 */
	public enum TableSelectState{

		NONE,
		TAB_WHOLE,
		CELL_SINGLE,
		CELL_MULTI,
		ROW_SINGLE,
		ROW_MULTI,
		COL_SINGLE,
		COL_MULTI;
	}

	public TableSelectState getState() {
		return stateNow;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void columnSelect(Pair<Integer, String> index, TableColumn column) {
		stateChange(TableSelectState.COL_SINGLE);
		columnIndexSelect.add(index);
		columnSelects.add(column);
		column.getStyleClass().add(SELECT_HIGHLIGHT);
	}

	/**
	 * 添加选择
	 * @param index
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addColumnSelect(Pair<Integer, String> index, TableColumn column) {
		if (getState()!=TableSelectState.COL_MULTI) {
			stateChange(TableSelectState.COL_MULTI);
		}
		columnIndexSelect.add(index);
		columnSelects.add(column);
		column.getStyleClass().add(SELECT_HIGHLIGHT);
	}

	public List<Pair<Integer, String>> getColumnSelect() {
		return columnIndexSelect;
	}

	public List<String> getColumnStrings(){
		List<String> reStrings = new ArrayList<>();
		for (Pair<Integer, String> p : columnIndexSelect) {
			reStrings.add(p.getValue());
		}
		return reStrings;
	}

	@SuppressWarnings("rawtypes")
	private void stateChange(TableSelectState state){
		switch (stateNow) {
		case COL_SINGLE:
			for (TableColumn c : columnSelects) {
				c.getStyleClass().remove(SELECT_HIGHLIGHT);
			}
			columnSelects.clear();
			columnIndexSelect.clear();
			break;
		case COL_MULTI:
			for (TableColumn c : columnSelects) {
				c.getStyleClass().remove(SELECT_HIGHLIGHT);
			}
			columnSelects.clear();
			columnIndexSelect.clear();
			break;
		default:
			break;
		}
		stateNow = state;
	}
}
