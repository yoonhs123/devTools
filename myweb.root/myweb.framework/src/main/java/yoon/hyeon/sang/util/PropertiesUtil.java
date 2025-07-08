package yoon.hyeon.sang.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/// 서버 시작시 프로퍼티 파일을 로드합니다
public class PropertiesUtil {

    private static final Logger logger = LogManager.getLogger(PropertiesUtil.class);

    private static final String propertySubDir = "hsyoon_property";
    private static final Properties props = new Properties();
    private static boolean loaded = false;

    // 서버 시작 시 한번만 호출할 메서드
    public static synchronized void init() {
        if (loaded) return;
        loaded = true;

        String deployPath = System.getProperty("DEPLOY_PATH");
        if (deployPath == null || StringUtils.isBlank(deployPath)) {
            logger.error("DEPLOY_PATH VM 옵션이 설정되지 않았습니다.");
            throw new RuntimeException("DEPLOY_PATH VM 옵션이 설정되지 않았습니다.");
        }

        File dir = new File(deployPath, propertySubDir);
        if (!dir.exists() || !dir.isDirectory()) {
            logger.error("프로퍼티 디렉토리 없음: {}", dir.getAbsolutePath());
            throw new RuntimeException("프로퍼티 디렉토리 없음: " + dir.getAbsolutePath());
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".properties"));
        if (files == null || files.length == 0) {
            logger.error("해당 폴더에 properties 파일이 없습니다: {}", dir.getAbsolutePath());
            return;
        }

        for (File file : files) {
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
                Properties temp = new Properties();
                temp.load(reader);
                props.putAll(temp);
                logger.debug("프로퍼티파일 로드에 성공했습니다: {}", file.getName());
            } catch (Exception e) {
                logger.error("프로퍼티파일 로드에 실패했습니다: {}", file.getAbsolutePath(), e);
            }
        }
    }

    // getProperties 메서드들 - 로딩은 서버 시작 시 이미 되었으므로 그냥 반환
    public static String getProperties(String key) {
        if (!loaded) {
            throw new IllegalStateException("Properties not loaded. Call load() at startup.");
        }
        return props.getProperty(key);
    }

    public static String getProperties(String key, String defaultValue) {
        if (!loaded) {
            throw new IllegalStateException("Properties not loaded. Call load() at startup.");
        }
        return props.getProperty(key, defaultValue);
    }
}
