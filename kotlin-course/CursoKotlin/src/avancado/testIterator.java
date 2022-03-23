package avancado;

import java.util.HashMap;
import java.util.Iterator;

public class testIterator {


    private Iterator<Integer> mAlbumIterator;
    private HashMap<Integer, String> songs;

    testIterator(){
        songs = new HashMap();
        songs.put(1, "um");
        songs.put(2, "dois");
        songs.put(3, "tres");
        mAlbumIterator = songs.keySet().iterator();
    }

    int getNext(){
        int result = mAlbumIterator.hasNext() ? mAlbumIterator.next() : 0;
        return result;
    }
}

