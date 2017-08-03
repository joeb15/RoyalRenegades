package com.korestudios.royalrenegades.constants;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import static com.korestudios.royalrenegades.constants.VariableConstants.FRAME_HEIGHT;
import static com.korestudios.royalrenegades.constants.VariableConstants.FRAME_WIDTH;

public class GlobalVariables {
    public static Vector2f CENTER =
            new Vector2f(FRAME_WIDTH/2, FRAME_HEIGHT/2);
    public static int triangles_drawn = 0, triangles_drawn_last_frame = 0;
    public static long render_time_ns = 0;
    public static long displayed_render_time=0;
    public static long update_time_ns = 0;
    public static long displayed_update_time=0;
    public static double displayed_fps=0;
    public static double displayed_tps=0;
    public static final Matrix4f PROJECTION_MATRIX = new Matrix4f();
    public static long time_between_updates = (long) (1E9/10);

    public static boolean debug_mode = false;
    public static final float delta = 0.00001f;
}
