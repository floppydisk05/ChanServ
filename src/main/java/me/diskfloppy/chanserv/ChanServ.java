package me.diskfloppy.chanserv;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import io.github.cdimascio.dotenv.Dotenv;
import me.diskfloppy.chanserv.listeners.ReadyHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChanServ {
    private static JDA jda;
    public static EventWaiter waiter = new EventWaiter();
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // TODO: change config to .properties file or something
        Dotenv dotenv = Dotenv.load();
        
        CommandClientBuilder client = new CommandClientBuilder();
        client.setOwnerId("437970062922612737");
        client.setActivity(Activity.watching("the world burn"));
        
        client.useHelpBuilder(false);
        
        client.addSlashCommands(getSlashCommands());
        client.forceGuildOnly("1013928864868728872");
        
        jda = JDABuilder.createDefault(dotenv.get("DISCORD_TOKEN"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(waiter,
                        new ReadyHandler(),
                        client.build()
                )
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.playing("Booting..."))
                .build();
    }
    
    private static SlashCommand[] getSlashCommands() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Reflections reflections = new Reflections("me.diskfloppy.chanserv.commands");
        Set<Class<? extends SlashCommand>> subTypes = reflections.getSubTypesOf(SlashCommand.class);
        List<SlashCommand> commands = new ArrayList<>();
        
        for (Class<? extends SlashCommand> theClass : subTypes) {
            // Ignore if "SubCommand" is in the name
            if (theClass.getSimpleName().contains("SubCommand")) continue;
            
            commands.add(theClass.getDeclaredConstructor().newInstance());
            LoggerFactory.getLogger(theClass).debug("Loaded SlashCommand!");
        }
        return commands.toArray(new SlashCommand[0]);
    }
}