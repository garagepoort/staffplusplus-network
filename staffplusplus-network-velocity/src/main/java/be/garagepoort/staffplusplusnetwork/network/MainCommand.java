package be.garagepoort.staffplusplusnetwork.network;

import be.garagepoort.mcioc.configuration.ConfigProperty;
import be.garagepoort.mcioc.tubingvelocity.annotations.IocVelocityCommandHandler;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@IocVelocityCommandHandler("staffplusplus-network")
public class MainCommand implements SimpleCommand {

    @ConfigProperty("permissions.command.reload")
    private String reloadPermission;

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission(reloadPermission);
    }

    @Override
    public void execute(SimpleCommand.Invocation invocation) {
        String[] strings = invocation.arguments();
        if(strings.length == 0 || !strings[0].equalsIgnoreCase("reload")) {
            TextComponent result = LegacyComponentSerializer.legacyAmpersand().deserialize("&cInvalid use of command. Please add 'reload' to reload the plugin");
            invocation.source().sendMessage(result);
            return;
        }
        StaffPlusPlusVelocity.getPlugin().reload();
        TextComponent result = LegacyComponentSerializer.legacyAmpersand().deserialize("&6[staff++ network] &creloaded plugin");
        invocation.source().sendMessage(result);
    }
}
