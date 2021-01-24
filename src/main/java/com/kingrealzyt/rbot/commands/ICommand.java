package com.kingrealzyt.rbot.commands;

import java.util.List;

public interface ICommand {

    void handle(com.kingrealzyt.rbot.commands.CommandContext event);

    String getName();

    default List<String> getAliases() {
        return List.of();
    }

}
