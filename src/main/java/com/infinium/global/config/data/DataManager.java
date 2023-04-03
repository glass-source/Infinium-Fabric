package com.infinium.global.config.data;

import com.infinium.Infinium;
import com.infinium.global.config.data.player.InfiniumPlayer;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.sanity.SanityManager;
import lombok.Getter;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class DataManager {

  private final Infinium infinium;
  private final InfiniumServerManager core;
  private final @Getter JsonConfig gameData;

  public DataManager(final Infinium infinium) throws Exception {
    this.infinium = infinium;
    this.core = this.infinium.getCore();
    this.gameData = JsonConfig.config("gameData.json");
  }

  public void save() {
    core.getTotalPlayers().forEach(serverPlayerEntity -> {
      var infPlayer = InfiniumPlayer.getInfiniumPlayer(serverPlayerEntity);
      infPlayer.save(infPlayer.getUserJson());
    });
  }

  public void restore() {
    core.getTotalPlayers().forEach(serverPlayerEntity -> {
      var infPlayer = InfiniumPlayer.getInfiniumPlayer(serverPlayerEntity);
      infPlayer.restore(infPlayer.getUserJson());
    });

  }

}
