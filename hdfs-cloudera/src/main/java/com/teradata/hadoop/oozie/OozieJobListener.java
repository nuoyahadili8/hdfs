package com.teradata.hadoop.oozie;

import org.apache.hadoop.conf.Configuration;
import org.apache.oozie.event.*;
import org.apache.oozie.event.listener.JobEventListener;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/5/1/001 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class OozieJobListener extends JobEventListener {
    @Override
    public void init(Configuration conf) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onWorkflowJobEvent(WorkflowJobEvent wje) {

    }

    @Override
    public void onWorkflowActionEvent(WorkflowActionEvent wae) {

    }

    @Override
    public void onCoordinatorJobEvent(CoordinatorJobEvent cje) {

    }

    @Override
    public void onCoordinatorActionEvent(CoordinatorActionEvent cae) {

    }

    @Override
    public void onBundleJobEvent(BundleJobEvent bje) {

    }
}
