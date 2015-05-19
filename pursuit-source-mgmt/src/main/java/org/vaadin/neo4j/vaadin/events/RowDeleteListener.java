package org.vaadin.neo4j.vaadin.events;

import org.vaadin.viritin.fields.MTable;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class RowDeleteListener implements ClickListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1671366517895664214L;
	Object rowId;
	MTable table;
	Object entity;
	
	public RowDeleteListener(MTable table, Object rowId, Object entity) {
		this.table=table;
		this.rowId=rowId;
		this.entity=entity;
	}
	@Override
	public void buttonClick(ClickEvent event) {
		table.removeItem(rowId);
	}

}
