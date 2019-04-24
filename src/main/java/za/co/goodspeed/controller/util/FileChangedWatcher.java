package za.co.goodspeed.controller.util;

import java.io.File;

public abstract class FileChangedWatcher
{
    private File file;
    private long pollInterval;

    protected void createMe(String filePath, long pollInterval){
        file = new File(filePath);
        this.pollInterval = pollInterval;
    }
    public void watch() {

        Runnable runnable = () -> {
            try {
                long currentModifiedDate = file.lastModified();
                while (true) {
                    long newModifiedDate = file.lastModified();
                    if (newModifiedDate != currentModifiedDate) {
                        currentModifiedDate = newModifiedDate;
                        onModified();
                    }

                    Thread.sleep(pollInterval);
                }
            }
            catch(InterruptedException e){}
        };
        Thread thrd = new Thread(runnable);
        thrd.start();
    }

    public String getFilePath(){
        return file.getAbsolutePath();
    }

    protected abstract void onModified();
}