package jack.cn.findbyjackidlib;

import android.app.Activity;
import android.view.View;

/**
 * Created by Jackyu
 * On 2019/3/11.
 * Describe:
 */
public class ViewJackFinder {

    private Activity mActivity;
    private View mView;

    public ViewJackFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewJackFinder(View view) {
        this.mView = view;
    }

    public View findViewById(int viewId) {
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}
