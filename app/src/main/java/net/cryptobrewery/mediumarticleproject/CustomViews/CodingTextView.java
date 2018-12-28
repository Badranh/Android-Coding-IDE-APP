package net.cryptobrewery.mediumarticleproject.CustomViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class CodingTextView extends android.support.v7.widget.AppCompatTextView{
    public CodingTextView(Context context) {
        super(context);
        init(context);
    }

    public CodingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CodingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setTypeface(CachingFactory.getFont("fonts/firacode.ttf",context.getAssets()));
    }


}
