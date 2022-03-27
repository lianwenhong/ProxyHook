package com.example.proxyhook;

import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ActivityHooker {

    /**
     * getMethod()：获取本身、父类以及接口中所有声明的public方法
     * getDeclaredMethod()：获取类自身的所有方法，包含public、protected、private，但是不能获取父类的方法
     * getDeclaredMethod()还有一个特点就是如果想获取父类public的方法，那么就重写该方法，其他方法修饰符方法无法获得。
     * 如果想获取父类的所有方法，则可以class.getSupperclass()，然后再去调用getDeclaredMethod()
     */

    /**
     * hook Context的startActivity方法
     */
    public static void hookContext() {

        try {
            Class mainThreadClz = Class.forName("android.app.ActivityThread");
            Method method = mainThreadClz.getDeclaredMethod("currentActivityThread");
            method.setAccessible(true);
            Object mainThread = method.invoke(null);

            Field field = mainThreadClz.getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) field.get(mainThread);

            HookInstrumentation hookInstrumentation = new HookInstrumentation(instrumentation);

            field.set(mainThread, hookInstrumentation);

        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
