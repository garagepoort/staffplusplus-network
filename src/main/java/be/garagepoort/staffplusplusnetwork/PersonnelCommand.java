package be.garagepoort.staffplusplusnetwork;

import be.garagepoort.staffplusplusnetwork.session.PlayerSettings;
import be.garagepoort.staffplusplusnetwork.session.PlayerSettingsSqlRepositoryImpl;
import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PersonnelCommand implements SimpleCommand {

    private final ProxyServer server;
    private final PlayerSettingsSqlRepositoryImpl playerSettingsSqlRepository;
    private final Toml config;

    public PersonnelCommand(ProxyServer server, PlayerSettingsSqlRepositoryImpl playerSettingsSqlRepository, Toml config) {
        this.server = server;
        this.playerSettingsSqlRepository = playerSettingsSqlRepository;
        this.config = config;
    }

    @Override
    public void execute(Invocation invocation) {
        Collection<Player> allPlayers = server.getAllPlayers();
        List<UUID> allUuids = allPlayers.stream()
            .filter(p -> p.hasPermission(config.getString("permissions.member")))
            .map(Player::getUniqueId)
            .collect(Collectors.toList());

        List<PlayerSettings> settings = playerSettingsSqlRepository.findSettings(allUuids);
        List<UUID> uuidsToHide = settings.stream().filter(PlayerSettings::isVanished).map(PlayerSettings::getUuid).collect(Collectors.toList());

        String playerList = allPlayers.stream()
            .filter(p -> !uuidsToHide.contains(p.getUniqueId())).
            map(Player::getUsername)
            .collect(Collectors.joining(","));
        invocation.source().sendMessage(Component.text(playerList).color(TextColor.color(255, 255, 0)));
    }
}
