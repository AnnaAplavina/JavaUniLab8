package logic.commands;

import connection.MusicBandResponse;

public interface ChangingCollectionCommand {
    /**
     * @return response with changed data for all connected clients, null if update is not needed
     */
    MusicBandResponse getUpdateResponse();
}
