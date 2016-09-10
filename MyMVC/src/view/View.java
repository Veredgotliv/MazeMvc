package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;

public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze (Maze3d maze);
	void setCommands(HashMap<String, Command> commands);
}