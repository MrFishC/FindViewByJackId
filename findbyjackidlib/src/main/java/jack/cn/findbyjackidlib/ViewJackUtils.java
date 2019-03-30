package jack.cn.findbyjackidlib;

import android.app.Activity;
import android.view.View;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Jackyu
 * On 2019/3/11.
 * Describe:使用方法，类似xUtils
 *
 * 学习思路分为四步，目标在于提高对知识点的理解，若在项目中使用，需要好好考虑，手动搭建IOC注解框架的兼容性问题会比较差。：
 * 1.xUtils源码分析；
 * 2.ButterKnife源码分析和工作原理；
 * 3.在xml中定义OnClick方法为什么会执行只View的onclick源码查看；
 * 4.搭建IOC注解框架；
 */
public class ViewJackUtils {

    public static void inject(Activity activity) {
        inject(new ViewJackFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewJackFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewJackFinder(view), object);
    }

    // 兼容 上面三个方法  object --> 反射需要执行的类
    private static void inject(ViewJackFinder finder, Object object) {
        injectFiled(finder, object);
        injectEvent(finder, object);
    }

    /**
     * 注入方法
     */
    private static void injectEvent(ViewJackFinder finder, Object object) {
        // 1. 获取类里面所有的方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        // 2. 获取Onclick的里面的value值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    // 3. findViewById 找到View
                    View view = finder.findViewById(viewId);

                    // 扩展功能 检测网络
//                    boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;

                    if (view != null) {
                        // 4. view.setOnClickListener
                        view.setOnClickListener(new DeclaredOnClickListener(method, object));
                    }
                }
            }
        }
    }

    /**
     * 拷贝源码的逻辑
     */
    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Object mObject;
        private Method mMethod;

        public DeclaredOnClickListener(Method method, Object object) {
            this.mObject = object;
            this.mMethod = method;
        }

        @Override
        public void onClick(View v) {
            // 需不需要检测网络

            // 点击会调用该方法
            try {
                // 所有方法都可以 包括私有共有
                mMethod.setAccessible(true);
                // 5. 反射执行方法
                mMethod.invoke(mObject, v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject, null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 注入属性
     */
    private static void injectFiled(ViewJackFinder finder, Object object) {
        // 1. 获取类里面所有的属性
        Class<?> clazz = object.getClass();
        // 获取所有属性包括私有和共有
        Field[] fields = clazz.getDeclaredFields();

        // 2. 获取ViewById的里面的value值
        for (Field field : fields) {
            ViewJackById viewById = field.getAnnotation(ViewJackById.class);
            if (viewById != null) {
                // 获取注解里面的id值  --> R.id.xxx
                int viewId = viewById.value();
                // 3. findViewById 找到View
                View view = finder.findViewById(viewId);
                if (view != null) {
                    // 能够注入所有修饰符  private public
                    field.setAccessible(true);
                    // 4. 动态的注入找到的View
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
