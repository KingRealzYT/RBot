package com.kingrealzyt.rbot;


import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.time.Instant;

public class Listener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        User user = event.getAuthor();

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        final long guildId = event.getGuild().getIdLong();
        String prefix = Config.PREFIX;
        String raw = event.getMessage().getContentRaw();

        if (raw.equalsIgnoreCase(prefix + "shutdown")
                && user.getId().equals(Config.OWNER_ID)) {
            event.getChannel().sendMessage("I am now shutting down.").queue();
            LOGGER.info("Shutting down");
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());
            return;
        }

        if (raw.startsWith(prefix)) {
            manager.handle(event, prefix);
        }

        String msg = event.getMessage().getContentRaw().toLowerCase();

        if (msg.contains("<@!794775670383050773>")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(0xd01212);
            eb.setTitle("CBot Info!");
            eb.setFooter("Requested by: " + event.getMessage().getAuthor().getAsTag());
            eb.setTimestamp(Instant.now());
            eb.addField("Version", "1.2A", true);
            eb.addField("License", "[GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html)", true);
            eb.addField("Current Prefix: ", "`" + prefix + "`", true);
            eb.addField("API", "[JDA](https://github.com/DV8FromTheWorld/JDA)", true);

            event.getChannel().sendMessage(eb.build()).queue();
        }
    }
}

