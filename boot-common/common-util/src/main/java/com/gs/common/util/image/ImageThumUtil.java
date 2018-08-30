package com.gs.common.util.image;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

/**
 * 图片压缩工具类
 * Created by gs on 2017/12/11.
 */
public class ImageThumUtil {

    /**
     * 指定大小进行缩放
     * @param srcUrl 源图片地址
     * @param targetUrl 目标图片地址
     * @param width 宽
     * @param height 高
     * @throws IOException
     */
    public static void resize(String srcUrl,String targetUrl,int width,int height) throws IOException {

        Thumbnails.of(srcUrl).size(width, height).toFile(targetUrl);
    }
}
