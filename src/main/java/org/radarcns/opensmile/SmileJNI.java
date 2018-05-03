/*
 Copyright (c) 2015 audEERING UG. All rights reserved.

 Date: 17.08.2015
 Author(s): Florian Eyben
 E-mail:  fe@audeering.com

 This is the interface between the Android app and the openSMILE binary.
 openSMILE is called via SMILExtractJNI()
 Messages from openSMILE are received by implementing the SmileJNI.Listener interface.
*/

package org.radarcns.opensmile;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.util.Log;

import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Timer;
import java.util.TimerTask;

public class SmileJNI {
    public static final String[] assets = {
            "ComParE_2016.conf",
            "ComParE_2016_core.func.conf.inc",
            "ComParE_2016_core.lld.conf.inc",
            "shared/arff_targets.conf.inc",
            "shared/BufferMode.conf.inc",
            "shared/BufferModeLive.conf.inc",
            "shared/BufferModeRb.conf.inc",
            "shared/BufferModeRbLag.conf.inc",
            "shared/FrameModeFunctionals.conf.inc",
            "shared/FrameModeFunctionalsLive.conf.inc",
            "shared/standard_data_output.conf.inc",
            "shared/standard_data_output_lldonly.conf.inc",
            "shared/standard_wave_input.conf.inc"
    };

    public interface ThreadListener {
        void onFinishedRecording();
    }

    String conf;
    String recordingPath;
    ThreadListener listener = null;
    private static boolean isRecording = false;
    public static boolean allowRecording = true;
    public static String lastRecording = "";


    /**
     * load the JNI interface
     */
    static {
        System.loadLibrary("smile_jni");
    }

    /**
     * method to execute openSMILE binary from the Android app activity, see smile_jni.cpp.
     *
     * @param configFile configuration file name
     * @param updateProfile whether to update the audio profile
     * @param outputfile outputfile that openSMILE should write to
     * @return
     */
    public static native String SMILExtractJNI(String configFile, int updateProfile, String outputfile);

    public static native String SMILEndJNI();

    public static void  prepareOpenSMILE(Context c){
        setupAssets(c);
    }

    public void runOpenSMILE(String conf, String recordingPath, long second) {
        this.conf = conf;
        this.recordingPath = recordingPath;

        final SmileThread obj = new SmileThread();
        final Thread newThread = new Thread(obj);
        newThread.start();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isRecording)
                    stopOpenSMILE();
                isRecording = false;
            }
        }, second * 1000);
    }

    public static boolean getIsRecording(){
        return isRecording;
    }

    public void addListener(ThreadListener listener) {
        this.listener = listener;
    }

    public void stopOpenSMILE() {
        SmileJNI.SMILEndJNI();
    }

    class SmileThread implements Runnable {
        //String  conf = getCacheDir()+"/liveinput_android.conf";
        private volatile boolean paused = false;
        private final Object signal = new Object();

        @Override
        public void run() {
            //String recordingPath = getExternalFilesDir("") + "/test50.bin";
            File output = new File(recordingPath);
            lastRecording = recordingPath;
            if(output.exists()){
                output.delete();
            }
            if(allowRecording) {
                isRecording = true;
                try {
                    SmileJNI.SMILExtractJNI(conf, 1, recordingPath);
                }
                catch (Exception e){
                    lastRecording = "Error in openSMILE!";
                }
                isRecording = false;
                listener.onFinishedRecording();
                /*try {
                    //byte[] b = IOUtil.readFile(recordingPath);
                    /*final String b64 = Base64.encodeToString(b,Base64.DEFAULT);
                    String dataPathText = getExternalFilesDir("") + "/test50.txt";
                    try {
                        File f = new File(dataPathText);
                        FileOutputStream fos = new FileOutputStream(f);
                        PrintWriter pw = new PrintWriter(fos);
                        pw.print(b64);
                        pw.flush();
                        pw.close();
                        fos.close();
                        ///OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().openFileOutput("test50.txt", Context.MODE_PRIVATE));
                        //outputStreamWriter.write(b64);
                        //outputStreamWriter.close();
                    }
                    catch (IOException e) {
                        Log.e("Exception", "File write failed: " + e.toString());
                    }*
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        }
    }

    /**
     * process the messages from openSMILE (redirect to app activity etc.)
     */
    public interface Listener {
        void onSmileMessageReceived(String text);
    }

    private static Listener listener_;

    public static void registerListener(Listener listener) {
        listener_ = listener;
    }

    /**
     * this is the first method called by openSMILE binary. it redirects the call to the Android
     * app activity.
     *
     * @param text JSON encoded string
     */
    static void receiveText(String text) {
        if (listener_ != null)
            listener_.onSmileMessageReceived(text);
    }

    static void setupAssets (Context c){
        //SHOULD BE MOVED TO: /data/user/0/org.radarcns.opensmile/cache/
        ContextWrapper cw = new ContextWrapper(c);
        String confcach = cw.getCacheDir() + "/" ;//+ conf.mainConf;

        AssetManager assetManager = c.getAssets();

        for(String filename : assets) {
            String out = confcach + filename;
            File outFile = new File(out);
            outFile.getParentFile().mkdirs();

            try (InputStream in = assetManager.open(filename);
                 FileOutputStream outst = new FileOutputStream(outFile)) {
                IOUtils.copy(in, outst);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
        }
    }
}
