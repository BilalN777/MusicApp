package com.cs478.clipserver;

// Declare any non-default types here with import statements

interface IAudioService {
    void play(int clipIndex);
    void pause();
    void stopPlayback();
    void stopService();
    void resume();

}