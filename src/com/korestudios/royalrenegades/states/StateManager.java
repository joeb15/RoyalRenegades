package com.korestudios.royalrenegades.states;

import com.korestudios.royalrenegades.guis.GuiManager;
import com.korestudios.royalrenegades.input.Input;
import com.korestudios.royalrenegades.utils.time.TimerUtils;

public class StateManager {

    public static State gameState = new GameState();

    public static State CURRENT_STATE = gameState;

    public static void switchState(State state){
        CURRENT_STATE.destroy();
        GuiManager.clearGuis();
        TimerUtils.clearTimers();
        Input.clearListeners();
        CURRENT_STATE=state;
        CURRENT_STATE.init();
    }

}
