package org.vaadin.neo4j.vaadin.events;

import org.vaadin.maddon.fields.MTable;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class RowDeleteListener implements ClickListener {
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
