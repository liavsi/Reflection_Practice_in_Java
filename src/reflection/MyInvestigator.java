package reflection;

import reflection.api.Investigator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyInvestigator implements Investigator {

    private Class m_Instance;
    @Override
    public void load(Object anInstanceOfSomething) {
        m_Instance = anInstanceOfSomething.getClass();
    }

    @Override
    public int getTotalNumberOfMethods() {
        return m_Instance.getMethods().length;
    }

    @Override
    public int getTotalNumberOfConstructors() {
        return m_Instance.getConstructors().length;
    }

    @Override
    public int getTotalNumberOfFields() {
        return m_Instance.getFields().length;
    }

    @Override
    public Set<String> getAllImplementedInterfaces() {
        Class[] implementedInterfaces = m_Instance.getInterfaces();
        Set<String> interfaceNamesSet = new HashSet<>();
        for (Class implementedInterface : implementedInterfaces)
        {
            interfaceNamesSet.add(implementedInterface.getSimpleName());
        }
        return interfaceNamesSet;
    }

    @Override
    public int getCountOfConstantFields() {
        Field[] allFiledsOfMyInstance = m_Instance.getDeclaredFields();
        int counterOfFinalFields = 0;
        for (Field currField : allFiledsOfMyInstance){
            if (Modifier.isFinal(currField.getModifiers())) {
                counterOfFinalFields++;
            }
        }
        return counterOfFinalFields;
    }

    @Override
    public int getCountOfStaticMethods() {
        Field[] allFiledsOfMyInstance = m_Instance.getDeclaredFields();
        int counterOfStaticFields = 0;
        for (Field currField : allFiledsOfMyInstance){
            if (Modifier.isStatic(currField.getModifiers())) {
                counterOfStaticFields++;
            }
        }
        return counterOfStaticFields;
    }

    @Override
    public boolean isExtending() {
        if (m_Instance.getSuperclass() == Object.class)
            return false;
        else
            return true;
    }

    @Override
    public String getParentClassSimpleName() {
        if (m_Instance == Object.class)
            return null;
        else {
            return m_Instance.getSuperclass().getSimpleName();
        }
    }

    @Override
    public boolean isParentClassAbstract() {
        return false;
    }

    @Override
    public Set<String> getNamesOfAllFieldsIncludingInheritanceChain() {
        return null;
    }

    @Override
    public int invokeMethodThatReturnsInt(String methodName, Object... args) {
        return 0;
    }

    @Override
    public Object createInstance(int numberOfArgs, Object... args) {
        return null;
    }

    @Override
    public Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args) {
        return null;
    }

    @Override
    public String getInheritanceChain(String delimiter) {
        return null;
    }
}
