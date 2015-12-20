package com.sxdsf.deposit.dao.disk.impl;

import android.util.Log;

import com.sxdsf.deposit.dao.disk.DiskDAO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class DiskDAOImpl implements DiskDAO {

    private static final String TAG = "DiskDAOImpl";

    @Override
    public <T> boolean save(File file, T value) {
        // TODO Auto-generated method stub
        boolean success = false;
        try {
            if (file != null && file.exists()) {
                if (file.isFile()) {
                    byte[] temp = toByteArray(value);
                    FileOutputStream out = null;
                    FileChannel outChannel = null;
                    try {
                        out = new FileOutputStream(file);
                        outChannel = out.getChannel();
                        outChannel.write(ByteBuffer.wrap(temp));
                        success = true;
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        Log.e(TAG, e.getMessage());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        Log.e(TAG, e.getMessage());
                    } finally {
                        if (outChannel != null) {
                            try {
                                outChannel.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                Log.e(TAG, e.getMessage());
                            }
                        }
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return success;
    }

    @Override
    public <T> boolean save(String filePath, T value) {
        // TODO Auto-generated method stub
        boolean success = false;
        if (this.createDirs(filePath)) {
            File file = new File(filePath);
            success = this.save(file, value);
        }
        return success;
    }

    @Override
    public <T> T get(File file) {
        // TODO Auto-generated method stub
        T result = null;
        try {
            if (file != null && file.exists()) {
                if (file.isFile()) {
                    FileInputStream in = null;
                    FileChannel inChannel = null;
                    try {
                        in = new FileInputStream(file);
                        inChannel = in.getChannel();
                        // 一次读多个字节
                        ByteBuffer buff = ByteBuffer
                                .allocateDirect((int) file.length());
                        inChannel.read(buff);
                        result = toObject(buff.array());
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        Log.e(TAG, e.getMessage());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        Log.e(TAG, e.getMessage());
                    } finally {
                        if (inChannel != null) {
                            try {
                                inChannel.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                Log.e(TAG, e.getMessage());
                            }
                        }
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }

    @Override
    public <T> T get(String filePath) {
        // TODO Auto-generated method stub
        File file = new File(filePath);
        return this.get(file);
    }

    @Override
    public boolean delete(File file, boolean include) {
        // TODO Auto-generated method stub
        // 判断目录或文件是否存在
        boolean success = false;
        if (file != null && file.exists()) { // 不存在返回 false
            // 判断是否为文件
            success = file.isFile() ? this.deleteFile(file) : this
                    .deleteDirectory(file, include);
        }
        return success;
    }

    @Override
    public boolean delete(String filePath, boolean include) {
        // TODO Auto-generated method stub
        File file = new File(filePath);
        return this.delete(file, include);
    }

    @Override
    public long getModifyTime(File file) {
        // TODO Auto-generated method stub
        return (file != null && file.exists()) ? file.lastModified() : 0;
    }

    @Override
    public long getModifyTime(String filePath) {
        // TODO Auto-generated method stub
        File file = new File(filePath);
        return this.getModifyTime(file);
    }

    @Override
    public boolean copy(File from, File to) {
        // TODO Auto-generated method stub
        boolean success = false;
        if (from != null && from.exists() && to != null && to.exists()) {
            FileInputStream fi = null;
            FileOutputStream fo = null;
            FileChannel in = null;
            FileChannel out = null;
            try {
                fi = new FileInputStream(from);
                fo = new FileOutputStream(to);
                in = fi.getChannel();// 得到对应的文件通道
                out = fo.getChannel();// 得到对应的文件通道
                in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
                success = true;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                Log.e(TAG, e.getMessage());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e(TAG, e.getMessage());
            } finally {
                if (fi != null) {
                    try {
                        fi.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        Log.e(TAG, e.getMessage());
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        Log.e(TAG, e.getMessage());
                    }
                }
                if (fo != null) {
                    try {
                        fo.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        Log.e(TAG, e.getMessage());
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        Log.e(TAG, e.getMessage());
                    }
                }
            }

        }
        return success;
    }

    @Override
    public boolean copy(String fromPath, String toPath) {
        // TODO Auto-generated method stub
        File from = new File(fromPath);
        File to = new File(toPath);
        return this.copy(from, to);
    }

    /**
     * 对象转数组
     *
     * @param obj
     * @return
     */
    private <T> byte[] toByteArray(T obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.e(TAG, e.getMessage());
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        return bytes;
    }

    /**
     * 数组转对象
     *
     * @param bytes
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T toObject(byte[] bytes) {
        T obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = (T) ois.readObject();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        } catch (ClassCastException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.e(TAG, e.getMessage());
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        return obj;
    }

    /**
     * 删除单个文件
     *
     * @param file
     * @return
     */
    private boolean deleteFile(File file) {
        boolean flag = false;
        // 路径为文件且不为空则进行删除
        if (file != null && file.isFile() && file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 删除目录以及目录下的文件
     *
     * @param dirFile
     * @param include
     * @return
     */
    private boolean deleteDirectory(File dirFile, boolean include) {
        boolean flag = false;
        if (dirFile != null) {
            // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
            String path = dirFile.getAbsolutePath();
            if (!path.endsWith(File.separator)) {
                path = path + File.separator;
                dirFile = new File(path);
            }
            // 如果dir对应的文件不存在，或者不是一个目录，则退出
            if (dirFile.exists() && dirFile.isDirectory()) {
                // 删除文件夹下的所有文件(包括子目录)
                File[] files = dirFile.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        // 删除子文件
                        File file = files[i];
                        if (file != null) {
                            if (file.isFile()) {
                                flag = deleteFile(file);
                                if (!flag)
                                    break;
                            } else {
                                flag = deleteDirectory(file, true);
                                if (!flag)
                                    break;
                            }
                        }
                    }
                }
            }

            if (flag && include) {
                flag = dirFile.delete();
            }
        }
        return flag;
    }

    private boolean createDirs(String filePath) {
        boolean flag = false;
        if (filePath != null) {
            // 20150615 sunbowen 这里的key第一个字符不能是分隔符，所以要大于0
            if (filePath.indexOf(File.separator) > 0) {
                int index = filePath.lastIndexOf(File.separator);
                String path = filePath.substring(0, index);
                if (path != null) {
                    File file = new File(filePath);
                    if (file != null) {
                        if (file.exists()) {
                            flag = true;
                        } else {
                            flag = file.mkdirs();
                        }
                    }
                }
                // 如果传入的不是“/”这种，那么就是一个单独的key，不用创建文件夹
            } else if (filePath.indexOf(File.separator) < 0) {
                flag = true;
            }
        }
        return flag;
    }

}
