package fr.overcraftor.mmo.utils.guilds;

import java.util.ArrayList;
import java.util.UUID;

public class Guild {

    private final String name, tag;
    private final ArrayList<UUID> players;
    private final UUID owner;

    public Guild(String name, String tag, ArrayList<UUID> players, UUID owner) {
        this.name = name;
        this.tag = tag;
        this.players = players;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public ArrayList<UUID> getPlayers() {
        return players;
    }

    public UUID getOwner() {
        return owner;
    }
}
