package packetapp.DataAccessLayer;

import packetapp.Model.PacketLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogDataBase {

    private static List<PacketLog> LOG_DATABASE;

    public static void addPacketLog(PacketLog packetLog)
    {
        LOG_DATABASE.add(packetLog);
    }

    public static List<PacketLog> getLogDatabase()
    {
        return Collections.unmodifiableList(LOG_DATABASE);
    }

    static {
        LOG_DATABASE = new ArrayList<>();
    }
}
