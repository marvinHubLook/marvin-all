package cn.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-02-27 17:28
 **/
public class VersionedData {
    // bit that we'll use to indicate that the state is being written to
    private static final int WRITE = 1 << 31;
    // we need to CAS version (in practise we'll do it via Unsafe to avoid extra object)
    private final AtomicInteger version = new AtomicInteger();

    // this is the data I protect, in reality there is much more protected state
    private int x, y;

    public synchronized void update(int x, int y) {
        // I guarantee single writers to this method,
        // illustrated with 'synchronized' in this simplification
        // first use CAS to mark version as being written to
        int v0 = version.get(); // can be non-volatile read, but then the following CAS can fail and needs to retry
        version.compareAndSet(v0, v0 | WRITE); // always succeeds in single-writer case, ensures proper HB edges for JMM
        // then write protected data (non-volatile writes)
        this.x = x;
        this.y = y;
        // then increment version and reset write bit
        version.set((v0 + 1) & ~WRITE);
    }

    public DataCarrier read() {
        // I allow multiple readers, so this method is not synchronized
        int x=0, y=0;
        int v0;
        do {
            // first read version
            v0 = version.get();
            if ((v0 & WRITE) != 0)
                continue; // immediately abort, because write in progress was detected
            // then read protected data
            x = this.x;
            y = this.y;
            // use CAS to check that version is still the same and to ensure proper HB edges for JMM at the same time
        } while (!version.compareAndSet(v0, v0));
        return new DataCarrier(x, y);
    }

    static class DataCarrier{
        private int x,y;
        public DataCarrier(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
