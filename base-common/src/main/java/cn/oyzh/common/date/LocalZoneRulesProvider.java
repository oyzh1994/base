package cn.oyzh.common.date;

import cn.oyzh.common.SysConst;
import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.util.ReflectUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesException;
import java.time.zone.ZoneRulesProvider;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * 本地时区规则提供者，从本地 tzdb.dat 加载时区规则并缓存
 *
 * @author oyzh
 * @since 2024-09-27
 */
public class LocalZoneRulesProvider extends ZoneRulesProvider {

    /**
     * 时区数据版本标识
     */
    private String versionId;

    /**
     * 时区区域标识列表
     */
    private List<String> regionIds;

    /**
     * 构造并加载本地时区规则
     */
    public LocalZoneRulesProvider() {
        try {
            this.doLoad();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected Set<String> provideZoneIds() {
        return new HashSet<>(this.regionIds);
    }

    @Override
    protected ZoneRules provideRules(String zoneId, boolean forCaching) {
        if (!this.regionIds.contains(zoneId)) {
            throw new ZoneRulesException("Unknown time-zone ID: " + zoneId);
        }
        try {
            return this.readCache(zoneId);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    protected NavigableMap<String, ZoneRules> provideVersions(String zoneId) {
        TreeMap<String, ZoneRules> map = new TreeMap<>();
        ZoneRules rules = getRules(zoneId, false);
        if (rules != null) {
            map.put(this.versionId, rules);
        }
        return map;
    }

    /**
     * 从本地 tzdb.dat 加载时区数据并写入缓存
     *
     * @throws Exception 加载过程中的异常
     */
    private void doLoad() throws Exception {
        String libDir = System.getProperty("java.home") + File.separator + "lib";
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(libDir, "tzdb.dat"))));
        try (dis) {
            if (dis.readByte() != 1) {
                throw new StreamCorruptedException("File format not recognised");
            }
            // 分组
            String groupId = dis.readUTF();
            if (!"TZDB".equals(groupId)) {
                throw new StreamCorruptedException("File format not recognised");
            }
            // 版本
            int versionCount = dis.readShort();
            for (int i = 0; i < versionCount; i++) {
                this.versionId = dis.readUTF().intern();
            }
            // 区域
            int regionCount = dis.readShort();
            String[] regionArray = new String[regionCount];
            for (int i = 0; i < regionCount; i++) {
                regionArray[i] = dis.readUTF().intern();
            }
            this.regionIds = Arrays.asList(regionArray);
            // 规则
            int ruleCount = dis.readShort();
            Object[] ruleArray = new Object[ruleCount];
            for (int i = 0; i < ruleCount; i++) {
                byte[] bytes = new byte[dis.readShort()];
                dis.readFully(bytes);
                ruleArray[i] = bytes;
            }
            // 关联版本-区域-规则
            for (int i = 0; i < versionCount; i++) {
                int versionRegionCount = dis.readShort();
                for (int j = 0; j < versionRegionCount; j++) {
                    String regionId = regionArray[dis.readShort()];
                    Object rule = ruleArray[dis.readShort() & 0xffff];
                    if (rule instanceof byte[] bytes) {
                        ZoneRules rules = this.readRules(bytes);
                        this.doCache(regionId, rules);
                    }
                }
            }
        }
    }

    /**
     * 将时区规则写入本地缓存文件
     *
     * @param zoneId 时区标识
     * @param rules  时区规则
     * @throws IOException 写入过程中的异常
     */
    private void doCache(String zoneId, ZoneRules rules) throws IOException {
        String cacheDir = SysConst.cacheDir();
        File file = new File(cacheDir, zoneId);
        FileUtil.touch(file);
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
        try (os) {
            os.writeObject(rules);
        }
    }

    /**
     * 从字节数组反序列化时区规则
     *
     * @param bytes 规则字节数组
     * @return 时区规则
     * @throws ClassNotFoundException    类未找到异常
     * @throws InvocationTargetException 方法调用异常
     * @throws IllegalAccessException    非法访问异常
     */
    private ZoneRules readRules(byte[] bytes) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        Class<?> clazz = Class.forName("java.time.zone.Ser");
        Method method = ReflectUtil.getMethod(clazz, "read", DataInput.class);
        method.setAccessible(true);
        return (ZoneRules) method.invoke(null, dis);
    }

    /**
     * 从本地缓存文件读取时区规则
     *
     * @param zoneId 时区标识
     * @return 时区规则，缓存不存在时返回 null
     * @throws IOException            读取异常
     * @throws ClassNotFoundException 类未找到异常
     */
    private ZoneRules readCache(String zoneId) throws IOException, ClassNotFoundException {
        String cacheDir = SysConst.cacheDir();
        File file = new File(cacheDir, zoneId);
        if (!file.exists()) {
            return null;
        }
        ZoneRules rules;
        ObjectInputStream os = new ObjectInputStream(new FileInputStream(file));
        try (os) {
            rules = (ZoneRules) os.readObject();
        }
        return rules;
    }

    @Override
    public String toString() {
        return "Local[" + versionId + "]";
    }
}
