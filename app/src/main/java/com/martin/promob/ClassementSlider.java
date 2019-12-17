package com.martin.promob;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ClassementSlider extends LinearLayout {

    protected boolean isOpen;

    protected RelativeLayout classementRelative;

    protected final static int SPEED = 300;

    /* Listener pour l'animation de fermeture du menu */
    Animation.AnimationListener closeListener = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {
            classementRelative.setVisibility(View.GONE);
        }

        public void onAnimationRepeat(Animation animation) {

        }

        public void onAnimationStart(Animation animation) {

        }
    };

    /* Listener pour l'animation d'ouverture du menu */
    Animation.AnimationListener openListener = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
            classementRelative.setVisibility(View.VISIBLE);
        }
    };

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
        TranslateAnimation animation = null;

        //On passe de ouvert � ferm� (ou vice versa)
        isOpen = !isOpen;

        //Si le menu est d�j� ouvert
        if (isOpen)
        {
            //Animation de translation du haut gauche vers bas droite
            animation = new TranslateAnimation(0.0f,0.0f, 0.0f, -classementRelative.getHeight());
            animation.setAnimationListener(openListener);
        } else
        {
            //Sinon, animation de translation du bas gauche vers haut droit
            animation = new TranslateAnimation(0.0f,0.0f, -classementRelative.getHeight(), 0.0f);
            animation.setAnimationListener(closeListener);
        }

        //On d�termine la dur�e de l'animation
        animation.setDuration(SPEED);
        //On ajoute un effet d'acc�l�ration
        animation.setInterpolator(new AccelerateInterpolator());
        //Enfin, on lance l'animation
        startAnimation(animation);

        return isOpen;
    }


    public void setToHide(RelativeLayout toHide) {
        this.classementRelative = toHide;
    }
}

