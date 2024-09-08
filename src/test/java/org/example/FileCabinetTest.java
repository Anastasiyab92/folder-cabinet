package org.example;

import org.example.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FileCabinetTest {
    @Test
    public void testCountWithoutSubFolders(){
        Folder folder1 = new SimpleFolder("Folder1", "SIMPLE");
        Folder folder2 = new SimpleFolder("Folder2", "SIMPLE");

        FileCabinet cabinet = new FileCabinet(Arrays.asList(folder1,folder2));
        assertEquals(2,cabinet.count());

    }

    @Test
    public void testCountWithSubFolders(){
        Folder folder1 = new SimpleFolder("Folder1", "SIMPLE");
        Folder folder2 = new SimpleFolder("Folder2", "SIMPLE");

        MultiFolder multiFolder = new MultiFolderImpl("Folder3", "LARGE", Arrays.asList(folder1,folder2));
        FileCabinet cabinet = new FileCabinet(Collections.singletonList(multiFolder));
        assertEquals(3,cabinet.count());

    }

    @Test
    public void testCountEmptyFolders(){
        MultiFolder multiFolder = new MultiFolderImpl("EmptyFolder", "LARGE",Collections.emptyList());
        FileCabinet cabinet = new FileCabinet(Collections.singletonList(multiFolder));

        assertEquals(1,cabinet.count());

    }

    @Test
    public void testFindFolderByName(){
        Folder folder1 = new SimpleFolder("Folder1", "SIMPLE");
        Folder folder2 = new SimpleFolder("Folder2", "SIMPLE");
        MultiFolder multiFolder = new MultiFolderImpl("Folder3", "LARGE", Arrays.asList(folder1,folder2));

        FileCabinet cabinet = new FileCabinet(Collections.singletonList(multiFolder));
        Optional<Folder> result = cabinet.findFolderByName("Folder2");

        assertTrue(result.isPresent());
        assertEquals("Folder2", result.get().getName());
    }

    @Test
    public void testFindFolderByNameNotFound(){
        Folder folder1 = new SimpleFolder("Folder1", "SIMPLE");
        Folder folder2 = new SimpleFolder("Folder2", "SIMPLE");
        MultiFolder multiFolder = new MultiFolderImpl("Folder3", "LARGE", Arrays.asList(folder1,folder2));

        FileCabinet cabinet = new FileCabinet(Collections.singletonList(multiFolder));
        Optional<Folder> result = cabinet.findFolderByName("Folder4");

        assertFalse(result.isPresent());
    }

    @Test
    public void testFindFoldersBySize(){
        Folder folder1 = new SimpleFolder("Folder1", "SIMPLE");
        Folder folder2 = new SimpleFolder("Folder2", "MEDIUM");
        Folder folder3 = new SimpleFolder("Folder3", "LARGE");
        Folder folder4 = new SimpleFolder("Folder4", "SIMPLE");

        MultiFolder multiFolder = new MultiFolderImpl("Folder5", "LARGE", Arrays.asList(folder1,folder2));

        FileCabinet cabinet = new FileCabinet(Arrays.asList(folder3, folder4, multiFolder));

        List<Folder> simpleFolders = cabinet.findFoldersBySize("SIMPLE");
        assertEquals(2, simpleFolders.size());
        assertTrue(simpleFolders.stream().allMatch(folder -> "SIMPLE".equals(folder.getSize())));

        List<Folder> mediumFolders = cabinet.findFoldersBySize("MEDIUM");
        assertEquals(1, mediumFolders.size());
        assertTrue(mediumFolders.stream().allMatch(folder -> "MEDIUM".equals(folder.getSize())));

        List<Folder> largeFolders = cabinet.findFoldersBySize("LARGE");
        assertEquals(2, largeFolders.size());
        assertTrue(largeFolders.stream().allMatch(folder -> "LARGE".equals(folder.getSize())));

    }
}
