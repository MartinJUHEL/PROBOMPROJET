package com.martin.promob;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ClassementSlider extends LinearLayout {

    protected boolean isOpen;

    protected RelativeLayout classementRelative;



    /**
     * Constructeur utilis� pour l'initialisation en Java.
     * @param context Le contexte de l'activit�.
     */
    public ClassementSlider(Context context) {
        super(context);
        this.isOpen=false;

    }

    /**
     * Constructeur utilis� pour l'initialisation en XML.
     * @param context Le contexte de l'activit�.
     * @param attrs Les attributs d�finis dans le XML.
     */
    public ClassementSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isOpen=false;
    }

    /**
     * Utilis�e pour ouvrir ou fermer le menu.
     * @return true si le menu est d�sormais ouvert.
     */
    public boolean toggle() {
        //Animation de transition.

        //On passe de ouvert � ferm� (ou vice versa)
        isOpen = !isOpen;

        //Si le menu est d�j� ouvert
        if (isOpen)
        {
            classementRelative.setVisibility(View.VISIBLE);
        } else
        {
            classementRelative.setVisibility(View.GONE);
        }

        return isOpen;
    }


    public void setToHide(RelativeLayout toHide) {
        this.classementRelative = toHide;
    }
}

