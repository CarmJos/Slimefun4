package com.xzavier0722.mc.plugin.slimefun4.chat;

import com.xzavier0722.mc.plugin.slimefun4.chat.listener.PlayerChatListener;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerChatCatcher {
    private final Map<UUID, Consumer<String>> catchers;

    public PlayerChatCatcher(Slimefun plugin) {
        catchers = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), plugin);
    }

    public void scheduleCatcher(UUID pUuid, Consumer<String> handler) {
        catchers.put(pUuid, handler);
    }

    public Optional<Consumer<String>> pollCatcher(UUID pUuid) {
        return Optional.ofNullable(catchers.remove(pUuid));
    }
}
