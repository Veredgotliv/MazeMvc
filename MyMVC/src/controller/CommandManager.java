package controller;

import java.io.File;

import java.util.HashMap;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Seracher;
import algorithms.search.Solution;
import model.Model;
import view.View;

// TODO: Auto-generated Javadoc
/**

 * <h1> The Class CommandManager. </h1>
 * <p>
 * This is a class that will manage all of the commands of the program <p>
 * it defines class of a command, and defines the DoCommand Override method for each command 
 *  <p>
 * @author NofarLevi
 * @since September 2016
 */
public class CommandManager {

	/** The model. */
	private Model model;

	/** The view. */
	private View view;

	/**
	 * Instantiates a new command manager.
	 *
	 * @param model
	 *            the model
	 * @param view
	 *            the view
	 */
	public CommandManager(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Gets the commands map.
	 *
	 * @return the commands map
	 */
	public HashMap<String, Command> getCommandsMap() {
		HashMap<String, Command> commands = new HashMap<String, Command>();
		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		commands.put("dir", new DirCommand());
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("save_maze", new SaveMazeCommand());
		commands.put("load_maze", new LoadMazeCommand());
		commands.put("solve_maze", new SolveMazeCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		commands.put("exit", new exit());
		return commands;
	}

	/**
	 * The Class GenerateMazeCommand.
	 */
	public class GenerateMazeCommand implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			
			// checking if the user entered the right num of parameter
			if (model.isGoodInput(4, args.length)) {
				String name = args[0];
				int floors = Integer.parseInt(args[1]);
				int rows = Integer.parseInt(args[2]);
				int cols = Integer.parseInt(args[3]);
				model.generateMaze(name, floors, rows, cols);
			}
		}
	}

	/**
	 * The Class DisplayMazeCommand.
	 */
	public class DisplayMazeCommand implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			
			// checking if the user entered the right num of parameter
			if (model.isGoodInput(1, args.length)) {
				String name = args[0];
				Maze3d maze = model.getMaze(name);
				view.displayMaze(maze);
			}
		}
	}

	/**
	 * The Class DirCommand.
	 */
	public class DirCommand implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			
			// checking if the user entered the right num of parameter
			if (model.isGoodInput(1, args.length)) {
				String paths = args[0];
				File folderPath = null;
				String[] filelist;

				try {
					// create new file
					folderPath = new File(paths);

					// array of files and directory
					filelist = folderPath.list();

					view.printAnswers(filelist);
				} catch (Exception e) {
					// if any error occurs
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * The Class DisplayCrossSectionCommand.
	 */
	public class DisplayCrossSectionCommand implements Command {

		/*
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			
			// checking if the user entered the right num of parameter
			if (model.isGoodInput(3, args.length)) {
				String name = args[0];
				String cross = args[1].toLowerCase(); // the dimension
				String index = args[2];
				Maze3d maze = model.getMaze(name);
				switch (cross) { // checking what dimension the cross will be
				case "z":
					view.printCrossSection(maze.getCrossSectionByZ(Integer.parseInt(index)));
					break;
				case "y":
					view.printCrossSection(maze.getCrossSectionByY(Integer.parseInt(index)));
					break;
				case "x":
					view.printCrossSection(maze.getCrossSectionByX(Integer.parseInt(index)));
					break;
				default:
					break;
				}

			}
		}
	}

	/**
	 * The Class SaveMazeCommand.
	 */
	public class SaveMazeCommand implements Command {

		/*
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			
			// checking if the user entered the right num of parameter
			if (model.isGoodInput(2, args.length)) {
				String name = args[0];
				String path = args[1];
				model.saveMaze(name, path);
			}
		}
	}

	/**
	 * The Class LoadMazeCommand.
	 */
	public class LoadMazeCommand implements Command {

		/*
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			
			// checking if the user entered the right num of parameter
			if (model.isGoodInput(2, args.length)) {
				String path = args[0];
				String name = args[1];
				model.loadMaze(path, name);
			}
		};
	}

	/**
	 * The Class SolveMazeCommand.
	 */
	public class SolveMazeCommand implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			// checking if the user entered the right num of parameter
			if (model.isGoodInput(2, args.length)) {
				String name = args[0];
				String algo = args[1];
				Seracher<Position> algorithm = null;
				switch (algo) {
				case "BFS":
					algorithm = new BFS<Position>();
					break;
				case "DFS":
					algorithm = new DFS<Position>();
					break;
				default:
					//print error msg
					break;
				}
				model.solveMaze(name, algorithm);
			}
		}
	}

	/**
	 * The Class DisplaySolutionCommand.
	 */
	public class DisplaySolutionCommand implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			// checking if the user entered the right num of parameter
			if (model.isGoodInput(1, args.length)) {

				String name = args[0];
				// bring the solution of the maze (according to the name) from
				// the
				// model, to sent to the view
				Solution<Position> sol = model.getSolution(name);
				
				// send the solution to the view, so it could be displayed for the end-user
				view.displaySolution(sol); 
			}
		}

	}

	/**
	 * The Class exit.
	 */
	public class exit implements Command {

		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] s) {
			view.notifyProgramIsAboutToEnd();
			model.exit();
		}

	}

}
