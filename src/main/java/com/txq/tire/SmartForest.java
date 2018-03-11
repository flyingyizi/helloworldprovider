package com.txq.tire;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//小树
public class SmartForest<T> implements Comparable<SmartForest<T>> {
  private static final int MAX_SIZE = 65536;  // 2^16
  //private static final int MAX_SIZE = 256;  // 2^8
  public SmartForest<T>[] branches = null;
  // 单独查找出来的对象
  SmartForest<T> brh = null;
  private double rate = 0.9;
  private char c;

  /**
   * status 此字的状态1，继续 2，是个词语但是还可以继续 ,3确定 nature 词语性质
   */
  private byte status = 1;
  // 词典后的参数
  //private T param = null;
  private Set<T> param = null;

  // root
  public SmartForest() {
  }

  // constractor for:首位直接数组定位
  @SuppressWarnings("unchecked")
  public SmartForest(double rate) {
    branches = new SmartForest[MAX_SIZE];
    this.rate = rate;
  }
  // constractor for:temp branch
  private SmartForest(char c) {
    this.c = c;
  }
  //constractor for:
  public SmartForest(char c, int status, T param) {
    this.c = c;
    this.status = (byte) status;
    //this.param = param;
    if( this.param==null ) {
      this.param = new HashSet<T>();
    }
    this.param.add(param);
  }

  /**
   * 增加子页节点
   *
   * @param branch
   * @return
   */
  @SuppressWarnings("unchecked")
  public synchronized SmartForest<T> add(SmartForest<T> branch) {
    if (branches == null) {
      branches = new SmartForest[0];
    }
    int bs = getIndex(branch.getC());
    if (bs > -1) {
      if (this.branches[bs] == null) {
        this.branches[bs] = branch;
      }
      this.brh = this.branches[bs];
      switch (branch.getStatus()) {
        case -1:
          this.brh.setStatus(1);
          break;
        case 1:
          if (this.brh.getStatus() == 3) {
            this.brh.setStatus(2);
          }
          break;
        case 3:
          if (this.brh.getStatus() != 3) {
            this.brh.setStatus(2);
          }
          this.brh.setParam(branch.getParam());

      }
      return this.brh;
    }

    if (bs < 0) {
      // 如果数组内元素接近于最大值直接数组定位，rate是内存和速度的一个平衡
      if (branches != null && branches.length >= MAX_SIZE * rate) {
        SmartForest<T>[] tempBranches = new SmartForest[MAX_SIZE];
        for (SmartForest<T> b : branches) {
          tempBranches[b.getC()] = b;
        }
        tempBranches[branch.getC()] = branch;
        branches = null;
        branches = tempBranches;
      } else {
        SmartForest<T>[] newBranches = new SmartForest[branches.length + 1];
        int insert = -(bs + 1);
        System.arraycopy(this.branches, 0, newBranches, 0, insert);
        System.arraycopy(branches, insert, newBranches, insert + 1, branches.length - insert);
        newBranches[insert] = branch;
        this.branches = newBranches;
      }

    }
    return branch;
  }

  public int getIndex(char c) {
    if (branches == null) {
      return -1;
    }
    if (branches.length == MAX_SIZE) {
      return c;
    }
    int i = Arrays.binarySearch(this.branches, new SmartForest<T>(c));
    return i;
  }

  /**
   * 二分查找是否包含
   *
   * @param c
   * @return
   */
  public boolean contains(char c) {
    if (this.branches == null) {
      return false;
    }
    return Arrays.binarySearch(this.branches, c) > -1;
  }

  public int compareTo(char c) {
    if (this.c > c) {
      return 1;
    }
    if (this.c < c) {
      return -1;
    }
    return 0;
  }

  public boolean equals(char c) {
    return this.c == c;
  }

  @Override
  public int hashCode() {
    return this.c;
  }

  public byte getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = (byte) status;
  }

  public char getC() {
    return this.c;
  }

  //public T getParam() {
  //  return this.param;
  //}
  public Set<T> getParam()  {
    return this.param;
  }

  @SuppressWarnings("unchecked")
  public void setParam(Set<T> param) {
    //this.param = param;
    if ( this.param == null) {
      this.param = new HashSet<T>();
    }
    this.param.addAll(param);

 /*
    if (this.param == null) {
      this.param = new HashSet<>();
      Collections.addAll(this.param, (T) param.toArray());
    }else {
      Set<T> set = new HashSet<>();
      Collections.addAll(set, (T) this.param.toArray());
      Collections.addAll(set, (T) param.toArray());
      this.param = null;
      this.param = set;
    }
*/


  }

  /**
   * 增加新词
   *
   * @param
   */
  public void add(String keyWord, T t) {
    SmartForest<T> tempBranch = this;
    for (int i = 0; i < keyWord.length(); i++) {
      if (keyWord.length() == i + 1) {
        tempBranch.add(new SmartForest<T>(keyWord.charAt(i), 3, t));
      } else {
        tempBranch.add(new SmartForest<T>(keyWord.charAt(i), 1, null));
      }
      tempBranch = tempBranch.branches[tempBranch.getIndex(keyWord.charAt(i))];
    }
  }

  /**
   * 增加一个元素到节点
   *
   * @param keyWord
   * @param t
   */
  public void addBranch(String keyWord, T t) {
    this.add(keyWord, t);
  }

  @Override
  public int compareTo(SmartForest<T> o) {
    if (this.c > o.c) {
      return 1;
    }
    if (this.c < o.c) {
      return -1;
    }
    return 0;
  }

  public SmartForest<T> get(char c) {
    return getBranch(c);
  }

  public SmartForest<T> getBranch(char c) {
    int index = getIndex(c);
    if (index < 0) {
      return null;
    } else {
      return branches[index];
    }
  }

  /**
   * 根据一个词获得所取的参数,没有就返回null
   *
   * @param keyWord
   */
  public SmartForest<T> getBranch(String keyWord) {
    SmartForest<T> tempBranch = this;
    int index = 0;
    for (int j = 0; j < keyWord.length(); j++) {
      index = tempBranch.getIndex(keyWord.charAt(j));
      if (index < 0) {
        return null;
      }
      if ((tempBranch = tempBranch.branches[index]) == null) {
        return null;
      }

    }
    return tempBranch;
  }

  /**
   * 根据一个词获得所取的参数,没有就返回null
   *
   * @param chars
   */
  public SmartForest<T> getBranch(char[] chars) {
    SmartForest<T> tempBranch = this;
    int index = 0;
    for (int j = 0; j < chars.length; j++) {
      index = tempBranch.getIndex(chars[j]);
      if (index < 0) {
        return null;
      }
      if ((tempBranch = tempBranch.branches[index]) == null) {
        return null;
      }

    }
    return tempBranch;
  }

  /**
   * 取得所有的分支
   *
   * @return
   */
  public SmartForest<T>[] getBranches() {
    return branches;
  }

  /**
   * 删除一个词语
   *
   * @param word
   */
  public void remove(String word) {
    getBranch(word).status = 1;
    getBranch(word).param = null ;
  }

  /**
   * 清空
   */
  @SuppressWarnings("unchecked")
  public void clear() {
    branches = new SmartForest[MAX_SIZE];
  }


}
