package com.bordengrammar.bordengrammarapp;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

/**
 * The manager of roboto typefaces.
 *
 * @author Finley Smith
 */
public class FancyTypefaceManager {

    /*
     * Available values ​​for the "typeface" attribute.
     */
    private final static int SANS_EXTRALIGHT = 0;
    private final static int SANS_LIGHT = 1;
    private final static int SANS_REGULAR = 2;
    private final static int SANS_SEMIBOLD = 3;
    private final static int SANS_BOLD = 4;

    /**
     * Array of created typefaces for later reused.
     */
    private final static SparseArray<Typeface> mTypefaces = new SparseArray<Typeface>(20);

    
    public static Typeface obtaintTypeface(Context context, int typefaceValue) throws IllegalArgumentException {
        Typeface typeface = mTypefaces.get(typefaceValue);
        if (typeface == null) {
            typeface = createTypeface(context, typefaceValue);
            mTypefaces.put(typefaceValue, typeface);
        }
        return typeface;
    }

    
    private static Typeface createTypeface(Context context, int typefaceValue) throws IllegalArgumentException {
        Typeface typeface;
        switch (typefaceValue) {
            case SANS_EXTRALIGHT:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/SourceSansPro-ExtraLight.otf");
                break;
            case SANS_LIGHT:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/SourceSansPro-Light.otf");
                break;
            case SANS_REGULAR:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/SourceSansPro-Regular.otf");
                break;
            case SANS_SEMIBOLD:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/SourceSansPro-Semibold.otf");
                break;
            case SANS_BOLD:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/SourceSansPro-Bold.otf");
                break;
            
            default:
                throw new IllegalArgumentException("Unknown `typeface` attribute value " + typefaceValue);
        }
        return typeface;
    }

}