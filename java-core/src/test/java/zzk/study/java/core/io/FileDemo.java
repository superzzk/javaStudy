package zzk.study.java.core.io;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileDemo {

    @Test
    public void setLastModified() throws ParseException, IOException {
        // Create an object of the File class
        // Replace the file path with path of the file
        // who's "last modified" date you want to change
        String dir = System.getProperty("user.home") + "/test_file";
        System.out.println(dir);

        File file = new File(dir);
        System.out.println(file.lastModified());

        System.out.println(file.createNewFile());
        System.out.println(file.lastModified());

        // Create an object of the SimpleDateFormat class
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");

        // Print the current "last modified" date of the file
        System.out.println("Original Last Modified Date : " + sdf.format((long) file.lastModified()));

        // Create a string containing a new date that has to be set as "last modified" date
        String newLastModified = "10/10/2020";

        // Convert this new date into milliseconds format (in long type)
        Date newDate = sdf.parse(newLastModified);

        // Set the new date as the "last modified" date of the our file
        file.setLastModified(newDate.getTime());

        // Print the updated "last modified" date
        System.out.println("Latest Last Modified Date : " + sdf.format(file.lastModified()));

        file.delete();
    }

}
