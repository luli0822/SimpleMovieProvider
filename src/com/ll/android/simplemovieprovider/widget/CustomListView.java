package com.ll.android.simplemovieprovider.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.ll.android.simplemovieprovider.R;

public class CustomListView extends ListView {

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialControl();
	}

	public CustomListView(Context context) {
		super(context);
		initialControl();
	}

	private void initialControl() {
		final LayoutInflater inflater;

		inflater =
				(LayoutInflater) this.getContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE );

		inflater.inflate( R.layout.list_item_layout, this, false );

		this.setBackgroundResource(android.R.color.transparent);
	}
	
	
}
