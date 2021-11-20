package main;

import command.RunnableCommand;
import framework.application.Application;
import framework.state.ApplicationState;
import state.LaboratoryState;

public class Main {

    private static final String PROPERTY_PATH_STRING = "/laboratory.properties";

    public static void main(String[] args) {
        ApplicationState state = new LaboratoryState();
        Application application = new Application.ApplicationBuilder(PROPERTY_PATH_STRING, state)
                .addCommand("run", new RunnableCommand())
                .build();
        application.start();
    }

}
