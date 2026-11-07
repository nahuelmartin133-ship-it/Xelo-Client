package com.origin.launcher.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LicenseCheck {
    public static boolean isMultiplayerAllowed(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("BreakingCraft", Context.MODE_PRIVATE);
        String code = prefs.getString("license_code", "");
        
        if (code.isEmpty()) return false;
        
        try {
            String[] parts = code.split("-");
            if (parts.length < 3) return false;
            
            String dateStr = parts[1]; 
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            Date expiryDate = sdf.parse(dateStr);
            Date today = new Date();
            
            return !today.after(expiryDate);
        } catch (Exception e) {
            return false;
        }
    }
}
