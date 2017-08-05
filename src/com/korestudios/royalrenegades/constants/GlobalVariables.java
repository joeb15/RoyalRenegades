package com.korestudios.royalrenegades.constants;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class GlobalVariables {

    /**
     * ENTITY VARIABLES
     */
    public static final float ENTITY_SPEED = 2f/60f;

    /**
     * FRAME VARIABLES
     */
    public static int FRAME_WIDTH = 1280;
    public static int FRAME_HEIGHT = 720;

    /**
     * WORLD VARIABLES
     */
    public static int TILE_SIZE;
    public static float TILE_SIZE_INCHES = 0.25f;

    public static Matrix4f PROJECTION_MATRIX = new Matrix4f();
    public static Vector2f CENTER =
            new Vector2f(FRAME_WIDTH/2, FRAME_HEIGHT/2);

    /**
     * DEBUG VARIABLES
     */

    public static int triangles_drawn = 0, triangles_drawn_last_frame = 0;
    public static long render_time_ns = 0;
    public static long displayed_render_time=0;
    public static long update_time_ns = 0;
    public static long displayed_update_time=0;
    public static double displayed_fps=0;
    public static double displayed_tps=0;
    public static long time_between_updates = (long) (1E9/10);
    public static boolean debug_mode = true;
    public static boolean limitFrameRate = true;



}
