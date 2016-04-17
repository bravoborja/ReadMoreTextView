/*
 * Copyright (C) 2016 Borja Bravo Álvarez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.borjabravo.readmoretextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/**
 * Created by borja on 17/4/16.
 */
public class ReadMoreTextView extends TextView {

    private final static String ELLIPSE = "... ";
    private final static String SPACE = " ";
    private final static int DEFAULT_NUM_LINES = 4;
    private final static int ALL_LINES = -1;

    private ReadMoreClickableSpan readMoreClickableSpan;
    private boolean readMore = true;

    private CharSequence trimCollapsedText;
    private CharSequence trimExpandedText;
    private int numLines;
    private int maxLines;
    private int colorClickableText;

    public ReadMoreTextView(Context context) {
        this(context, null);
        init();
    }

    public ReadMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ReadMoreTextView);
        int resourceIdTrimCollapsedText = typedArray.getResourceId(R.styleable.ReadMoreTextView_trimCollapsedText, R.string.read_more);
        int resourceIdTrimExpandedText = typedArray.getResourceId(R.styleable.ReadMoreTextView_trimExpandedText, R.string.read_less);
        this.trimCollapsedText = getResources().getString(resourceIdTrimCollapsedText);
        this.trimExpandedText = getResources().getString(resourceIdTrimExpandedText);
        this.numLines = typedArray.getInt(R.styleable.ReadMoreTextView_numLines, DEFAULT_NUM_LINES);
        this.maxLines = numLines;
        this.colorClickableText = typedArray.getColor(R.styleable.ReadMoreTextView_colorClickableText, ContextCompat.getColor(context, R.color.colorAccent));
        this.readMoreClickableSpan = new ReadMoreClickableSpan();
        typedArray.recycle();
        init();
    }

    private void init() {
        if (getTag() == null) {
            setTag(getText());
        }
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
                if (numLines == 0) {
                    updateText(textCollapsed(0), trimCollapsedText);
                } else if (numLines > 0 && getLineCount() > numLines) {
                    updateText(textCollapsed(numLines - 1), trimCollapsedText);
                } else {
                    updateText(textExpanded(getLineEndIndex(getLayout().getLineCount() - 1)), trimExpandedText);
                }
            }
        });
    }

    private int getLineEndIndex(int position) {
        return getLayout().getLineEnd(position);
    }

    private String textCollapsed(int position) {
        return getText().subSequence(0, getLineEndIndex(position) - trimCollapsedText.length() + 1) + ELLIPSE + trimCollapsedText;
    }

    private String textExpanded(int lineEndIndex) {
        if ((getText().length() == lineEndIndex) && (getLineCount() <= numLines)) {
            return getText().toString(); // Text does not need to expand
        } else {
            return getText().subSequence(0, lineEndIndex) + SPACE + trimExpandedText;
        }
    }

    private void updateText(String text, CharSequence expandText) {
        setText(text);
        setMovementMethod(LinkMovementMethod.getInstance());
        setText(addClickableReadMore(Html.fromHtml(getText().toString()), expandText.toString()), TextView.BufferType.SPANNABLE);
    }

    private SpannableStringBuilder addClickableReadMore(final Spanned strSpanned, final String spannableText) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);
        if (str.contains(spannableText)) {
            ssb.setSpan(readMoreClickableSpan, str.indexOf(spannableText), str.indexOf(spannableText) + spannableText.length(), 0);
        }
        return ssb;
    }

    private void updateViewMoreSpan() {
        setLayoutParams(getLayoutParams());
        setText(getTag().toString(), TextView.BufferType.SPANNABLE);
        invalidate();
        init();
    }

    private class ReadMoreClickableSpan extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            numLines = readMore ? ALL_LINES : maxLines;
            updateViewMoreSpan();
            readMore = !readMore;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(colorClickableText);
        }
    }
}