package comp5216.sydney.edu.au.focuson;

import android.net.Uri;

import java.io.File;

/**
 * The interface My firebase interface.
 */
public interface MyFirebaseInterface {
    /**
     * Upload file.
     *
     * @param uri the uri
     * @param arg the arg
     */
    void uploadFile(Uri uri, int arg);

    /**
     * Download file file.
     *
     * @param fileName the file name
     * @param arg      the arg
     * @return the file
     */
    File downloadFile(String fileName, int arg);

}
