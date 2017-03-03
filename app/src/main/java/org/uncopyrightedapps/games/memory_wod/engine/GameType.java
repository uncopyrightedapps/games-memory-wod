package org.uncopyrightedapps.games.memory_wod.engine;

import org.uncopyrightedapps.games.memory_wod.R;

public enum GameType {

    NO_BRAIN("NO_BRAIN", R.string.button_game_type_no_brain),
    EASY("EASY", R.string.button_game_type_easy),
    MEDIUM("MEDIUM", R.string.button_game_type_medium),
    HARD("HARD", R.string.button_game_type_hard);

    private String code;
    private int resourceStringId;

    GameType(String code, int resourceStringId) {
        this.resourceStringId = resourceStringId;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public int getResourceStringId() {
        return resourceStringId;
    }
}
