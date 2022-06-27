package be.garagepoort.staffplusplusnetwork.velocity;

import be.garagepoort.mcioc.configuration.ConfigProperty;
import be.garagepoort.mcioc.load.InjectTubingPlugin;
import be.garagepoort.mcioc.tubingvelocity.annotations.IocVelocityCommandHandler;
import be.garagepoort.staffplusplusnetwork.common.SppPlayer;
import be.garagepoort.staffplusplusnetwork.common.services.PersonnelService;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@IocVelocityCommandHandler("config:commands.personnel")
public class PersonnelCommand implements SimpleCommand {

    @ConfigProperty("permissions.member")
    private String memberPermission;

    private final VelocityStaffPlusPlus tubingVelocityPlugin;
    private final PersonnelService personnelService;

    public PersonnelCommand(@InjectTubingPlugin VelocityStaffPlusPlus tubingVelocityPlugin, PersonnelService personnelService) {
        this.tubingVelocityPlugin = tubingVelocityPlugin;
        this.personnelService = personnelService;
    }

    @Override
    public void execute(Invocation invocation) {
        List<SppPlayer> allPlayers = tubingVelocityPlugin.getProxyServer().getAllPlayers().stream().map(VelocitySppPlayer::new).collect(Collectors.toList());
        Map<String, List<SppPlayer>> playerList = personnelService.getStaffOnServers(allPlayers, memberPermission);

        TextComponent.Builder textComponent = Component.empty().toBuilder();
        playerList.forEach((serverName, players) -> {
            textComponent.append(Component.text(serverName).color(TextColor.color(255, 255, 0)));
            for (SppPlayer player : players) {
                textComponent.append(Component.text(player.getUsername()).color(TextColor.color(255, 255, 0)));
            }
        });
        invocation.source().sendMessage(textComponent.build());
    }
}
