package ru.otus.hw.services;

import org.springframework.stereotype.Service;
import ru.otus.hw.models.SystemInfo;

@Service
public class SystemInfoService {

    public SystemInfo getSystemInfo(){
        String osName = System.getProperty("os.name");
        String timeZone = System.getProperty("user.timezone");
        String osArch = System.getProperty("os.arch");
        int processorsCount = Runtime.getRuntime().availableProcessors();
        return new SystemInfo(osName, timeZone, osArch, processorsCount);

    }
}
