﻿using System;
using System.Collections.Generic;

public class BinaryHeap<T> where T : IComparable<T>
{
    private List<T> heap;

    public BinaryHeap()
    {
        this.heap = new List<T>();
    }

    public int Count
    {
        get
        {
            return this.heap.Count;
        }
    }

    public void Insert(T item)
    {
        this.heap.Add(item);
        this.HeapifyUp(item, this.heap.Count - 1);
    }

    private void HeapifyUp(T item, int index)
    {
        int parent = (index - 1) / 2;
        if (parent < 0)
        {
            return;
        }

        int compare = this.heap[parent].CompareTo(this.heap[index]);
        if (compare < 0)
        {
            this.Swap(parent, index);
            this.HeapifyUp(this.heap[parent], parent);
        }
    }

    private void Swap(int parent, int index)
    {
        T temp = this.heap[parent];
        this.heap[parent] = this.heap[index];
        this.heap[index] = temp;
    }

    public T Peek()
    {
        if (this.heap.Count == 0)
        {
            throw new InvalidOperationException();
        }

        return this.heap[0];
    }

    public T Pull()
    {
        if (this.heap.Count == 0)
        {
            throw new InvalidOperationException();
        }

        T element = this.heap[0];
        this.Swap(0, this.Count - 1);
        this.heap.RemoveAt(this.Count - 1);
        this.HeapifyDown(element, 0);

        return element;
    }

    private void HeapifyDown(T element, int index)
    {
        int parentIndex = index;        

        while (parentIndex < this.Count / 2)
        {
            int childIndex = (parentIndex * 2) + 1;
            if (childIndex + 1 < this.Count && IsGreater(childIndex + 1, childIndex))
            {
                childIndex++;
            }

            int compare = this.heap[parentIndex]
                .CompareTo(this.heap[childIndex]);
            if (compare < 0)
            {
                this.Swap(parentIndex, childIndex);
                parentIndex = childIndex;
            }
        }
    }

    private bool IsGreater(int left, int right)
    {
        return this.heap[left].CompareTo(this.heap[right]) < 0;
    }
}
