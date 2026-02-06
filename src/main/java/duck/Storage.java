package duck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import java.util.Random;

/**
 * Class which interfaces with hard disk (by writing to a txt file)
 */
public class Storage {
    private Path folderPath;
    private Path filePath;
    private Path filePathCheer;

    /**
     * Constructor Class to define directories
     * Calls onStartup() method to initialise all required files
     * @param home current directory (e.g. src/main/java)
     */
    public Storage(String home){
        this.folderPath = Paths.get(home, "data");
        this.filePath = this.folderPath.resolve("duck.txt");
        this.filePathCheer = this.folderPath.resolve("cheer.txt");
        onStartup();
    }

    /**
     * Create empty Directory and File to store tasks in Hard Disk
     */
    public void onStartup() {
        // check if file exists
        boolean directoryExists = Files.exists(this.folderPath);
        if (!directoryExists) {
            // directory does not exist -- create folder
            try {
                Files.createDirectories(this.folderPath);
            } catch (IOException folderError) {
                System.out.println("Unable to create directory: " + folderError.getMessage());
            }
        }
        boolean fileExists = Files.exists(this.filePath);
        if (!fileExists) {
            // file does not exist -- create file
            try {
                System.out.println("CREATING FILE");
                Files.createFile((this.filePath));
            } catch (IOException fileError) {
                System.out.println("Unable to create file: " + fileError.getMessage());
            }
        }
        System.out.println("FILE SETUP COMPLETE -- DUCK.TXT file in data folder");
    }

    /**
     * Stores hard disk data to current sessions's MasterList
     */
    public List<String> load() throws DuckException {
        try {
            return Files.readAllLines(this.filePath);
        } catch (IOException loadError) {
            throw new DuckException("No Existing Task Data");
        }
    }


    /**
     * Helper Function to add new task entry to hard disk
     * Called by todoMethod, deadlineMethod, eventMethod
     * @param content appends this string to hard disk
     */
    public void addToFile(String content){
        try {
            // Write content to file
            Files.writeString(this.filePath, content, StandardOpenOption.APPEND);
        } catch (IOException appendError) {
            System.out.println("Unable to append to file: " + appendError.getMessage());
        }
    }

    /**
     * Helper function to delete task entry from hard disk
     * Called by deleteItemMethod(command, MasterList)
     * @param lineNumber deletes content at lineNumber from hard disk
     */
    public void deleteFromFile(int lineNumber){
        try {
            List<String> lines = Files.readAllLines(this.filePath);
            lines.remove(lineNumber);
            String totalStr = "";
            for (String line: lines) {
                totalStr = totalStr + line + '\n';
            }
            Files.writeString(this.filePath, totalStr);   // Overwrites by default
        } catch (IOException deleteError) {
            System.out.println("Unable to delete from file: " + deleteError.getMessage());
        }
    }

    /**
     * Helper function to edit (mark / unmark task as done) a task entry in hard disk
     * Called by markUnmarkItemMethod(command, MasterList)
     * @param lineNumber target lineNumber to change data
     * @param editedEntry new data to replace old data
     */
    public void editFile(int lineNumber, String editedEntry){
        try {
            List<String> lines = Files.readAllLines(this.filePath);
            lines.set(lineNumber, editedEntry);
            String totalStr = "";
            for (String line: lines) {
                totalStr = totalStr + line + '\n';
            }
            Files.writeString(this.filePath, totalStr);
        } catch (IOException editError) {
            System.out.println("Unable to edit file: " + editError.getMessage());
        }
    }

    public String cheer() throws DuckException, IOException {
        Random rand = new Random();
        if (Files.size(this.filePathCheer) == 0) {
            throw new DuckException("Error! Cheer File is Empty");
        } else {
            List<String> lines = Files.readAllLines(this.filePathCheer);
            int randomBoundedInt = rand.nextInt(lines.size());
            return lines.get(randomBoundedInt);
        }

    }

}
