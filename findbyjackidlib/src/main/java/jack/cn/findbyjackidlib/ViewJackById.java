package jack.cn.findbyjackidlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Jackyu
 * On 2019/3/11.
 * Describe:View属性注解类
 */

// RUNTIME 运行时检测，CLASS 编译时butterKnife使用是这个  SOURCE 源码资源的时候
// FIELD 注解只能放在属性上    METHOD 方法上  TYPE 类上  CONSTRUCTOR 构造方法上
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.FIELD)
public @interface ViewJackById {
    // 代表可以传值int类型  使用的时候：ViewJackById(R.id.xxx)
    int value();
}
