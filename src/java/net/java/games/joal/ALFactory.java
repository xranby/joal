/**
* Copyright (c) 2003 Sun Microsystems, Inc. All  Rights Reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* -Redistribution of source code must retain the above copyright notice, 
* this list of conditions and the following disclaimer.
*
* -Redistribution in binary form must reproduce the above copyright notice, 
* this list of conditions and the following disclaimer in the documentation
* and/or other materials provided with the distribution.
*
* Neither the name of Sun Microsystems, Inc. or the names of contributors may 
* be used to endorse or promote products derived from this software without 
* specific prior written permission.
* 
* This software is provided "AS IS," without a warranty of any kind.
* ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
* ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
* NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN") AND ITS
* LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A
* RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
* IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT
* OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
* PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
* ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
* BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
*
* You acknowledge that this software is not designed or intended for use in the
* design, construction, operation or maintenance of any nuclear facility.
*/

package net.java.games.joal;

/**
 * This class provides factory methods for generating AL and ALC objects. The
 * class must be initialized before use, and should be deinitialized when OpenAL
 * functionality is no longer needed to free up native resources. 
 *
 * @author Athomas Goldberg
 */
public class ALFactory {
    static {
        System.loadLibrary("joal");
    }

    private static boolean isInitialized;
    private static ALImpl al;
    private static ALC alc;

    /**
     * Initialize the OpenAL environment
     *
     * @return true is OpenAL was able to initialize,
     *         false if OpenAL was not able to intialize
     */
    public static boolean initialize() {
        String osProperty = System.getProperty("os.name");
        if(osProperty.startsWith("Win")) {
            isInitialized = init(new String[] { "OpenAL32.dll" });
        } else if(osProperty.startsWith("Linux")) {
            isInitialized = init(new String[] { "libopenal.so" });
        } else {
            // OSX            
        }
        return isInitialized;
    }

    private static native boolean init(String[] oalPaths);

    /**
     * Deinitialize the OpenAL environment
     *
     * @return true if OpenAL was able to be deinitialized,
     *         false if OpenAL uas unable to be deinitialized
     */
    public static native boolean deinitialize();

    /**
     * Get the default AL object. This object is used to access most of the
     * OpenAL functionality.
     *
     * @return the AL object
     */
    public static AL getAL() {
        if(!isInitialized) {
            initialize();
        }
        if (isInitialized && al == null) {
            al = new ALImpl();
        }
        return al;
    }

    /**
     * Get the default ALC object. This object is used to access most of the 
     * OpenAL context functionality.
     *
     * @return the ALC object
     */
    public static ALC getALC() {
        if(!isInitialized) {
            initialize();
        }
        if (isInitialized && alc == null) {
            alc = new ALCImpl();
        }
        return alc;
    }
}