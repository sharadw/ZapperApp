package com.zapper.assessment.utils;

/**
 * Callback Interface for Permission Listener
 */

public interface PermissionRequestCallbackListener
{
    int PERMISSION_GRANTED = 1, PERMISSION_CANCELLED = 2;

    void getPermissionData(int permissionAction, int requestCode);
}
