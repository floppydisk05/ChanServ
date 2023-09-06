package me.diskfloppy.chanserv.commands;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class TestCommand extends SlashCommand {
    public TestCommand() {
        this.name = "fuck";
        this.help = "sdfsFUCKING SJSHITI";
    }
    
    @Override
    protected void execute(SlashCommandEvent event) {
        event.reply("AAAAAA FUCK").queue();
    }
}
