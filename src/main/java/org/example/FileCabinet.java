package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FileCabinet implements Cabinet {
    List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return findFolderByNameRecursive(folders, folder -> folder.getName().equals(name));
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return findFoldersBySizeRecursive(folders, folder -> folder.getSize().equals(size));
    }


    @Override
    public int count() {
        return countFoldersRecursive(folders);
    }

    private Optional<Folder> findFolderByNameRecursive(List<Folder> folders, Predicate<Folder> condition) {
        for (Folder folder : folders) {
            if (condition.test(folder)) {
                return Optional.of(folder);
            }
            if (folder instanceof MultiFolder) {
                MultiFolder multiFolder = (MultiFolder) folder;
                Optional<Folder> result = findFolderByNameRecursive(multiFolder.getFolders(), condition);
                if (result.isPresent()) {
                    return result;
                }
            }
        }
        return Optional.empty();
    }

    private List<Folder> findFoldersBySizeRecursive(List<Folder> folders, Predicate<Folder> condition) {
        List<Folder> result = new ArrayList<>();

        for (Folder folder : folders) {
            if (condition.test(folder)) {
                result.add(folder);
            }

            if (folder instanceof MultiFolder) {
                MultiFolder multiFolder = (MultiFolder) folder;
                result.addAll(findFoldersBySizeRecursive(multiFolder.getFolders(), condition));
            }
        }
        return result;
    }

    private int countFoldersRecursive(List<Folder> folders) {
        int count = 0;

        for (Folder folder : folders) {
            count++;

            if (folder instanceof MultiFolder) {
                MultiFolder multiFolder = (MultiFolder) folder;
                count += countFoldersRecursive(multiFolder.getFolders());
            }
        }
        return count;
    }
}
