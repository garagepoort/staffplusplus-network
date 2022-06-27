package be.garagepoort.staffplusplusnetwork.network;

import be.garagepoort.mcioc.configuration.ConfigProperty;
import be.garagepoort.mcioc.load.InjectTubingPlugin;
import be.garagepoort.mcioc.tubingvelocity.annotations.IocVelocityCommandHandler;
import be.garagepoort.staffplusplusnetwork.network.common.SppPlayer;
import be.garagepoort.staffplusplusnetwork.network.common.services.PersonnelService;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.List;
import java.util.stream.Collectors;

@IocVelocityCommandHandler("config:commands.personnel")
public class PersonnelCommand implements SimpleCommand {

    private final StaffPlusPlusVelocity tubingVelocityPlugin;
    private final PersonnelService personnelService;
    @ConfigProperty("permissions.command.personnel")
    private String personnelPermission;

    public PersonnelCommand(@InjectTubingPlugin StaffPlusPlusVelocity tubingVelocityPlugin, PersonnelService personnelService) {
        this.tubingVelocityPlugin = tubingVelocityPlugin;
        this.personnelService = personnelService;
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission(personnelPermission);
    }

    @Override
    public void execute(Invocation invocation) {
        List<SppPlayer> allPlayers = tubingVelocityPlugin.getProxyServer().getAllPlayers().stream().map(VelocitySppPlayer::new).collect(Collectors.toList());

        String personnelText = personnelService.getPersonnelText(allPlayers);
        TextComponent result = LegacyComponentSerializer.legacyAmpersand().deserialize(personnelText);
        invocation.source().sendMessage(result);
    }
}
