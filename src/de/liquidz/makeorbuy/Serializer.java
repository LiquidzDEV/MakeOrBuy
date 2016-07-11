package de.liquidz.makeorbuy;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import de.liquidz.makeorbuy.tab.pool.PoolTab;
import de.liquidz.makeorbuy.tab.project.Project;

/**
 * Enthält Methoden die zum Laden und Speichern der Serialisierten Objekte zuständig sind.
 * 
 * @author Pascal
 */
public class Serializer {

	public static final String	PROJECT_DIR		= "data/projects";

	public static final String	STAFF_DIR		= "data/personal.prs";
	public static final String	MACHINE_DIR		= "data/maschinen.mch";
	public static final String	MATERIAL_DIR	= "data/materialien.mtr";
	public static final String	DELIVERER_DIR	= "data/lieferanten.lfr";

	public static void saveProject(Project project) {
		try {
			FileOutputStream fout = new FileOutputStream(Serializer.PROJECT_DIR + "/" + project.title + ".prj");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(project);
			oos.close();
			System.out.println("Projekt " + project.title + " gespeichert.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Project> loadProjects() {
		Map<String, Project> projects = new HashMap<>();
		if (!Files.isDirectory(Paths.get(Serializer.PROJECT_DIR))) {
			new File(Serializer.PROJECT_DIR).mkdirs();
		}
		for (File file : new File(Serializer.PROJECT_DIR).listFiles(new ProjectFileFilter())) {
			try {
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fin);
				Project project = (Project) ois.readObject();
				ois.close();
				projects.put(project.title, project);
				System.out.println("Projekt " + project.title + " geladen!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return projects;
	}

	public static void deleteProject(String projectName) {
		new File(Serializer.PROJECT_DIR + "/" + projectName + ".prj").delete();
	}

	public static void saveManager(String fileName, PoolTab managerPanel) {
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(managerPanel);
			oos.close();
			System.out.println(fileName + " gespeichert.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PoolTab loadManager(String fileName) {
		return null;
		// if (!Files.exists(Paths.get(fileName))) {
		// return null;
		// }
		// try {
		// FileInputStream fin = new FileInputStream(fileName);
		// ObjectInputStream ois = new ObjectInputStream(fin);
		// ManagerPanel managerPanel = (ManagerPanel) ois.readObject();
		// ois.close();
		// return managerPanel;
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return null;
	}

	/**
	 * Statische Klasse um .prj-Dateien raus zu filtern.
	 * 
	 * @author Pascal
	 */
	static class ProjectFileFilter implements FileFilter {

		@Override
		public boolean accept(File pathname) {
			if (pathname.getName().contains(".prj")) return true;
			return false;
		}
	}
}
