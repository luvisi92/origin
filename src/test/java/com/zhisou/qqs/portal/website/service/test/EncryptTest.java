package com.zhisou.qqs.portal.website.service.test;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import java.security.Key;

import org.junit.Assert;
import org.junit.Test;

public class EncryptTest {

  @Test
  public void encrypt() {
      AesCipherService aesCipherService = new AesCipherService();
      aesCipherService.setKeySize(128); //设置key长度
      //生成key
      Key key = aesCipherService.generateNewKey();
      StringBuffer sb = new StringBuffer();
      for (byte b : key.getEncoded()) {
          sb.append(b).append(",");
      }
      System.out.println(sb.toString());
      String text = "hello";
      //加密
      String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
      //解密
      String text2 =
       new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

      Assert.assertEquals(text, text2);
  }
;
}
