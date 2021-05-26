```
//ANT+
7e  //head
aa //magic data
00 00 00 65 //HUB_ID
19 7c //包序号
23  //hub version
00 2a //len
ff ff 00  //reserver
00 00 00 00 00 00 //hub mac addr
01 //cmd
01 //key
00 0f  //key pack len
78 01 00 00 00 e6 04 ff a1 5a a0 5a 9b 00 00   //ant+ data
00 00 //好吧，这是协议里面没有的，写错了，现在当作reserver，以后修改
30 f4  //check sum
7f//tail
```

```
7E AA 00 00 00 77 DA 8F 23 00 28 FF FF 00 00 00 00 00 00 00 01 01 00 0F 78 01 00 00 04 DF 84 FF B0 23 BD 25 B4 75 C3 81 09 7F
```

```
数据的接收，根据转译规则进行数据还原->根据密钥解密->按照数据格式解析。
0X7D   0x7D 0x01
0x7E    0x7D 0x02
0x7F    0x7D 0x03

```

```
转义  1 去掉7E 头尾 7F
```

