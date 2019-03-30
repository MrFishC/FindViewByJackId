package jack.cn.findbyjackidlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Jackyu
 * On 2019/3/11.
 * Describe:View事件注解的Annotation
 */
// @Target(ElementType.FIELD) 代表Annotation的位置  FIELD属性  TYPE类上  CONSTRUCTOR 构造函数上
// @Retention(RetentionPolicy.CLASS) 什么时候生效 CLASS编译时   RUNTIME运行时  SOURCE源码资源
@Target(ElementType.METHOD) @Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {
    // --> @ViewById(R.id.xxx)
    int[] value();
}
