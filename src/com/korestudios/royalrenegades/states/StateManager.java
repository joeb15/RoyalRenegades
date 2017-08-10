package com.korestudios.royalrenegades.states;

import com.korestudios.royalrenegades.Main;
import com.korestudios.royalrenegades.constants.GlobalVariables;
import com.korestudios.royalrenegades.guis.GuiManager;
import com.korestudios.royalrenegades.input.Input;
import com.korestudios.royalrenegades.shaders.Shader;
import com.korestudios.royalrenegades.utils.time.TimerUtils;

import static com.korestudios.royalrenegades.constants.GlobalVariables.FRAME_HEIGHT;
import static com.korestudios.royalrenegades.constants.GlobalVariables.FRAME_WIDTH;

public class StateManager {

    public static State gameState = new GameState();
    public static State introState = new SplashState();

    public static State CURRENT_STATE = introState;

    public static void switchState(State state){
        CURRENT_STATE.destroy();
        GuiManager.clearGuis();
        TimerUtils.clearTimers();
        Input.clearListeners();
        CURRENT_STATE=state;
        CURRENT_STATE.init();
        Shader.loadAll();
    }

}
