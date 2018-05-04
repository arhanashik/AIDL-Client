// IAdd.aidl
package com.w3engineers.core;

// Declare any non-default types here with import statements
import com.w3engineers.core.Person;

interface IAdd {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int addNumbers(int num1, int num2);
    List<String> getStringList();
    Person getPerson();
    List<Person> getPersonList();
    void placeCall(String number);
}
