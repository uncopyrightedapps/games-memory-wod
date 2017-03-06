package org.uncopyrightedapps.games.memory_wod.engine;


public enum Graphic {
    ANIMALS(0, "animais"),
    DRAWINGS(1, "draw"),
    BUGS(2, "bug");

    private int code;
    private String filePrefix;

    private Graphic(int code, String filePrefix) {
        this.code = code;
        this.filePrefix = filePrefix;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public static Graphic getByCode(int code) {
        for(Graphic e : values()) {
            if(e.code == code) return e;
        }
        return null;
    }

    public int getCode() {
        return code;
    }
}