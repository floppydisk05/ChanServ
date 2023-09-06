package me.diskfloppy.chanserv.listeners;

import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class ReadyHandler extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        SelfUser bot = event.getJDA().getSelfUser();
        LoggerFactory.getLogger(ReadyHandler.class).info("Logged in as " + bot.getName());
    }
}
