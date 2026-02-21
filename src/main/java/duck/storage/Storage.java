package duck.storage;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import duck.exception.StorageException;

/**
 * Class which interfaces with hard disk (by writing to a txt file).
 */
public class Storage {

    // Constants
    private static final String FOLDER_NAME = "data";
    private static final String FILE_NAME = "duck.txt";
    private static final String CHEER_FILE_NAME = "cheer.txt";


    // Instance Attributes
    private Path folderPath;
    private Path filePath;
    private Path filePathCheer;

    /**
     * Constructor Class to define directories.
     * Calls onStartup() method to initialise all required files.
     *
     * @param home current directory (e.g. src/main/java).
     */
    public Storage(String home) throws StorageException {
        this.folderPath = Paths.get(home, FOLDER_NAME);
        this.filePath = this.folderPath.resolve(FILE_NAME);
        this.filePathCheer = this.folderPath.resolve(CHEER_FILE_NAME);
        onStartup();
    }

    /**
     * Helper function to check and create files / folders
     *
     * @param filepath File/folder to be checked / created
     * @param isDirectory True if folder to be created, false if file to be created
     * @throws StorageException Error while creating File
     */
    private void checkAndCreateFileFolder(Path filepath, boolean isDirectory) throws StorageException {
        boolean fileExists = Files.exists(filepath);
        if (!fileExists) {
            // directory/file does not exist -- create folder/file
            try {
                if (isDirectory) {
                    Files.createDirectories(filepath);
                } else {
                    Files.createFile(filepath);
                }
            } catch (IOException fileError) {
                throw new StorageException("Unable to create directory/file: " + fileError.getMessage());
            }
        }
    }

    /**
     * Check if directory and file exists. If not, create empty directory and file.
     */
    public void onStartup() throws StorageException {
        checkAndCreateFileFolder(this.folderPath, true);
        checkAndCreateFileFolder(this.filePath, false);
        checkAndCreateFileFolder(this.filePathCheer, false);
    }

    /**
     * Store hard disk data to current session MasterList
     */
    public List<String> load() throws StorageException {
        try {
            return Files.readAllLines(this.filePath);
        } catch (IOException loadError) {
            throw new StorageException("No Existing Task Data");
        }
    }

    /**
     * Add new task entry to hard disk.
     * Called by todoMethod, deadlineMethod, eventMethod.
     *
     * @param content Entry to be appended to file.
     */
    public void addToFile(String content) throws StorageException {
        try {
            // Write content to file
            Files.writeString(this.filePath, content, StandardOpenOption.APPEND);
        } catch (IOException appendError) {
            throw new StorageException("Unable to append to file: " + appendError.getMessage());
        }
    }

    /**
     * Delete task entry from hard disk.
     * Called by deleteItemMethod(command, MasterList).
     *
     * @param lineNumber Delete content at lineNumber from hard disk.
     */
    public void deleteFromFile(int lineNumber) {
        try {
            List<String> lines = Files.readAllLines(this.filePath);
            lines.remove(lineNumber);
            Files.write(this.filePath, lines);
        } catch (IOException deleteError) {
            System.out.println("Unable to delete from file: " + deleteError.getMessage());
        }
    }

    /**
     * Mark / unmark a task entry in hard disk.
     * Called by markUnmarkItemMethod(command, MasterList).
     *
     * @param lineNumber Row number where task will be marked as done/undone.
     * @param editedEntry New data to replace existing data.
     */
    public void editFile(int lineNumber, String editedEntry) throws StorageException {
        try {
            List<String> lines = Files.readAllLines(this.filePath);
            lines.set(lineNumber, editedEntry);
            Files.write(this.filePath, lines);
        } catch (IOException editError) {
            throw new StorageException("Unable to edit file: " + editError.getMessage());
        }
    }

    private void addToCheerFile() throws IOException {
        List<String> cheers = new ArrayList<>();
        cheers.add("“ Those who can imagine anything, can create the impossible.” - Alan Turing");
        cheers.add("“ Logic will get you from A to Z; imagination will get you everywhere.” - Albert Einstein");
        cheers.add("“ Anyone who has never made a mistake has never tried anything new.” - Albert Einstein");
        cheers.add("“ Any sufficiently advanced technology is equivalent to magic.” - Arthur C. Clarke");
        Files.write(this.filePathCheer, cheers, StandardOpenOption.APPEND);
    }

    /**
     * Randomly selects motivational quote from cheer.txt.
     *
     * @return String --> the motivational quote
     * @throws IOException error if unable to open cheer.txt.
     */
    public String cheer() throws IOException {
        Random rand = new Random();
        if (Files.size(this.filePathCheer) == 0) {
            addToCheerFile();
        }
        List<String> lines = Files.readAllLines(this.filePathCheer);
        int randomBoundedInt = rand.nextInt(lines.size());
        return lines.get(randomBoundedInt);
    }
}
