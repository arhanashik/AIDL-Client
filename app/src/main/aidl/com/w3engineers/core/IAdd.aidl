// IAdd.aidl
package com.w3engineers.core;

// Declare any non-default types here with import statements

interface IAdd {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int addNumbers(int num1, int num2);
    List<String> getStringList();
    void placeCall(String number);
}
