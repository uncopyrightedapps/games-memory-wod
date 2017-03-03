package org.uncopyrightedapps.games.memory_wod.engine;

public enum GameType {

    NO_BRAIN("NO_BRAIN", "Sem Pensar"),
    EASY("EASY", "Fácil"),
    MEDIUM("MEDIUM", "Média"),
    HARD("HARD", "Difícil");

    private String code;
    private String name;

    GameType(String code, String name) {
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return name;
    }


}
