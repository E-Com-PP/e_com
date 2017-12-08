package com.fci.e_com;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by StarkTheGnr on 12/6/2017.
 */

public class Synchronizer extends TimerTask {
    public List<NetTask> Tasks = new ArrayList<>();
    public long IntervalMS = 500;

    MainActivity MainActv;
    boolean busy = false;

    public Synchronizer(MainActivity main, long interv) { MainActv = main; IntervalMS = interv; }

    public void RunTask(NetTask task)
    {
        if(Tasks.size() == 0 && !busy) {
            task.started = true;
            task.run();
        }

        Tasks.add(task);
    }

    public void AddTask(NetTask task, boolean prioritize)
    {
        if(prioritize)
            Tasks.add(1, task);
        else
            Tasks.add(task);
    }

    public void TaskDone()
    {
        Tasks.remove(0);
        busy = false;
    }

    public void run()
    {
        if(!busy && Tasks.size() != 0 && !Tasks.get(0).started)
        {
            busy = true;
            Tasks.get(0).started = true;
            MainActv.webViewer.post(new Runnable() {
                @Override
                public void run() {
                    Tasks.get(0).run();
                }
            });
        }
    }
}

class NetTask implements Runnable
{
    String[] stringArgs;
    int[] intArgs;

    boolean started = false;

    public void run()
    {

    }
}
