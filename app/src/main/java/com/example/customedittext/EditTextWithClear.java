package com.example.customedittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

public class EditTextWithClear extends AppCompatEditText {

    Drawable mClearButtonImage;

    public EditTextWithClear(Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_clear_opaque_24dp, null);

        //if the clear (x) button is tapped, clear text
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (getCompoundDrawablesRelative()[2] != null){
                    float clearButtonStart;   //Used for LTR languages
                    float clearButtonEnd;     //Used for RTL languages
                    boolean isClearButtonClicked = false;

                    //  Detect the touch in RTL or LTR layout direction.
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL){
                        //if RTL, get the end of the button in the left side
                        clearButtonEnd = mClearButtonImage
                                .getIntrinsicWidth() + getPaddingStart();

                        // If the touch occurred before the end of the button,
                        // set isClearButtonClicked to true.
                        if(motionEvent.getX() < clearButtonEnd){
                            isClearButtonClicked = true;
                        }


                    }else {
                        //Layout is LTR
                        //Get the start of the button in the right side
                        clearButtonStart = (getWidth() - getPaddingEnd()
                                            - mClearButtonImage.getIntrinsicWidth());

                        // If the touch occurred after the start of the button,
                        // set isClearButtonClicked to true.
                        if (motionEvent.getX() > clearButtonStart) {
                            isClearButtonClicked = true;

                        }

                        }

                    // Check for actions if the button is tapped.
                    if (isClearButtonClicked){
                        // Check for ACTION_DOWN (always occurs before ACTION_UP).
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            //Switch to the black version of the clear image
                            mClearButtonImage = ResourcesCompat.getDrawable
                                    (getResources(), R.drawable.ic_clear_black_24dp,null);
                            showClearButton();
                        }
                        // Check for ACTION_UP
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                            //Switch to the opaque version of clear button
                            mClearButtonImage = ResourcesCompat.getDrawable
                                    (getResources(), R.drawable.ic_clear_opaque_24dp, null);

                            // Clear the text and hide the clear button
                            getText().clear();
                            hideClearButton();
                            return true;
                        }

                    }else {
                        return false;
                    }

                }
                return false;
            }
        });

        //if the text changes, show or hide the (x) button
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showClearButton();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void showClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,                  //Start of text
                        null,            //Top of text
                        mClearButtonImage,    //End of text
                        null);        //Bottom of text
    }

    private void hideClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds
                 (     null,            //Start of text
                        null,            //Top
                        null,            //End
                     null);           //Bottom
    }
}
