package org.example;

import java.util.List;

public class MultiFolderImpl extends SimpleFolder implements MultiFolder {
    private final List<Folder> folders;

    public MultiFolderImpl(String name, String size, List<Folder> folders) {
        super(name, size);
        this.folders = folders;
    }

    @Override
    public List<Folder> getFolders() {
        return folders;
    }
}
