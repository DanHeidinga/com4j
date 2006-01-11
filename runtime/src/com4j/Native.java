package com4j;




/**
 * Native methods implemented in the dll.
 *
 * @author Kohsuke Kawaguchi (kk@kohsuke.org)
 */
class Native {

    /**
     * Initializes the native code.
     */
    static native void init();

    /**
     * Creates a COM object and returns its pointer.
     */
    static native int createInstance( String clsid, int clsctx, long iid1, long iid2 );

    /**
     * Calls <tt>AddRef</tt>.
     */
    static native void addRef( int pComObject );

    /**
     * Calls <tt>Release</tt>.
     */
    static native void release( int pComObject );

    /**
     * Invokes a method.
     *
     * @throws ComException
     *      if the invocation returns a failure HRESULT, and the return type
     *      is not HRESULT.
     */
    static native Object invoke( int pComObject, int vtIndex,
         Object[] args, int[] parameterConversions,
         Class returnType, int returnIndex, boolean returnIsInOut, int returnConversion );

    /**
     * Gets the error info.
     *
     * <p>
     * This method is used after the <tt>invoke</tt> method fails,
     * to obtain the <tt>IErrorInfo</tt> object. This method checks
     * <tt>ISupportErrorInfo</tt>.
     *
     * @param pComObject
     *      The object that caused an error.
     * @return
     *      the pointer to <tt>IErrorInfo</tt> or null if not available.
     */
    static native int getErrorInfo( int pComObject, long iid1, long iid2 );

    static IErrorInfo getErrorInfo( int pComObject, Class<? extends Com4jObject> _interface ) {
        GUID guid = COM4J.getIID(_interface);
        int p = getErrorInfo(pComObject,guid.v[0],guid.v[1]);
        if(p==0)    return null;
        else        return Wrapper.create(IErrorInfo.class,p);
    }

    static native int queryInterface( int pComObject, long iid1, long iid2 );

    static int queryInterface( int pComObject, GUID guid ) {
        return queryInterface(pComObject, guid.v[0], guid.v[1]);
    }

    /**
     * Loads a type library from a given file, wraps it, and returns its IUnknown.
     */
    static native int loadTypeLibrary(String name);

    /**
     * Calls "CoInitialize"
     */
    static native void coInitialize();
    /**
     * Calls "CoUninitialize"
     */
    static native void coUninitialize();
}
