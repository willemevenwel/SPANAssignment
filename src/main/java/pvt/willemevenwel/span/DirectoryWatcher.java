package pvt.willemevenwel.span;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;

abstract class DirectoryWatcher {

    private static Logger logger = Logger.getLogger(DirectoryWatcher.class);

    private String mPathToListen = "";

    public String getPathToListen() {
        return mPathToListen;
    }

    public void setPathToListen(String mPathToListen) {
        this.mPathToListen = mPathToListen;
    }

    public void startWatching(String pPathToListen) {

        try {

            setPathToListen(pPathToListen);

            // Creates a instance of WatchService.
            WatchService watcher = FileSystems.getDefault().newWatchService();

            // Registers the logDir below with a watch service.
            Path logDir = Paths.get(getPathToListen());

            //Register for the events for create, update, delete
            logDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            logger.debug("Starting to listen to file changes at: " + getPathToListen());

            //Loop forever, ugly way to just wait, but I'm okay with it for now
            while (true) {

                WatchKey key = watcher.take();

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (StandardWatchEventKinds.ENTRY_CREATE.equals(kind)) {
                        fileHasBeenCreated(event.context().toString());
                    } else if (StandardWatchEventKinds.ENTRY_MODIFY.equals(kind)) {
                        fileHasBeenUpdated(event.context().toString());
                    } else if (StandardWatchEventKinds.ENTRY_DELETE.equals(kind)) {
                        fileHasBeenDeleted(event.context().toString());
                    }
                }

                key.reset();

            }

        } catch (IOException | InterruptedException e) {

            logger.error(e);

        }

    }

    abstract public void fileHasBeenCreated(String pResult);

    abstract public void fileHasBeenUpdated(String pResult);

    abstract public void fileHasBeenDeleted(String pResult);

}
