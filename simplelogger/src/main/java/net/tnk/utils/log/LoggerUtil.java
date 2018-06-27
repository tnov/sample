/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tnk.utils.log;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import net.tnk.utils.log.impl.LoggingProperties;

/**
 *
 * @author fission
 */
public class LoggerUtil {

    // このシステムのプロパティファイル
    private static final Path INTERNAL_PATH = Paths.get(System.getProperty("user.dir") + File.separator + "logging.properties");

    private static final Path EXTERNAL_PATH = Paths.get("java.util.logging.config.file");

    // logging.properties modify time
    private static FileTime modifiedTime = null;

    public static boolean applyLoggerProperties() {
        // ファイルの更新日付が変わった場合に再読み込み
        try {
            if (modifiedTime == null || !Files.getLastModifiedTime(getPath(), LinkOption.NOFOLLOW_LINKS).equals(modifiedTime)) {
            	loadLoggerProperties();
                return true;
            }
        } catch (IOException ex) {
            // エラー時はログのみ出力
            Logger.getLogger(LoggerUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private static void loadLoggerProperties() {
        // 外部プロパティファイルが設定されている場合、それを最優先にする
        // それ以外はこのシステムのプロパティファイルを使用する
        Path path = getPath();
        if (Files.exists(path)) {
            setLoggerProperties(path);
        } else {
            System.out.println("creating... " + path);
            createLoggingProperties(path);
            setLoggerProperties(path);
        }
        // ログプロパティの更新日時を保持
        try {
            modifiedTime = Files.getLastModifiedTime(INTERNAL_PATH, LinkOption.NOFOLLOW_LINKS);
        } catch (IOException ex) {
            Logger.getLogger(LoggerUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Path getPath() {
        // JVM指定プロパティファイルパス(最優先)
        if (Files.exists(EXTERNAL_PATH)) {
            // 外部のプロパティファイルを使用
            System.out.println("loading default logger... " + Paths.get("java.util.logging.config.file"));
            return EXTERNAL_PATH;
        } else {
            // 所定のプロパティファイルを使用
            return INTERNAL_PATH;
        }
    }



    private static void createLoggingProperties(Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE_NEW)) {
            writer.write(new LoggingProperties().getLoggingProperties());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setLoggerProperties(Path path) {
        if (Files.exists(path)) {
            try {
                System.out.println("loading... " + path);
                LogManager.getLogManager().readConfiguration((new ByteArrayInputStream(Files.readAllLines(path, Charset.forName("UTF-8")).stream().collect(Collectors.joining(System.lineSeparator())).getBytes("UTF-8"))));
            } catch (SecurityException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
