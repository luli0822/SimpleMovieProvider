package com.ll.android.simplemovieprovider.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

public class ClearableEditText extends EditText
{
	private static final String TAG = "ClearableEditText";
	
    public String mDefaultValue = "";

    @SuppressWarnings("deprecation")
	final Drawable imgX = getResources().getDrawable(
    		android.R.drawable.presence_offline); // X image
                                                                                           
    private Context mContext;

    public ClearableEditText(Context context)
    {
        super(context);
        mContext = context;
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        init();
    }

    void init()
    {

        // Set bounds of our X button
        imgX.setBounds(0, 0, imgX.getIntrinsicWidth(), imgX.getIntrinsicHeight());

        // There may be initial text in the field, so we may need to display the
        // button
        manageClearButton();
        
        setHorizontallyScrolling(false);
        setMaxLines(Integer.MAX_VALUE);

        this.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                ClearableEditText et = ClearableEditText.this;

                // Is there an X showing?
                if (et.getCompoundDrawables()[2] == null)
                    return false;
                // Only do this for up touches
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                // Is touch on our clear button?
                if (event.getX() > et.getWidth() - et.getPaddingRight() - imgX.getIntrinsicWidth())
                {
                    et.setText("");
                    ClearableEditText.this.removeClearButton();
                }
                return false;
            }
        });
	}
    
	public void manageClearButton()
    {
        if (this.getText().toString().equals(""))
            removeClearButton();
        else
            addClearButton();
    }

    public void addClearButton()
    {
        this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], imgX, this.getCompoundDrawables()[3]);
    }

    public void removeClearButton()
    {
        this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
    }

}