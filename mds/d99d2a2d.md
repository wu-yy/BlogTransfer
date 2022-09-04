获取DataFrame的列名是一个比较简单的操作，又以下几个方法：

1.\[column for column in df\]  
2.df.columns.values 返回 array  
3.list(df)  
4.df.columns 返回Index，可以通过 tolist(), 或者 list（array） 转换为list

```java
import pandas as pd
from numpy.random import randint
df = pd.DataFrame(columns=list('abcdefghij'))

%timeit [column for column in df]

%timeit list(df.columns.values)

%timeit list(df)

%timeit list(df.columns)
# 100000 loops, best of 3: 2.24 µs per loop

%timeit df.columns.tolist()
# 1000000 loops, best of 3: 1.77 µs per loop
```