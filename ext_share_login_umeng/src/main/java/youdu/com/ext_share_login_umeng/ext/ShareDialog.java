package youdu.com.ext_share_login_umeng.ext;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import youdu.com.ext_share_login_umeng.R;

/**
 * @author YonStone
 * @date 2019/10/08
 * @desc
 */
public class ShareDialog extends Dialog {
    public ShareDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share_layout);
    }
}
