/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.pinnet.chargerapp.utils.ringbuffer;


import com.pinnet.chargerapp.utils.Utils;

/**
 * Create Date: 2015-2-6<br>
 * Create Author: cWX239887<br>
 * Description : 字符串型环形数据缓冲区
 */
public class StringRingBuffer extends ObjRingBuffer<String>
{

    /**
     * 缓冲区构造函数
     * @param capacity 缓冲区容量
     */
    public StringRingBuffer(int capacity)
    {
        super(capacity, new String[Utils.range(MIN_SIZE, MAX_SIZE, capacity)]);
    }

    @Override
    public String[] getBuffer()
    {
        return getBuffer(new String[getValidElementSize()]);
    }

}
