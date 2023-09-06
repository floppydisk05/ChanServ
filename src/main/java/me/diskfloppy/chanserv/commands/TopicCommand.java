package me.diskfloppy.chanserv.commands;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.Color;
import java.util.Collections;

public class TopicCommand extends SlashCommand {
    public TopicCommand() {
        this.name = "topic";
        this.help = "Sets the topic of the current channel (requires owner)";
        
        this.options = Collections.singletonList(
                new OptionData(OptionType.STRING, "topic", "The topic to set.").setRequired(true)
        );
    }
    
    @Override
    protected void execute(SlashCommandEvent event) {
        String currentTopic = event.getTextChannel().getTopic();
        if (currentTopic == null || currentTopic.isBlank()) {
            currentTopic = fixTopic(event.getTextChannel());
        }
        String mode = currentTopic.split(" ")[0];
        event.getTextChannel().getManager().setTopic(mode + " " + event.optString("topic")).queue(channel -> {
            event.replyEmbeds(new EmbedBuilder()
                    .setTitle(event.getUser().getName() + " set the topic")
                    .setDescription(event.optString("topic", ""))
                    .setColor(Color.GREEN).build()
            ).queue();
        });
    }

    public static String fixTopic(TextChannel channel) {
        if (channel.getTopic() == null) {
            channel.getManager().setTopic("[+nt]").queue();
            return "[+nt]";
        } else {
            channel.getManager().setTopic("[+nt] " + channel.getTopic()).queue();
            return "[+nt] " + channel.getTopic();
        }
    }

}
