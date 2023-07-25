package reflection;

import reflection.api.Investigator;

import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Set;

public class MyInvestigator implements Investigator {

    private Class m_aClass;
    private Object m_Instance;
    @Override
    public void load(Object anInstanceOfSomething) {
        m_aClass = anInstanceOfSomething.getClass();
        m_Instance = anInstanceOfSomething;
    }

    @Override
    public int getTotalNumberOfMethods() {
        return m_aClass.getMethods().length;
    }

    @Override
    public int getTotalNumberOfConstructors() {
        return m_aClass.getConstructors().length;
    }

    @Override
    public int getTotalNumberOfFields() {
        return m_aClass.getFields().length;
    }

    @Override
    public Set<String> getAllImplementedInterfaces() {
        Class[] implementedInterfaces = m_aClass.getInterfaces();
        Set<String> interfaceNamesSet = new HashSet<>();
        for (Class implementedInterface : implementedInterfaces)
        {
            interfaceNamesSet.add(implementedInterface.getSimpleName());
        }
        return interfaceNamesSet;
    }

    @Override
    public int getCountOfConstantFields() {
        Field[] allFiledsOfMyInstance = m_aClass.getDeclaredFields();
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
        Field[] allFiledsOfMyInstance = m_aClass.getDeclaredFields();
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
        if (m_aClass.getSuperclass() == Object.class)
            return false;
        else
            return true;
    }

    @Override
    public String getParentClassSimpleName() {
        if (m_aClass == Object.class)
            return null;
        else
            return m_aClass.getSuperclass().getSimpleName();
    }

    @Override
    public boolean isParentClassAbstract() {
        //does not have a parent
        if (m_aClass == Object.class)
            return false;
        // the parent is abstract
        if (Modifier.isAbstract(m_aClass.getSuperclass().getModifiers()))
            return true;
        // otherwise..
        return false;
    }

    @Override
    public Set<String> getNamesOfAllFieldsIncludingInheritanceChain() {
        Field[] myClassFields = m_aClass.getDeclaredFields();
        Class currClassInInheritenceChain = m_aClass;
        Set<String> fieldsNameSet = new HashSet<>();
        while (currClassInInheritenceChain  != null)
        {
            for (Field someField : myClassFields) {
                fieldsNameSet.add(someField.getName());
            }
            currClassInInheritenceChain = currClassInInheritenceChain.getSuperclass();
            myClassFields = currClassInInheritenceChain.getDeclaredFields();
        }
        return fieldsNameSet;
    }

    @Override
    public int invokeMethodThatReturnsInt(String methodName, Object... args) {
        int resultFromMethod;
        try {
            Method func = m_aClass.getMethod(methodName);
            if (args.length == 0)
                resultFromMethod = (int)func.invoke(m_Instance);
            else
                resultFromMethod = (int)func.invoke(m_Instance, args);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return resultFromMethod;
    }

    @Override
    public Object createInstance(int numberOfArgs, Object... args) {
        Constructor[] allConstructors = m_aClass.getConstructors();
        Object resInstance = null;
        for (Constructor ctor : allConstructors) {
            if (ctor.getParameterCount() == args.length) {
                try {
                    resInstance = ctor.newInstance(args);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return resInstance;
    }

    @Override
    public Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args) {
        Object resObject = null;
        try {
            Method method = m_aClass.getDeclaredMethod(name, parametersTypes);
            method.setAccessible(true);
            resObject = method.invoke(m_Instance, args);
            method.setAccessible(false);

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return  resObject;
    }

    @Override
    public String getInheritanceChain(String delimiter) {
        StringBuilder chain = new StringBuilder();
        Class currentClass = m_aClass;
        // using insert in first index make it reversed from Object to our Class
        while (currentClass != null) {
            if (chain.length() > 0) {
                chain.insert(0, delimiter);
            }
            chain.insert(0, currentClass.getSimpleName());

            currentClass = currentClass.getSuperclass();
        }

        return chain.toString();
    }
}
