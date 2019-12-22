package com.martin.promob;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class EscapeActivity extends Activity {
    private EscapeView escapeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);

        // On cré un objet "GameView" qui est le code principal du jeu
        escapeView=new EscapeView(this,size.x,size.y);

        // et on l'affiche.
        setContentView(escapeView);

    }


}
