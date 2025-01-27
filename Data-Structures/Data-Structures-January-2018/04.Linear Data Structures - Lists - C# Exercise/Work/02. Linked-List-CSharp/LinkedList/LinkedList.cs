﻿using System;
using System.Collections;
using System.Collections.Generic;

public class LinkedList<T> : IEnumerable<T>
{
    public class Node
    {
        public T Value { get; set; }
        public Node Next { get; set; }

        public Node(T value)
        {
            this.Value = value;
        }
    }

    public Node Head { get; private set; }
    public Node Tail { get; private set; }
    public int Count { get; private set; }
       
    public void AddFirst(T item)
    {
        Node old = Head;

        this.Head = new Node(item);
        this.Head.Next = old;

        if (this.IsEmpty())
        {
            Tail = Head;
        }

        Count++;
    }

    private bool IsEmpty()
    {
        return Head == null || Tail == null;
    }

    public void AddLast(T item)
    {
        Node old = this.Tail;
        this.Tail = new Node(item);

        if (this.IsEmpty())
        {
            Head = Tail;
        }
        else
        {
            old.Next = this.Tail;
        }

        this.Count++;
    }

    public T RemoveFirst()
    {
        if (this.IsEmpty())
        {
            throw new InvalidOperationException();
        }

        T item = this.Head.Value;
        this.Head = this.Head.Next;
        this.Count--;

        if (this.IsEmpty())
        {
            this.Tail = null;
        }

        return item;
    }

    public T RemoveLast()
    {
        if (this.IsEmpty())
        {
            throw new InvalidOperationException();
        }

        T item = this.Tail.Value;

        if (this.Count == 1)
        {
            this.Head = this.Tail = null;
        }
        else
        {
            Node newTail = this.GetSecondToLast();
            newTail.Next = null;
            this.Tail = newTail;
        }

        this.Count--;

        return item;
    }

    private Node GetSecondToLast()
    {
        Node parent = this.Head;
        while(parent.Next != this.Tail)
        {
            parent = parent.Next;
        }

        parent.Next = null;
        return parent;
    }

    public IEnumerator<T> GetEnumerator()
    {
        Node current = this.Head;
        while (current != null)
        {
            yield return current.Value;
            current = current.Next;
        }
    }

    IEnumerator IEnumerable.GetEnumerator()
    {
        return this.GetEnumerator();
    }
}
