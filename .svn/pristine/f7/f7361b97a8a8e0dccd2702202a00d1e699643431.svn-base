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
 * Description : 数组对象型环形缓冲区
 */
public abstract class ObjRingBuffer<T>
{
    /** 缓冲区最大容量 */
    public static final int MAX_SIZE = 100000;
    /** 缓冲区最小容量 */
    public static final int MIN_SIZE = 10;
    /** 缓冲区当前容量 */
    private final int mCapacity;
    /** 一定容量的数组 */
    private final T[] mArrayBuffer;
    /** 缓冲区当前位置 */
    private int mListHeadIndex = 0;
    /** 已写入的元素 */
    private int mElementSize = 0;

    public ObjRingBuffer(int capacity, T[] arr)
    {
        super();
        mCapacity = Utils.range(MIN_SIZE, MAX_SIZE, capacity);
        mArrayBuffer = arr.clone();
    }
    
    /**
     * 添加元素到缓冲区
     * 
     * @param element
     *            待添加的元素
     */
    public synchronized void add(T element)
    {
        mListHeadIndex = calcNextIndex(mListHeadIndex);
        mArrayBuffer[mListHeadIndex] = element;
        mElementSize++;
    }

    /**
     * 重置缓冲区，清空引用的数据对象
     */
    public synchronized void reset()
    {
        mListHeadIndex = 0;
        mElementSize = 0;
    }

    /**
     * 按照写入顺序输出缓冲区数据
     * 
     * @return 返回已经排列好的缓冲区数据
     */
    protected synchronized T[] getBuffer(T[] outBuffer)
    {
        int validElementSize = getValidElementSize();
        if (outBuffer == null || outBuffer.length != validElementSize)
        {
            return outBuffer;
        }
        int intdex = mListHeadIndex;
        T[] ret = outBuffer;
        if (validElementSize <= 0)
        {
            return ret;
        }
        for (int i = validElementSize - 1; i >= 0; i--)
        {
            T element = mArrayBuffer[intdex];
            ret[i] = element;
            intdex = calcPrevIndex(intdex);
        }
        return ret;
    }

    /**
     * 获取当前以缓冲的数据
     * @return  将当前缓冲区已缓存数据以数组形式返回
     */
    public abstract T[] getBuffer();

    /**
     * 计算上一个buffer数据索引值
     * @param current   当前索引
     * @return  返回上一个buffer数据的索引值，如果buffer为空则返回0
     */
    protected synchronized int calcPrevIndex(int current)
    {
        return mElementSize > 0 ? (current - 1 + mCapacity) % mCapacity : 0;
    }

    /**
     * 计算下一个buffer数据索引值
     * @param current   当前索引
     * @return  返回下一个buffer数据的索引值，如果buffer为空则返回0
     */
    protected synchronized int calcNextIndex(int current)
    {
        return mElementSize > 0 ? (current + 1) % mCapacity : 0;
    }

    /**
     * 获取buffer最大容量
     */
    public int getCapacity()
    {
        return mCapacity;
    }

    /**
     * 获取缓冲区已缓存的元素个数
     */
    public synchronized int getValidElementSize()
    {
        return mElementSize > mCapacity ? mCapacity : mElementSize;
    }
}
