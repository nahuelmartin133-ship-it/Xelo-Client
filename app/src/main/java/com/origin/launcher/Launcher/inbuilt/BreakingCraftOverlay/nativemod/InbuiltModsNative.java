package com.origin.launcher.Launcher.inbuilt.BreakingCraftOverlay.nativemod;

public class InbuiltModsNative {
    private static boolean libraryLoaded = false;

    public static synchronized boolean loadLibrary() {
        if (libraryLoaded) return true;
        try {
            System.loadLibrary("BreakingCraftOverlay");
            libraryLoaded = true;
            return true;
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isLoaded() {
        return libraryLoaded;
    }
}